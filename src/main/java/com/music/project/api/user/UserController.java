package com.music.project.api.user;

import com.music.project.api.otp.dto.OtpRequestDto;
import com.music.project.api.otp.result.VerificationResult;
import com.music.project.api.otp.service.OtpService;
import com.music.project.api.user.dto.UserDTO;
import com.music.project.api.user.service.UserService;
import com.music.project.entities.User;
import com.music.project.helpers.base.response.ResponseObject;
import com.music.project.securities.AuthService;
import com.music.project.securities.SignInRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
    @RequestMapping("api/users")
public class UserController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private OtpService otpService;

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
    public ResponseEntity<ResponseObject<?>> signIn(@RequestBody SignInRequestDTO signInRequestDto) {
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
}
