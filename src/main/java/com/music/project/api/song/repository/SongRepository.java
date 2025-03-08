package com.music.project.api.song.repository;

import com.music.project.api.song.dto.SongDTO;
import com.music.project.api.song.dto.SongResultDTO;
import com.music.project.entities.Song;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {
    @Query("""
        SELECT new com.music.project.api.song.dto.SongResultDTO(
            s.id, s.name, s.slug, s.urlSource, s.photo, s.releaseDay
        )
        FROM Song s
    """)
    Page<SongResultDTO> findAllSongs(Pageable pageable);
}
