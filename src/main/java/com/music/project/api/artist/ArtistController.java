package com.music.project.api.artist;

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
import org.springframework.web.bind.annotation.*;

@RestController
    @RequestMapping("api/artist")
public class ArtistController {

    @GetMapping(value = "test")
    public String test() {
        return "ok";
    }




}
