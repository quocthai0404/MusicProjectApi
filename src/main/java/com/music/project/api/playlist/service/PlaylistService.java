package com.music.project.api.playlist.service;

import com.music.project.api.playlist.dto.request.PlaylistCreationRequest;
import com.music.project.api.playlist.dto.request.PlaylistUpdateRequest;
import com.music.project.api.playlist.dto.response.PlaylistResponse;
import com.music.project.api.playlist.mapper.PlaylistMapper;
import com.music.project.api.playlist.repository.PlaylistRepository;
import com.music.project.api.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlaylistService {
    PlaylistRepository repository;
    PlaylistMapper mapper;
    UserRepository userRepository;

    public PlaylistResponse create(PlaylistCreationRequest request) {
        var user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
                
        var playlist = mapper.toPlaylist(request);
        playlist.setUser(user);
        playlist.setCreatedAt(new Date());
        playlist.setUpdateAt(new Date());
        
        return mapper.toPlaylistResponse(repository.save(playlist));
    }

    public PlaylistResponse update(int playlistId, PlaylistUpdateRequest request) {
        var playlist = repository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        mapper.updatePlaylist(playlist, request);
        playlist.setUpdateAt(new Date());

        return mapper.toPlaylistResponse(repository.save(playlist));
    }

    public List<PlaylistResponse> getAll() {
        var playlists = repository.findAll();
        return playlists.stream().map(mapper::toPlaylistResponse).toList();
    }
    
    public List<PlaylistResponse> getByUserId(int userId) {
        var playlists = repository.findByUserId(userId);
        return playlists.stream().map(mapper::toPlaylistResponse).toList();
    }

    public PlaylistResponse getById(int playlistId) {
        var playlist = repository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));
        return mapper.toPlaylistResponse(playlist);
    }

    @Transactional
    public void delete(int playlistId) {
        if (!repository.existsById(playlistId)) {
            throw new RuntimeException("Playlist not found");
        }
        repository.deleteById(playlistId);
    }
}