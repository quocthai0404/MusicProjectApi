package com.music.project.api.otp.repository;

import com.music.project.entities.Otp;
import com.music.project.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OtpRepository extends JpaRepository<Otp, Integer> {
    @Query("SELECT o FROM Otp o WHERE o.code = :otp AND o.user.email = :email AND o.isUsed = false AND o.actionType = 'register'")
    Otp findOtpRegister(@Param("otp") String otp, @Param("email") String email);
}
