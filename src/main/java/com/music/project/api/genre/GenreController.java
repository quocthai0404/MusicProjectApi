package com.music.project.api.genre;

import com.music.project.api.artist.dto.ArtistOptionDTO;
import com.music.project.api.genre.dto.GenreOptionDTO;
import com.music.project.api.genre.dto.request.GenreCreationRequest;
import com.music.project.api.genre.dto.request.GenreUpdateRequest;
import com.music.project.api.genre.dto.response.GenreResponse;
import com.music.project.api.genre.repository.GenreRepository;
import com.music.project.api.genre.service.GenreService;
import com.music.project.helpers.base.response.ResponseObject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/genre")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GenreController {
    GenreService genreService;
    @Autowired
    public GenreRepository repo;

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


    @GetMapping("findAllGenreOptions")
    public List<GenreOptionDTO> findAllGenreOptions() {
        return repo.findAllGenreOptions();
    }
}
