package com.music.project.api.playlist.service;

import com.music.project.api.artist.dto.ArtistResultDTO;
import com.music.project.api.genre.dto.GenreResultDTO;
import com.music.project.api.playlist.dto.PlaylistDTO;
import com.music.project.api.playlist.dto.request.PlaylistCreationRequest;
import com.music.project.api.playlist.dto.request.PlaylistUpdateRequest;
import com.music.project.api.playlist.dto.response.PlaylistResponse;
import com.music.project.api.playlist.mapper.PlaylistMapper;
import com.music.project.api.playlist.repository.PlaylistRepository;
import com.music.project.api.playlistSong.repository.PlaylistSongRepository;
import com.music.project.api.song.dto.SongResultDTO;
import com.music.project.api.song.repository.SongRepository;
import com.music.project.api.songArtist.repository.SongArtistRepository;
import com.music.project.api.songGenre.repository.SongGenreRepository;
import com.music.project.api.user.repository.UserRepository;
import com.music.project.entities.*;
import com.music.project.helpers.string.StringHelper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlaylistService {
    PlaylistRepository playlistRepository;
    private final SongArtistRepository songArtistRepository;
    private final SongGenreRepository songGenreRepository;
    @Autowired
    private SongRepository songRepository;
    @Autowired
            private PlaylistSongRepository playlistSongRepository;
//    PlaylistMapper mapper;
    UserRepository userRepository;

//    public PlaylistResponse create(PlaylistCreationRequest request) {
//        var user = userRepository.findById(request.getUserId())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        var playlist = mapper.toPlaylist(request);
//        playlist.setUser(user);
//        playlist.setCreatedAt(new Date());
//        playlist.setUpdateAt(new Date());
//
//        return mapper.toPlaylistResponse(repository.save(playlist));
//    }
//
//    public PlaylistResponse update(int playlistId, PlaylistUpdateRequest request) {
//        var playlist = repository.findById(playlistId)
//                .orElseThrow(() -> new RuntimeException("Playlist not found"));
//
//        mapper.updatePlaylist(playlist, request);
//        playlist.setUpdateAt(new Date());
//
//        return mapper.toPlaylistResponse(repository.save(playlist));
//    }
//
//    public List<PlaylistResponse> getAll() {
//        var playlists = repository.findAll();
//        return playlists.stream().map(mapper::toPlaylistResponse).toList();
//    }
//
//    public List<PlaylistResponse> getByUserId(int userId) {
//        var playlists = repository.findByUserId(userId);
//        return playlists.stream().map(mapper::toPlaylistResponse).toList();
//    }
//
//    public PlaylistResponse getById(int playlistId) {
//        var playlist = repository.findById(playlistId)
//                .orElseThrow(() -> new RuntimeException("Playlist not found"));
//        return mapper.toPlaylistResponse(playlist);
//    }

//    @Transactional
//    public void delete(int playlistId) {
//        if (!repository.existsById(playlistId)) {
//            throw new RuntimeException("Playlist not found");
//        }
//        repository.deleteById(playlistId);
//    }

    public List<PlaylistDTO> getAllPlaylists() {
        return playlistRepository.findAllPlaylistDTOs();
    }

    public PlaylistDTO getPlaylistDetails(Integer playlistId) {
        Playlist playlist = playlistRepository.findPlaylistWithSongs(playlistId)
                .orElseThrow(() -> new EntityNotFoundException("Playlist not found"));

        List<SongResultDTO> songDTOs = playlist.getPlaylistSongs().stream()
                .map(ps -> {
                    Song song = ps.getSong();
                    SongResultDTO dto = new SongResultDTO(
                            song.getId(), song.getName(), song.getSlug(), song.getUrlSource(),
                            song.getPhoto(), song.getReleaseDay(), new ArrayList<>(), new ArrayList<>()
                    );
                    return dto;
                }).toList();

        List<Integer> songIds = songDTOs.stream().map(SongResultDTO::getId).toList();

        Map<Integer, List<ArtistResultDTO>> artistsMap = songArtistRepository.findBySongIds(songIds)
                .stream()
                .collect(Collectors.groupingBy(ArtistResultDTO::getSongId));

        Map<Integer, List<GenreResultDTO>> genresMap = songGenreRepository.findBySongIds(songIds)
                .stream()
                .collect(Collectors.groupingBy(GenreResultDTO::getSongId));

        songDTOs.forEach(song -> {
            song.setArtists(artistsMap.getOrDefault(song.getId(), new ArrayList<>()));
            song.setGenres(genresMap.getOrDefault(song.getId(), new ArrayList<>()));
        });

        return new PlaylistDTO(
                playlist.getId(), playlist.getUser().getId(), playlist.getName(),
                playlist.getSlug(), playlist.getPhoto(), songDTOs
        );
    }

    public void addSongToPlaylist(Integer playListId, Integer songId) {
        Playlist playlist = playlistRepository.findById(playListId)
                .orElseThrow(() -> new EntityNotFoundException("Playlist not found"));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new EntityNotFoundException("Song not found"));

        // Kiểm tra xem bài hát đã có trong playlist hay chưa
        if (playlistSongRepository.existsByPlaylistIdAndSongId(playListId, songId)) {
            throw new IllegalStateException("Song already exists in the playlist");
        }

        PlaylistSongId playlistSongId = new PlaylistSongId(playListId, songId, new Date(), new Date());
        PlaylistSong playlistSong = new PlaylistSong(playlistSongId, playlist, song);
        playlistSongRepository.save(playlistSong);
    }

    public PlaylistDTO addPlaylist(PlaylistCreationRequest dto) {
        // Kiểm tra xem slug có bị trùng không


        // Tìm user theo ID
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Tạo playlist mới
        Playlist playlist = new Playlist();
        playlist.setUser(user);
        playlist.setName(dto.getName());
        playlist.setSlug(StringHelper.toSlug(dto.getName()));

        playlist.setPhoto(dto.getPhoto());
        playlist.setCreatedAt(new Date());
        playlist.setUpdateAt(new Date());

        playlistRepository.save(playlist);

        // Trả về PlaylistDTO
        return new PlaylistDTO(playlist.getId(), user.getId(), playlist.getName(), playlist.getSlug(), playlist.getPhoto(), new ArrayList<>());
    }
}

