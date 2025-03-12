package com.music.project.api.playlistSong.repository;

import com.music.project.entities.PlaylistSong;
import com.music.project.entities.PlaylistSongId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlaylistSongRepository extends JpaRepository<PlaylistSong, PlaylistSongId> {
    boolean existsById(PlaylistSongId id);
    @Query("SELECT COUNT(ps) > 0 FROM PlaylistSong ps WHERE ps.playlist.id = :playlistId AND ps.song.id = :songId")
    boolean existsByPlaylistIdAndSongId(@Param("playlistId") Integer playlistId, @Param("songId") Integer songId);
}
