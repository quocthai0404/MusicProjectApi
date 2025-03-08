package com.music.project.api.song;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.music.project.api.artist.dto.ArtistDTO;
import com.music.project.api.song.dto.SongDTO;
import com.music.project.api.song.dto.SongResultDTO;
import com.music.project.api.song.service.SongService;
import com.music.project.api.song.service.SongUploaderService;
import com.music.project.api.user.service.UserService;
import com.music.project.entities.ArtistInfo;
import com.music.project.entities.Song;
import com.music.project.entities.User;
import com.music.project.helpers.base.response.ResponseObject;
import com.music.project.helpers.cloudinary.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("api/songs")
public class SongController {
    private static final String DEFAULT_AVATAR = "https://res.cloudinary.com/dhee9ysz4/image/upload/v1741076584/ypj7xyshwjq9zhuazfq9.webp";

    private static final String FOLDER_NAME = "resources";
    @Autowired
    private SongUploaderService songUploaderService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SongService songService;



//    public SongController(SongUploaderService songUploaderService) {
//        this.songUploaderService = songUploaderService;
//    }



    @PostMapping("/uploads")
    public String uploadSong(@RequestParam("mp3") MultipartFile mp3File, SongDTO songDTO)  {
//        Song song = new Song();
//        song.setUrlSource(songUploaderService.uploadMp3ToDropbox(mp3File));
//        song.setPhoto(songUploaderService.uploadImageToCloudinary(imageFile));
        try {
            return songUploaderService.uploadMp3ToDropbox(mp3File);
        }catch (Exception e) {
            return e.getMessage();
        }
//
    }

    @PostMapping("/uploadstest")
    public String uploadSongTest(@RequestParam("test") String test
    )  {
//        Song song = new Song();
//        song.setUrlSource(songUploaderService.uploadMp3ToDropbox(mp3File));
//        song.setPhoto(songUploaderService.uploadImageToCloudinary(imageFile));
        try {
            return test;
        }catch (Exception e) {
            return e.getMessage();
        }
//        return songUploaderService.uploadMp3ToDropbox(mp3File);
    }

    @GetMapping("/test")
    public String test(
    ) {
//        Song song = new Song();
//        song.setUrlSource(songUploaderService.uploadMp3ToDropbox(mp3File));
//        song.setPhoto(songUploaderService.uploadImageToCloudinary(imageFile));
        return "ok";
    }

    @PostMapping("add")
    public ResponseEntity<ResponseObject<String>> addArtist(@RequestParam("song") String songDTOJson,
                                                            @RequestParam(value = "photo", required = false) MultipartFile photo,
                                                            @RequestParam(value = "mp3", required = false) MultipartFile mp3File) {
        try {
            SongDTO songDTO = objectMapper.readValue(songDTOJson, SongDTO.class);

            String avatarPath = uploadIfNotEmpty(photo, DEFAULT_AVATAR);
            String mp3Path = songUploaderService.uploadMp3ToDropbox(mp3File);

            songDTO.setPhoto(avatarPath);
            songDTO.setUrlSource(mp3Path);
            Song savedSong = songService.save(songDTO);
            return ResponseEntity.ok(new ResponseObject<>(200, "Add Song Successfully", "success"));


//            return ResponseEntity.ok(new ResponseObject<>(200, "Add Artist Successfully", "success"));

        } catch (JsonProcessingException e) {
            return buildErrorResponse("error " + e.getMessage());
        } catch (IOException e) {
//            throw new RuntimeException(e);
            return buildErrorResponse("error " + e.getMessage());
        }
        catch (Exception e) {
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

    @GetMapping("getAll")
    public ResponseEntity<Page<SongResultDTO>> getSongsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<SongResultDTO> songs = songService.getSongsPaged(page, size);
        return ResponseEntity.ok(songs);
    }
}
