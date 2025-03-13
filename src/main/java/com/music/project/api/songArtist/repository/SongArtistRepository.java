package com.music.project.api.songArtist.repository;


import com.music.project.api.artist.dto.ArtistResultDTO;
import com.music.project.entities.SongArtist;
import com.music.project.entities.SongArtistId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SongArtistRepository extends JpaRepository<SongArtist, SongArtistId>{
    void deleteBySong_Id(int songId);

    @Query("""
    SELECT new com.music.project.api.artist.dto.ArtistResultDTO(sa.song.id, ai.id, ai.stageName)
    FROM SongArtist sa
    JOIN sa.user u
    JOIN ArtistInfo ai ON ai.user.id = u.id
    WHERE sa.song.id IN :songIds
""")
    List<ArtistResultDTO> findBySongIds(@Param("songIds") List<Integer> songIds);

    @Modifying
    @Transactional
    @Query("DELETE FROM SongArtist sa WHERE sa.id.songId = :songId")
    void deleteBySongId(@Param("songId") Integer songId);



}
