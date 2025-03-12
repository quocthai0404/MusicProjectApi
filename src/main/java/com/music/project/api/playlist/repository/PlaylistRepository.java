package com.music.project.api.playlist.repository;

import com.music.project.api.playlist.dto.PlaylistDTO;
import com.music.project.entities.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {
    List<Playlist> findByUserId(Integer userId);

    @Query("SELECT new com.music.project.api.playlist.dto.PlaylistDTO(p.id, p.user.id, p.name, p.slug, p.photo) FROM Playlist p")
    List<PlaylistDTO> findAllPlaylistDTOs();

    @Query("SELECT p FROM Playlist p LEFT JOIN FETCH p.playlistSongs ps LEFT JOIN FETCH ps.song WHERE p.id = :playlistId")
    Optional<Playlist> findPlaylistWithSongs(@Param("playlistId") Integer playlistId);
}