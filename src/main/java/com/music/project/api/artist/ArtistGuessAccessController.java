package com.music.project.api.artist;

import com.music.project.api.artist.dto.ArtistOptionDTO;
import com.music.project.api.artist.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
