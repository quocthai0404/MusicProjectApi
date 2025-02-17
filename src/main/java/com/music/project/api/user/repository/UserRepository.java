package com.music.project.api.user.repository;

import com.music.project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer>, JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
