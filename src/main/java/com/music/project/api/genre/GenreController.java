package com.music.project.api.genre;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.music.project.api.artist.dto.ArtistDTO;
import com.music.project.api.artist.dto.ArtistOptionDTO;
import com.music.project.api.genre.dto.GenreOptionDTO;
import com.music.project.api.genre.dto.request.GenreCreationRequest;
import com.music.project.api.genre.dto.request.GenreUpdateRequest;
import com.music.project.api.genre.dto.response.GenreResponse;
import com.music.project.api.genre.repository.GenreRepository;
import com.music.project.api.genre.service.GenreService;
import com.music.project.entities.ArtistInfo;
import com.music.project.entities.Genre;
import com.music.project.entities.User;
import com.music.project.helpers.base.response.ResponseObject;
import com.music.project.helpers.cloudinary.service.CloudinaryService;
import com.music.project.helpers.string.StringHelper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/genre")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GenreController {
    private static final String DEFAULT_AVATAR = "https://res.cloudinary.com/dhee9ysz4/image/upload/v1741076584/ypj7xyshwjq9zhuazfq9.webp";
    private static final String FOLDER_NAME = "resources";
    @Autowired
    private CloudinaryService cloudinaryService;
    GenreService genreService;
    @Autowired
    public GenreRepository repo;
    @Autowired
    private ObjectMapper objectMapper;

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

    @PostMapping("add")
    public ResponseEntity<ResponseObject<String>> addGenre(@RequestParam("GenreCreationRequestJson") String GenreCreationRequestJson,
                                                            @RequestParam(value = "photo", required = false) MultipartFile photo
                                                            ) {
        try {

            GenreCreationRequest dto = objectMapper.readValue(GenreCreationRequestJson, GenreCreationRequest.class);




            String photoPath = uploadIfNotEmpty(photo, DEFAULT_AVATAR);

            Genre genre = new Genre();
            genre.setName(dto.getName());
            genre.setPhoto(photoPath);
            genre.setSlug(StringHelper.toSlug(dto.getName()));



            repo.save(genre);

            return ResponseEntity.ok(new ResponseObject<>(200, "Add Genre Successfully", "success"));

        } catch (Exception e) {
            return buildErrorResponse("Unexpected error: " + e.getMessage());
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
    private ResponseEntity<ResponseObject<String>> buildErrorResponse(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseObject<>(400, "Bad Request: " + message, null));
    }
}
