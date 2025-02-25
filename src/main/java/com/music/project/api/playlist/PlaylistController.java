package com.music.project.api.playlist;

import com.music.project.api.playlist.dto.request.PlaylistCreationRequest;
import com.music.project.api.playlist.dto.request.PlaylistUpdateRequest;
import com.music.project.api.playlist.dto.response.PlaylistResponse;
import com.music.project.api.playlist.service.PlaylistService;
import com.music.project.helpers.base.response.ResponseObject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/playlist")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlaylistController {
    PlaylistService playlistService;

    @PostMapping("create")
    ResponseObject<PlaylistResponse> create(@RequestBody PlaylistCreationRequest request) {
        return ResponseObject.<PlaylistResponse>builder()
                .result(playlistService.create(request))
                .build();
    }

    @PutMapping("update/{playlistId}")
    ResponseObject<PlaylistResponse> update(@PathVariable int playlistId, @RequestBody PlaylistUpdateRequest request) {
        return ResponseObject.<PlaylistResponse>builder()
                .result(playlistService.update(playlistId, request))
                .build();
    }

    @GetMapping("getAll")
    ResponseObject<List<PlaylistResponse>> getAll() {
        return ResponseObject.<List<PlaylistResponse>>builder()
                .result(playlistService.getAll())
                .build();
    }
    
    @GetMapping("user/{userId}")
    ResponseObject<List<PlaylistResponse>> getByUserId(@PathVariable int userId) {
        return ResponseObject.<List<PlaylistResponse>>builder()
                .result(playlistService.getByUserId(userId))
                .build();
    }

    @GetMapping("{playlistId}")
    ResponseObject<PlaylistResponse> getById(@PathVariable int playlistId) {
        return ResponseObject.<PlaylistResponse>builder()
                .result(playlistService.getById(playlistId))
                .build();
    }

    @DeleteMapping("{playlistId}")
    ResponseObject<String> delete(@PathVariable int playlistId) {
        playlistService.delete(playlistId);
        return ResponseObject.<String>builder()
                .message("Playlist deleted successfully")
                .build();
    }
}