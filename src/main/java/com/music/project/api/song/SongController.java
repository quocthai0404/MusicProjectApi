package com.music.project.api.song;

import com.music.project.api.song.service.SongUploaderService;
import com.music.project.entities.Song;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/songs")
public class SongController {
    private final SongUploaderService songUploaderService;

    public SongController(SongUploaderService songUploaderService) {
        this.songUploaderService = songUploaderService;
    }



    @PostMapping("/uploads")
    public String uploadSong(@RequestParam("mp3") MultipartFile mp3File
                           )  {
//        Song song = new Song();
//        song.setUrlSource(songUploaderService.uploadMp3ToDropbox(mp3File));
//        song.setPhoto(songUploaderService.uploadImageToCloudinary(imageFile));
        try {
            return songUploaderService.uploadMp3ToDropbox(mp3File);
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
}
