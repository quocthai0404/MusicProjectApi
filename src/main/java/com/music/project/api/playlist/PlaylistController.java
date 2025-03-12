package com.music.project.api.playlist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.music.project.api.playlist.dto.PlaylistDTO;
import com.music.project.api.playlist.dto.request.PlaylistCreationRequest;
import com.music.project.api.playlist.dto.request.PlaylistUpdateRequest;
import com.music.project.api.playlist.dto.response.PlaylistResponse;
import com.music.project.api.playlist.service.PlaylistService;
import com.music.project.api.song.dto.SongDTO;
import com.music.project.helpers.base.response.ResponseObject;
import com.music.project.helpers.cloudinary.service.CloudinaryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("api/playlist")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlaylistController {
    PlaylistService playlistService;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private ObjectMapper objectMapper;
    private static final String FOLDER_NAME = "resources";

    private static final String DEFAULT_AVATAR = "https://res.cloudinary.com/dhee9ysz4/image/upload/v1741701543/dox72hqruib4lirmaynh.png";


//    @PostMapping("create")
//    ResponseObject<PlaylistResponse> create(@RequestBody PlaylistCreationRequest request) {
//        return ResponseObject.<PlaylistResponse>builder()
//                .result(playlistService.create(request))
//                .build();
//    }
//
//    @PutMapping("update/{playlistId}")
//    ResponseObject<PlaylistResponse> update(@PathVariable int playlistId, @RequestBody PlaylistUpdateRequest request) {
//        return ResponseObject.<PlaylistResponse>builder()
//                .result(playlistService.update(playlistId, request))
//                .build();
//    }
//
//    @GetMapping("getAll")
//    ResponseObject<List<PlaylistResponse>> getAll() {
//        return ResponseObject.<List<PlaylistResponse>>builder()
//                .result(playlistService.getAll())
//                .build();
//    }
//
//    @GetMapping("user/{userId}")
//    ResponseObject<List<PlaylistResponse>> getByUserId(@PathVariable int userId) {
//        return ResponseObject.<List<PlaylistResponse>>builder()
//                .result(playlistService.getByUserId(userId))
//                .build();
//    }
//
//    @GetMapping("{playlistId}")
//    ResponseObject<PlaylistResponse> getById(@PathVariable int playlistId) {
//        return ResponseObject.<PlaylistResponse>builder()
//                .result(playlistService.getById(playlistId))
//                .build();
//    }
//
//    @DeleteMapping("{playlistId}")
//    ResponseObject<String> delete(@PathVariable int playlistId) {
//        playlistService.delete(playlistId);
//        return ResponseObject.<String>builder()
//                .message("Playlist deleted successfully")
//                .build();
//    }

    @GetMapping
    public ResponseEntity<List<PlaylistDTO>> getAllPlaylists() {
        return ResponseEntity.ok(playlistService.getAllPlaylists());
    }

    @GetMapping("detail/{id}")
    public ResponseEntity<PlaylistDTO> getPlaylistDetails(@PathVariable Integer id) {
        return ResponseEntity.ok(playlistService.getPlaylistDetails(id));
    }

    @PostMapping("addSongToPlaylist")
    public ResponseEntity<ResponseObject<String>> addSongToPlaylist(@RequestParam Integer playListId,
                                                                    @RequestParam Integer songId) {
        try {
            playlistService.addSongToPlaylist(playListId, songId);
            return ResponseEntity.ok(ResponseObject.<String>builder()
                    .message("Song added to playlist successfully")
                    .result(null)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseObject.<String>builder()
                    .message("Failed to add song to playlist")
                    .result("Error: " + e.getMessage())
                    .build());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseObject<PlaylistDTO>> addPlaylist(
                                                                   @RequestParam("PlaylistCreationRequestDTOJson") String PlaylistCreationRequestDTOJson,
                                                                   @RequestParam(value = "photo", required = false) MultipartFile photo) {
        try {
            PlaylistCreationRequest dto = objectMapper.readValue(PlaylistCreationRequestDTOJson, PlaylistCreationRequest.class);
            String avatarPath = uploadIfNotEmpty(photo, DEFAULT_AVATAR);
            dto.setPhoto(avatarPath);
            PlaylistDTO playlistDTO = playlistService.addPlaylist(dto);

            return ResponseEntity.ok(ResponseObject.<PlaylistDTO>builder()
                    .message("Playlist created successfully")
                    .result(playlistDTO)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseObject.<PlaylistDTO>builder()
                    .message("Failed to create playlist")
                    .result(null)
                    .build());
        }
    }
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
}