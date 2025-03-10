package com.music.project.api.artist.repository;

import com.music.project.api.artist.dto.ArtistOptionDTO;
import com.music.project.entities.ArtistInfo;
import com.music.project.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArtistRepository extends JpaRepository<ArtistInfo, Integer> {
    @Query("SELECT new com.music.project.api.artist.dto.ArtistOptionDTO(a.id, a.stageName, a.about) FROM ArtistInfo a")
    List<ArtistOptionDTO> findAllArtistOptions();
}
