package com.music.project.api.user;

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

    @PostMapping(value = "register", consumes = MimeTypeUtils.APPLICATION_JSON_VALUE,
            produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@RequestBody UserDTO userDTO) {
        try {
            return new ResponseEntity<Object>(new Object() {
                public User result = userService.registerUser(userDTO);
            }, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<Object>(new Object(){
                public String exception = e.getMessage();
            },HttpStatus.BAD_REQUEST);
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
}
