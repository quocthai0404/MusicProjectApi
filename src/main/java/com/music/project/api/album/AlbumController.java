package com.music.project.api.album;

import com.music.project.api.album.dto.request.AlbumCreationRequest;
import com.music.project.api.album.dto.request.AlbumUpdateRequest;
import com.music.project.api.album.dto.response.AlbumResponse;
import com.music.project.api.album.service.AlbumService;
import com.music.project.helpers.base.response.ResponseObject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/album")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AlbumController {
    AlbumService albumService;

    @PostMapping("create")
    ResponseObject<AlbumResponse> create(@RequestBody AlbumCreationRequest request) {
        return ResponseObject.<AlbumResponse>builder()
                .result(albumService.create(request))
                .build();
    }

    @PutMapping("update/{albumId}")
    ResponseObject<AlbumResponse> update(@PathVariable int albumId, @RequestBody AlbumUpdateRequest request) {
        return ResponseObject.<AlbumResponse>builder()
                .result(albumService.update(albumId, request))
                .build();
    }

    @GetMapping("getAll")
    ResponseObject<List<AlbumResponse>> getAll() {
        return ResponseObject.<List<AlbumResponse>>builder()
                .result(albumService.getAll())
                .build();
    }

    @GetMapping("{albumId}")
    ResponseObject<AlbumResponse> getById(@PathVariable int albumId) {
        return ResponseObject.<AlbumResponse>builder()
                .result(albumService.getById(albumId))
                .build();
    }

    @DeleteMapping("{albumId}")
    ResponseObject<String> delete(@PathVariable int albumId) {
        albumService.delete(albumId);
        return ResponseObject.<String>builder()
                .message("Album deleted successfully")
                .build();
    }
}