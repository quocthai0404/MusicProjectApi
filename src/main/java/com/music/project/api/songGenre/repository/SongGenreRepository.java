package com.music.project.api.songGenre.repository;

import com.music.project.api.genre.dto.GenreResultDTO;
import com.music.project.entities.SongGenre;
import com.music.project.entities.SongGenreId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SongGenreRepository extends JpaRepository<SongGenre, SongGenreId> {
    @Query("""
    SELECT new com.music.project.api.genre.dto.GenreResultDTO(sg.song.id, g.id, g.name)
    FROM SongGenre sg
    JOIN sg.genre g
    WHERE sg.song.id IN :songIds
""")
    List<GenreResultDTO> findBySongIds(@Param("songIds") List<Integer> songIds);

    @Modifying
    @Transactional
    @Query("DELETE FROM SongGenre sg WHERE sg.id.songId = :songId")
    void deleteBySongId(@Param("songId") Integer songId);
}
