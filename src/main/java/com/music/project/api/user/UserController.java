package com.music.project.api.user;

import com.music.project.api.otp.dto.OtpRequestDto;
import com.music.project.api.otp.repository.OtpRepository;
import com.music.project.api.otp.result.VerificationResult;
import com.music.project.api.otp.service.OtpService;
import com.music.project.api.user.dto.ChangeInfoUserDTO;
import com.music.project.api.user.dto.ResetPasswordDTO;
import com.music.project.api.user.dto.UserDTO;
import com.music.project.api.user.repository.UserRepository;
import com.music.project.api.user.service.UserService;
import com.music.project.entities.Otp;
import com.music.project.entities.User;
import com.music.project.helpers.base.response.ResponseObject;
import com.music.project.helpers.otp.OtpHelper;
import com.music.project.securities.AuthService;
import com.music.project.securities.SignInRequestDTO;
import com.music.project.securities.SignInResponseDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@RestController
    @RequestMapping("api/users")
public class UserController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private OtpService otpService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpRepository otpRepository;

    @PostMapping(value = "register", consumes = MimeTypeUtils.APPLICATION_JSON_VALUE,
            produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseObject<User>> create(@RequestBody UserDTO userDTO) {
        try {
            User registeredUser = userService.registerUser(userDTO);
            ResponseObject<User> responseObject = new ResponseObject<>(200, "User registered successfully", registeredUser);
            return ResponseEntity.ok(responseObject);
        } catch (Exception e) {
            ResponseObject<User> responseObject = new ResponseObject<>(400, e.getMessage(), null);
            return ResponseEntity.badRequest().body(responseObject);
        }
    }

    @PostMapping(value = "sign-in", consumes = MimeTypeUtils.APPLICATION_JSON_VALUE,
            produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signIn(@RequestBody SignInRequestDTO signInRequestDto) {
        try {
            return ResponseEntity.ok(authService.signIn(signInRequestDto).getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject<>(400,"Bad Request: " + e.getMessage(), null));
        }
    }

    @PostMapping(value = "active-account", consumes = MimeTypeUtils.APPLICATION_JSON_VALUE,
            produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseObject<Void>> active(@RequestBody OtpRequestDto otpRequestDto) {
        VerificationResult result = otpService.verifyOtp(otpRequestDto);

        switch (result) {
            case SUCCESS:
                return ResponseEntity.ok(ResponseObject.<Void>builder()
                        .message("Account activated successfully.")
                        .build());
            case OTP_NOT_FOUND:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseObject.<Void>builder()
                                .code(400)
                                .message("OTP not found.")
                                .build());
            case OTP_EXPIRED:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseObject.<Void>builder()
                                .code(400)
                                .message("OTP expired.")
                                .build());
            case USER_NOT_FOUND:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseObject.<Void>builder()
                                .code(400)
                                .message("User not found.")
                                .build());
            case DATABASE_ERROR:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseObject.<Void>builder()
                                .code(500)
                                .message("Database error occurred.")
                                .build());
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseObject.<Void>builder()
                                .code(500)
                                .message("Unknown error occurred.")
                                .build());
        }
    }

    @PostMapping(value = "change-info",
            consumes = MimeTypeUtils.APPLICATION_JSON_VALUE,
            produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseObject<SignInResponseDto>> changeInfo(@RequestBody ChangeInfoUserDTO changeInfoUserDTO) {
        try {
            SignInResponseDto updatedUser = userService.changeInfoUser(changeInfoUserDTO);
            ResponseObject<SignInResponseDto> responseObject = new ResponseObject<>(200, "User info updated successfully", updatedUser);
            return ResponseEntity.ok(responseObject);
        } catch (Exception e) {
            ResponseObject<SignInResponseDto> responseObject = new ResponseObject<>(400, e.getMessage(), null);
            return ResponseEntity.badRequest().body(responseObject);
        }
    }

    @PostMapping("/request-otp")
    public ResponseEntity<ResponseObject<String>> requestOtp(@RequestParam String email) {
        try {
            userService.requestOtp(email);
            ResponseObject<String> responseObject = new ResponseObject<>(200, "OTP has been sent to your email.", null);
            return ResponseEntity.ok(responseObject);
        } catch (RuntimeException e) {
            ResponseObject<String> responseObject = new ResponseObject<>(400, e.getMessage(), null);
            return ResponseEntity.badRequest().body(responseObject);
        }
    }

    @Transactional
    @PostMapping(value = "verify-reset-password", consumes = MimeTypeUtils.APPLICATION_JSON_VALUE,
            produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseObject<String>> verifyResetPassword(@RequestBody OtpRequestDto otpRequestDto) {
        VerificationResult result = otpService.verifyOtpForgetPassword(otpRequestDto);
        Optional<User> optionalUser = userRepository.findByEmail(otpRequestDto.email);
        User user = null;
        if(optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseObject.<String>builder()
                            .code(400)
                            .message("user not found.")
                            .build());
        }
        user = optionalUser.get();
        switch (result) {
            case SUCCESS:
                String otpSendClientToResetPassword =  OtpHelper.generateOtp(6);
                Otp otp = new Otp();
                otp.setUser(user);
                otp.setCode(otpSendClientToResetPassword);
                otp.setActionType("resetpassword");
                otp.setIsUsed(false);
                otp.setCreatedAt(new Date());
                otp.setExpiresAt(Date.from(Instant.now().plus(5, ChronoUnit.MINUTES)));

                otpRepository.save(otp);
                return ResponseEntity.ok(ResponseObject.<String>builder()
                        .message("verify otp successfully.")
                        .result(otpSendClientToResetPassword)
                        .build());
            case OTP_NOT_FOUND:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseObject.<String>builder()
                                .code(400)
                                .message("OTP not found.")
                                .build());
            case OTP_EXPIRED:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseObject.<String>builder()
                                .code(400)
                                .message("OTP expired.")
                                .build());
            case USER_NOT_FOUND:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseObject.<String>builder()
                                .code(400)
                                .message("User not found.")
                                .build());
            case DATABASE_ERROR:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseObject.<String>builder()
                                .code(500)
                                .message("Database error occurred.")
                                .build());
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseObject.<String>builder()
                                .code(500)
                                .message("Unknown error occurred.")
                                .build());
        }
    }

    @Transactional
    @PostMapping(value = "reset-password", consumes = MimeTypeUtils.APPLICATION_JSON_VALUE,
            produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseObject<String>> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {

        VerificationResult result = otpService.verifyOtpResetPassword(resetPasswordDTO);
        if (result != VerificationResult.SUCCESS) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject<>(400, getErrorMessage(result), null));
        }


        User user = userRepository.findByEmail(resetPasswordDTO.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));


        if (!resetPasswordDTO.getPassword().equals(resetPasswordDTO.getRePassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject<>(400, "Your password doesn't match.", null));
        }


        user.setPassword(BCrypt.hashpw(resetPasswordDTO.getPassword(), BCrypt.gensalt()));
        userRepository.save(user);


        return ResponseEntity.ok(new ResponseObject<>(200, "Reset Password Successfully, You Can Log In Now.", null));
    }


    private String getErrorMessage(VerificationResult result) {
        return switch (result) {
            case OTP_NOT_FOUND -> "OTP not found.";
            case OTP_EXPIRED -> "OTP expired.";
            case USER_NOT_FOUND -> "User not found.";
            case NOT_MATCH_PASSWORD -> "Your password doesn't match.";
            case DATABASE_ERROR -> "Database error occurred.";
            default -> "Unknown error occurred.";
        };
    }

    @GetMapping("details/{id}")
    public ResponseEntity<ResponseObject<User>> getUserDetails(@PathVariable Integer id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(new ResponseObject<>(200, "User found", user)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseObject<>(404, "User not found with ID: " + id, null)));
    }


}
