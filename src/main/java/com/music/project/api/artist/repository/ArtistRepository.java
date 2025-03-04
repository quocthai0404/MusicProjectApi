package com.music.project.api.artist.repository;

import com.music.project.entities.ArtistInfo;
import com.music.project.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<ArtistInfo, Integer> {

}
