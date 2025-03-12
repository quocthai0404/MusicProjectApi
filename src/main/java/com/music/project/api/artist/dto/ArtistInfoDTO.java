package com.music.project.api.artist.dto;

import com.music.project.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArtistInfoDTO {
    private Integer id;
    private Integer userId;
    private String stageName;
    private String about;
    private String avatar;
    private String cover;

}
