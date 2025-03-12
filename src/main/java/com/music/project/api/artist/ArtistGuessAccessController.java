package com.music.project.api.artist;

import com.music.project.api.artist.dto.ArtistInfoDTO;
import com.music.project.api.artist.dto.ArtistOptionDTO;
import com.music.project.api.artist.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/artist-guess-access")
public class ArtistGuessAccessController {
    @Autowired
    private ArtistRepository artistRepository;
    @GetMapping("findAllArtistOptions")
    public List<ArtistOptionDTO> findAllArtistOptions() {
        return artistRepository.findAllArtistOptions();
    }

    @GetMapping("findAllArtistInfo")
    public List<ArtistInfoDTO> findAllArtistInfo() {
        return artistRepository.findAllArtistDTOs();
    }

    @GetMapping("findByArtistId/{id}")
    public ArtistInfoDTO findByArtistId(@PathVariable int id) {
        return artistRepository.findArtistDTOById(id);
    }

    @GetMapping("test")
    public String test() {
        return "ok";
    }
}
