package com.music.project.api.genre.service;

import com.music.project.api.genre.dto.request.GenreCreationRequest;
import com.music.project.api.genre.dto.request.GenreUpdateRequest;
import com.music.project.api.genre.dto.response.GenreResponse;
import com.music.project.api.genre.mapper.GenreMapper;
import com.music.project.api.genre.repository.GenreRepository;
import com.music.project.api.songArtist.dto.request.SongArtistUpdateRequest;
import com.music.project.api.songArtist.dto.response.SongArtistResponse;
import com.music.project.entities.SongArtist;
import com.music.project.entities.SongArtistId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GenreService {
    GenreRepository repository;
    GenreMapper mapper;

    public GenreResponse create(GenreCreationRequest request) {
        var genre = mapper.toGenre(request);
        return mapper.toGenreResponse(repository.save(genre));
    }

    public GenreResponse update(int genreId, GenreUpdateRequest request) {
        var genre = repository.findById(genreId).orElseThrow(() -> new RuntimeException("genre not found"));

        mapper.updateGenre(genre, request);

        return mapper.toGenreResponse(repository.save(genre));


    }

    public List<GenreResponse> getAll() {
        var genres = repository.findAll();
        return genres.stream().map(mapper::toGenreResponse).toList();
    }
}
