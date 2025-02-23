package com.music.project.api.songArtist.repository;


import com.music.project.entities.SongArtist;
import com.music.project.entities.SongArtistId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongArtistRepository extends JpaRepository<SongArtist, SongArtistId>{
    void deleteBySong_Id(int songId);
}
