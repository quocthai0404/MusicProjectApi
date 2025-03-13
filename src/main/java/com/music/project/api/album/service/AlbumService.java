package com.music.project.api.album.service;

import com.music.project.api.album.dto.request.AlbumCreationRequest;
import com.music.project.api.album.dto.request.AlbumUpdateRequest;
import com.music.project.api.album.dto.response.AlbumResponse;
import com.music.project.api.album.mapper.AlbumMapper;
import com.music.project.api.album.repository.AlbumRepository;
import com.music.project.api.albumUser.repository.AlbumUserRepository;
import com.music.project.api.user.repository.UserRepository;
import com.music.project.entities.Album;
import com.music.project.entities.AlbumUser;
import com.music.project.entities.AlbumUserId;
import com.music.project.helpers.string.StringHelper;
import com.music.project.securities.UserDetailsImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AlbumService {
    AlbumRepository repository;

    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private AlbumUserRepository albumUserRepository;
    @Autowired
    private UserRepository userRepository;
    //AlbumMapper mapper;

//    public AlbumResponse create(AlbumCreationRequest request) {
//        var album = mapper.toAlbum(request);
//        album.setCreatedAt(new Date());
//        album.setUpdateAt(new Date());
//        return mapper.toAlbumResponse(repository.save(album));
//    }
//
//    public AlbumResponse update(int albumId, AlbumUpdateRequest request) {
//        var album = repository.findById(albumId)
//            .orElseThrow(() -> new RuntimeException("Album not found"));
//
//        mapper.updateAlbum(album, request);
//        album.setUpdateAt(new Date());
//
//        return mapper.toAlbumResponse(repository.save(album));
//    }
//
//    public List<AlbumResponse> getAll() {
//        var albums = repository.findAll();
//        return albums.stream().map(mapper::toAlbumResponse).toList();
//    }
//
//    public AlbumResponse getById(int albumId) {
//        var album = repository.findById(albumId)
//            .orElseThrow(() -> new RuntimeException("Album not found"));
//        return mapper.toAlbumResponse(album);
//    }

    @Transactional
    public void delete(int albumId) {
        if (!repository.existsById(albumId)) {
            throw new RuntimeException("Album not found");
        }
        repository.deleteById(albumId);
    }



    @Transactional
    public Album createAlbum(AlbumCreationRequest request) {
        // Lấy userId từ JWT
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof UserDetailsImpl userDetails)) {
            throw new RuntimeException("Cannot get authenticated user");
        }

        Integer userId = userDetails.getId();

        // Tạo album mới
        Album album = new Album();
        album.setName(request.getName());

        album.setSlug(StringHelper.toSlug(request.getSlug()));
        album.setPhoto(request.getPhoto());
        album.setReleaseDay(request.getReleaseDay());
        album.setCreatedAt(new Date());
        album.setUpdateAt(new Date());

        Album savedAlbum = albumRepository.save(album);

        // Tạo AlbumUser để liên kết user với album
        AlbumUser albumUser = new AlbumUser();
        AlbumUserId albumUserId = new AlbumUserId(userId, savedAlbum.getId(), new Date(), new Date());

        albumUser.setId(albumUserId);
        albumUser.setAlbum(savedAlbum);
        albumUser.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")));

        albumUserRepository.save(albumUser);

        return savedAlbum;
    }
}