package com.music.project.api.songGenre.repository;

import com.music.project.entities.SongGenre;
import com.music.project.entities.SongGenreId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongGenreRepository extends JpaRepository<SongGenre, SongGenreId> {

}
