package com.music.project.api.playlist.dto;

import com.music.project.api.song.dto.SongResultDTO;
import com.music.project.entities.User;
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
public class PlaylistDTO {
    private Integer id;
    private Integer userId;
    private String name;
    private String slug;
    private String photo;
    private List<SongResultDTO> songs;

    public PlaylistDTO(Integer id, Integer userId, String name, String slug, String photo) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.slug = slug;
        this.photo = photo;
    }
}
