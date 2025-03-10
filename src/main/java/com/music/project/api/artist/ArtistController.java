package com.music.project.api.artist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.music.project.api.artist.dto.ArtistDTO;
import com.music.project.api.artist.dto.ArtistOptionDTO;
import com.music.project.api.artist.repository.ArtistRepository;
import com.music.project.api.user.repository.UserRepository;
import com.music.project.entities.ArtistInfo;
import com.music.project.entities.User;
import com.music.project.helpers.base.response.ResponseObject;
import com.music.project.helpers.cloudinary.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/artist")
public class ArtistController {

    private static final String DEFAULT_AVATAR = "https://res.cloudinary.com/dhee9ysz4/image/upload/v1741076584/ypj7xyshwjq9zhuazfq9.webp";
    private static final String DEFAULT_COVER = "https://res.cloudinary.com/dhee9ysz4/image/upload/v1741076673/u9ghxfako0emycevvqsu.jpg";
    private static final String FOLDER_NAME = "resources";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("findAllArtistOptions")
    public List<ArtistOptionDTO> findAllArtistOptions() {
       return artistRepository.findAllArtistOptions();
    }

    @GetMapping("test")
    public String test() {
        return "ok";
    }

    @PostMapping("add")
    public ResponseEntity<ResponseObject<String>> addArtist(@RequestParam("artist") String artistDTOJson,
                                                            @RequestParam(value = "avatar", required = false) MultipartFile avatar,
                                                            @RequestParam(value = "cover", required = false) MultipartFile cover) {
        try {

            ArtistDTO artistDTO = objectMapper.readValue(artistDTOJson, ArtistDTO.class);


            Optional<User> userOptional = userRepository.findById(artistDTO.getUserId());
            if (userOptional.isEmpty()) {
                return buildErrorResponse("User with ID " + artistDTO.getUserId() + " not found.");
            }


            String avatarPath = uploadIfNotEmpty(avatar, DEFAULT_AVATAR);
            String coverPath = uploadIfNotEmpty(cover, DEFAULT_COVER);


            ArtistInfo artistInfo = new ArtistInfo();
            artistInfo.setUser(userOptional.get());
            artistInfo.setAbout(artistDTO.getAbout());
            artistInfo.setStageName(artistDTO.getStageName());
            artistInfo.setAvatar(avatarPath);
            artistInfo.setCover(coverPath);
            artistInfo.setCreatedAt(new Date());
            artistInfo.setUpdateAt(new Date());

            artistRepository.save(artistInfo);

            return ResponseEntity.ok(new ResponseObject<>(200, "Add Artist Successfully", "success"));

        } catch (IOException e) {
            return buildErrorResponse("Invalid JSON format: " + e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse("Unexpected error: " + e.getMessage());
        }
    }


    private String uploadIfNotEmpty(MultipartFile file, String defaultPath) {
        if (file != null && !file.isEmpty()) {
            try {
                return (String) cloudinaryService.uploadFile(file, FOLDER_NAME).get("secure_url");
            } catch (Exception e) {
                return defaultPath;
            }
        }
        return defaultPath;
    }


    private ResponseEntity<ResponseObject<String>> buildErrorResponse(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseObject<>(400, "Bad Request: " + message, null));
    }
}
