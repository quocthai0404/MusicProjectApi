package com.music.project.api.albumUser.repository;

import com.music.project.entities.AlbumUser;
import com.music.project.entities.AlbumUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumUserRepository extends JpaRepository<AlbumUser, AlbumUserId> {
}
