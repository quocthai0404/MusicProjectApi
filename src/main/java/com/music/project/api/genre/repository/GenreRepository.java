package com.music.project.api.genre.repository;

import com.music.project.api.genre.dto.GenreOptionDTO;
import com.music.project.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    @Query("SELECT new com.music.project.api.genre.dto.GenreOptionDTO(g.id, g.name) FROM Genre g")
    List<GenreOptionDTO> findAllGenreOptions();
}
