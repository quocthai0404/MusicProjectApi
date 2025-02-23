package com.music.project.api.user.service;

import com.music.project.api.email.service.EmailService;
import com.music.project.api.otp.repository.OtpRepository;
import com.music.project.api.role.repository.RoleRepository;
import com.music.project.api.user.dto.UserDTO;
import com.music.project.api.user.repository.UserRepository;
import com.music.project.api.userRole.repository.UserRoleRepository;
import com.music.project.entities.*;
import com.music.project.helpers.otp.OtpHelper;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;
    
    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private EmailService emailService;

    
    public User registerUser(UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt())); // Bạn cần mã hóa password
        user.setFullname(userDTO.getFullname());
        user.setDob(userDTO.getDob());
        user.setPhoto(userDTO.getPhoto());
        user.setIsActive(false);
        user.setCreatedAt(new Date());

        // luu User
        User savedUser = userRepository.save(user);

        // lấy role USER từ DB, role USER id = 3
        Role role = roleRepository.findById(3).orElseThrow(() -> new RuntimeException("Role not found"));

        // tao UserRole
        UserRole userRole = new UserRole();
        UserRoleId userRoleId = new UserRoleId();

        userRoleId.setUserId(savedUser.getId());  // dung id của user đã lưu
        userRoleId.setRoleId(role.getId());

        userRole.setId(userRoleId);
        userRole.setUser(savedUser);
        userRole.setRole(role);

        userRoleRepository.save(userRole);
        

        // send OTP

        String otpCode = OtpHelper.generateOtp(6);
        // gui mail
        emailService.sendEmailOtpRegister(otpCode, savedUser);

        //luu otp vào bảng otp
        Otp otp = new Otp();
        otp.setUser(savedUser);
        otp.setCode(otpCode);
        otp.setActionType("register");
        otp.setIsUsed(Boolean.FALSE);

        Date dNow = new Date( ); // Instantiate a Date object

        Date now = new Date();


        otp.setCreatedAt(now);
        otp.setExpiresAt(Date.from(Instant.now().plus(5, ChronoUnit.MINUTES)));
        //otp 5p het han

        otpRepository.save(otp);

        return user;
    }



}
