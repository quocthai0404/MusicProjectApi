package com.music.project.api.otp.repository;

import com.music.project.entities.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<Otp, Integer> {
}
