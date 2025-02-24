package com.music.project.api.genre.mapper;

import com.music.project.api.genre.dto.request.GenreCreationRequest;
import com.music.project.api.genre.dto.request.GenreUpdateRequest;
import com.music.project.api.genre.dto.response.GenreResponse;
import com.music.project.entities.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    Genre toGenre(GenreCreationRequest request);
    GenreResponse toGenreResponse(Genre genre);

    @Mapping(target = "songGenres", ignore = true) // Bỏ qua danh sách songGenres
    void updateGenre(@MappingTarget  Genre genre, GenreUpdateRequest genreUpdateRequest);
}
