package com.music.project.api.artist.dto;

import com.music.project.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ArtistOptionDTO {
    private Integer id;
    private String stageName;
    private String about;
}
