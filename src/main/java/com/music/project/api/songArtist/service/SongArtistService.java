package com.music.project.api.songArtist.service;


import com.music.project.api.song.mapper.SongMapper;
import com.music.project.api.song.repository.SongRepository;
import com.music.project.api.songArtist.dto.request.SongArtistCreationRequest;
import com.music.project.api.songArtist.dto.request.SongArtistUpdateRequest;
import com.music.project.api.songArtist.dto.response.SongArtistResponse;
import com.music.project.api.songArtist.mapper.SongArtistMapper;
import com.music.project.api.songArtist.repository.SongArtistRepository;
import com.music.project.api.user.repository.UserRepository;
import com.music.project.entities.SongArtist;
import com.music.project.entities.SongArtistId;
import com.music.project.exception.AppException;
import com.music.project.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SongArtistService {
    SongRepository songRepository;
    SongMapper songMapper;
    SongArtistMapper songArtistMapper;
    SongArtistRepository songArtistRepository;
    UserRepository artistRepository;

    @Transactional
    public List<SongArtistResponse> create(SongArtistCreationRequest request) {
        //create song first
        var song = songMapper.toSong(request.getSong());
        songRepository.save(song);
        try {
            songRepository.save(song);
        }
        catch ( AppException e){
            throw  new AppException(ErrorCode.UNCATEGORIZED_ERROR);
        }

        //get artist list from id then use for building song artist list simultaneously (using building pattern lombok)
        List<SongArtist> songArtists = request.getArtistIds().stream()
                .map(artistId -> {
                    var artist = artistRepository.findById(artistId)
                            .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));//custom hien ten class
                    return SongArtist.builder()
                            .id( SongArtistId.builder()
                                    .songId(song.getId())
                                    .userId(artist.getId())
                                    .createdAt(new Date())
                                    .updateAt(new Date())
                                    .build())
                            .song(song)
                            .user(artist)
                            .build();
                })
                .collect(Collectors.toList());

        //save into db
        songArtistRepository.saveAll(songArtists);

        //map into response after save all
        return songArtists.stream().map(songArtistMapper::toSongArtistResponse).toList();
    }

    @Transactional
    public List<SongArtistResponse> update(int songId, SongArtistUpdateRequest request) {
        //delete song artist
        songArtistRepository.deleteBySong_Id(songId);

        //find existing song
        //example error response
        var existingSong = songRepository.findById(songId).orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));


        //get artist list from id then use for building song artist list simultaneously (using building pattern lombok)
        List<SongArtist> songArtists = request.getArtistIds().stream()
                .map(artistId -> {
                    var artist = artistRepository.findById(artistId)
                            .orElseThrow(() -> new RuntimeException("Artist not found"));
                    return SongArtist.builder()
                            .id( SongArtistId.builder()
                                    .songId(existingSong.getId())
                                    .userId(artist.getId())
                                    .createdAt(new Date())
                                    .updateAt(new Date())
                                    .build())
                            .song(existingSong)
                            .user(artist)
                            .build();
                })
                .collect(Collectors.toList());

        //save into db
        songArtistRepository.saveAll(songArtists);

        //map into response after save all
        return songArtists.stream().map(songArtistMapper::toSongArtistResponse).toList();
    }

    public List<SongArtistResponse> getAll() {
        var songArtists = songArtistRepository.findAll();
        return songArtists.stream().map(songArtistMapper::toSongArtistResponse).toList();
    }
}
