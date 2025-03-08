package com.music.project.api.song.dto;

import com.music.project.api.artist.dto.ArtistDTO;
import com.music.project.api.artist.dto.ArtistResultDTO;
import com.music.project.api.genre.dto.GenreResultDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SongResultDTO {
    private Integer id;
    private String name;
    private String slug;
    private String urlSource;
    private String photo;
    private Date releaseDay;
    private List<ArtistResultDTO> artists;
    private List<GenreResultDTO> genres;

    public SongResultDTO(Integer id, String name, String slug, String urlSource, String photo, Date releaseDay) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.urlSource = urlSource;
        this.photo = photo;
        this.releaseDay = releaseDay;
    }
}
