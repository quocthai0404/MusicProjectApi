package com.music.project.api.album.service;

import com.music.project.api.album.dto.request.AlbumCreationRequest;
import com.music.project.api.album.dto.request.AlbumUpdateRequest;
import com.music.project.api.album.dto.response.AlbumResponse;
import com.music.project.api.album.mapper.AlbumMapper;
import com.music.project.api.album.repository.AlbumRepository;
import com.music.project.entities.Album;
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
public class AlbumService {
    AlbumRepository repository;
    AlbumMapper mapper;

    public AlbumResponse create(AlbumCreationRequest request) {
        var album = mapper.toAlbum(request);
        album.setCreatedAt(new Date());
        album.setUpdateAt(new Date());
        return mapper.toAlbumResponse(repository.save(album));
    }

    public AlbumResponse update(int albumId, AlbumUpdateRequest request) {
        var album = repository.findById(albumId)
            .orElseThrow(() -> new RuntimeException("Album not found"));

        mapper.updateAlbum(album, request);
        album.setUpdateAt(new Date());

        return mapper.toAlbumResponse(repository.save(album));
    }

    public List<AlbumResponse> getAll() {
        var albums = repository.findAll();
        return albums.stream().map(mapper::toAlbumResponse).toList();
    }

    public AlbumResponse getById(int albumId) {
        var album = repository.findById(albumId)
            .orElseThrow(() -> new RuntimeException("Album not found"));
        return mapper.toAlbumResponse(album);
    }

    @Transactional
    public void delete(int albumId) {
        if (!repository.existsById(albumId)) {
            throw new RuntimeException("Album not found");
        }
        repository.deleteById(albumId);
    }
}