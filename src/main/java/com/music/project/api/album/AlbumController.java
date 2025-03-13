package com.music.project.api.album;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.music.project.api.album.dto.request.AlbumCreationRequest;
import com.music.project.api.album.dto.request.AlbumUpdateRequest;
import com.music.project.api.album.dto.response.AlbumResponse;
import com.music.project.api.album.service.AlbumService;
import com.music.project.api.playlist.dto.PlaylistDTO;
import com.music.project.api.playlist.dto.request.PlaylistCreationRequest;
import com.music.project.entities.Album;
import com.music.project.helpers.base.response.ResponseObject;
import com.music.project.helpers.cloudinary.service.CloudinaryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/album")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AlbumController {
    AlbumService albumService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CloudinaryService cloudinaryService;
    private static final String FOLDER_NAME = "resources";

    private static final String DEFAULT_AVATAR = "https://res.cloudinary.com/dhee9ysz4/image/upload/v1741701543/dox72hqruib4lirmaynh.png";

//    @PostMapping("create")
//    ResponseObject<AlbumResponse> create(@RequestBody AlbumCreationRequest request) {
//        return ResponseObject.<AlbumResponse>builder()
//                .result(albumService.create(request))
//                .build();
//    }
//
//    @PutMapping("update/{albumId}")
//    ResponseObject<AlbumResponse> update(@PathVariable int albumId, @RequestBody AlbumUpdateRequest request) {
//        return ResponseObject.<AlbumResponse>builder()
//                .result(albumService.update(albumId, request))
//                .build();
//    }
//
//    @GetMapping("getAll")
//    ResponseObject<List<AlbumResponse>> getAll() {
//        return ResponseObject.<List<AlbumResponse>>builder()
//                .result(albumService.getAll())
//                .build();
//    }
//
//    @GetMapping("{albumId}")
//    ResponseObject<AlbumResponse> getById(@PathVariable int albumId) {
//        return ResponseObject.<AlbumResponse>builder()
//                .result(albumService.getById(albumId))
//                .build();
//    }
//
//    @DeleteMapping("{albumId}")
//    ResponseObject<String> delete(@PathVariable int albumId) {
//        albumService.delete(albumId);
//        return ResponseObject.<String>builder()
//                .message("Album deleted successfully")
//                .build();
//    }

    private String uploadIfNotEmpty(MultipartFile file, String defaultPath) {
        if (file != null && !file.isEmpty()) {
            try {
                return (String) cloudinaryService.uploadFile(file, FOLDER_NAME).get("secure_url");
            } catch (Exception e) {
                return defaultPath;
            }
        }
        return defaultPath;
    }

    //api nay can jwt role artist hoac user
    @PostMapping("/add")
    public ResponseEntity<ResponseObject<Album>> createAlbum(
            @RequestParam("AlbumCreationRequestJson") String AlbumCreationRequestJson,
            @RequestParam(value = "photo", required = false) MultipartFile photo) {


        try {
            AlbumCreationRequest dto = objectMapper.readValue(AlbumCreationRequestJson, AlbumCreationRequest.class);
            String avatarPath = uploadIfNotEmpty(photo, DEFAULT_AVATAR);
            dto.setPhoto(avatarPath);
            Album album = albumService.createAlbum(dto);
            return ResponseEntity.ok(ResponseObject.<Album>builder()
                    .message("Album created successfully")
                    .result(album)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseObject.<Album>builder()
                    .message("Failed to create album")
                    .result(null)
                    .build());
        }
    }
}