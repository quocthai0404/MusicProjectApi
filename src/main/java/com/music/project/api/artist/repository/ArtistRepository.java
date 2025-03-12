package com.music.project.api.artist.repository;

import com.music.project.api.artist.dto.ArtistInfoDTO;
import com.music.project.api.artist.dto.ArtistOptionDTO;
import com.music.project.entities.ArtistInfo;
import com.music.project.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArtistRepository extends JpaRepository<ArtistInfo, Integer> {
    @Query("SELECT new com.music.project.api.artist.dto.ArtistOptionDTO(a.id, a.stageName, a.about) FROM ArtistInfo a")
    List<ArtistOptionDTO> findAllArtistOptions();

    @Query("SELECT new com.music.project.api.artist.dto.ArtistInfoDTO(a.id, a.user.id, a.stageName, a.about, a.avatar, a.cover) FROM ArtistInfo a")
    List<ArtistInfoDTO> findAllArtistDTOs();

    @Query("SELECT new com.music.project.api.artist.dto.ArtistInfoDTO(a.id, a.user.id, a.stageName, a.about, a.avatar, a.cover) " +
            "FROM ArtistInfo a WHERE a.id = :id")
    ArtistInfoDTO findArtistDTOById(@Param("id") int id);


}
