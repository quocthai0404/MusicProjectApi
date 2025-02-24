package com.music.project.api.genre;

import com.music.project.api.genre.dto.request.GenreCreationRequest;
import com.music.project.api.genre.dto.request.GenreUpdateRequest;
import com.music.project.api.genre.dto.response.GenreResponse;
import com.music.project.api.genre.service.GenreService;
import com.music.project.helpers.base.response.ResponseObject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/genre")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GenreController {
    GenreService genreService;

    @PostMapping("create")
    ResponseObject<GenreResponse> create(@RequestBody GenreCreationRequest request) {
        System.out.println(request);
        return ResponseObject.<GenreResponse>builder()
                .result(genreService.create(request))
                .build();
    }

    @PutMapping("update/{genreId}")
    ResponseObject<GenreResponse> update(@PathVariable int genreId,@RequestBody GenreUpdateRequest request) {
        System.out.println(request);
        return ResponseObject.<GenreResponse>builder()
                .result(genreService.update(genreId, request))
                .build();
    }

    @GetMapping("getAll")
    ResponseObject<List<GenreResponse>> getAll() {
        return ResponseObject.<List<GenreResponse>>builder()
                .result(genreService.getAll())
                .build();
    }
}
