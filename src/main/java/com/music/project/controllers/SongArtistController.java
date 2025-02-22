package com.music.project.controllers;

import com.music.project.api.songArtist.dto.request.SongArtistCreationRequest;
import com.music.project.api.songArtist.dto.request.SongArtistUpdateRequest;
import com.music.project.api.songArtist.dto.response.SongArtistResponse;
import com.music.project.api.songArtist.service.SongArtistService;
import com.music.project.helpers.base.response.ResponseObject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/song-artist")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SongArtistController {
    SongArtistService songArtistService;

    @PostMapping("create")
    ResponseObject<List<SongArtistResponse>> create(@RequestBody SongArtistCreationRequest request) {
        System.out.println(request);
        return ResponseObject.<List<SongArtistResponse>>builder()
                .result(songArtistService.create(request))
                .build();
    }

    @PutMapping("update/{songId}")
    ResponseObject<List<SongArtistResponse>> update(@PathVariable int songId,@RequestBody SongArtistUpdateRequest request) {
        System.out.println(request);
        return ResponseObject.<List<SongArtistResponse>>builder()
                .result(songArtistService.update(songId, request))
                .build();
    }
}
