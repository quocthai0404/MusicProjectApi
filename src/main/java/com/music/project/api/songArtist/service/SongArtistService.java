package com.music.project.api.songArtist.service;


import com.music.project.api.song.mapper.SongMapper;
import com.music.project.api.song.repository.SongRepository;
import com.music.project.api.songArtist.dto.request.SongArtistCreationRequest;
import com.music.project.api.songArtist.dto.request.SongArtistUpdateRequest;
import com.music.project.api.songArtist.dto.response.SongArtistResponse;
import com.music.project.api.songArtist.mapper.SongArtistMapper;
import com.music.project.api.songArtist.repository.SongArtistRepository;
import com.music.project.api.user.repository.UserRepository;
import com.music.project.entities.Song;
import com.music.project.entities.SongArtist;
import com.music.project.entities.SongArtistId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        System.out.println(songRepository.save(song));

        //get artist list from id then use for building song artist list simultaneously (using building pattern lombok)
        List<SongArtist> songArtists = request.getArtistIds().stream()
                .map(artistId -> {
                    var artist = artistRepository.findById(artistId)
                            .orElseThrow(() -> new RuntimeException("Artist not found"));
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

        //map into response after save all
        return songArtistMapper.toSongArtistReponse(songArtistRepository.saveAll(songArtists));
    }

    @Transactional
    public List<SongArtistResponse> update(int songId, SongArtistUpdateRequest request) {
        //delete songartist
        songArtistRepository.deleteBySong_Id(songId);

        //find existing song
        var existingSong = songRepository.findById(songId).orElseThrow(() -> new RuntimeException("Song not found"));


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

        //map into response after save all
        return songArtistMapper.toSongArtistReponse(songArtistRepository.saveAll(songArtists));
    }
}
