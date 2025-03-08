package com.music.project.api.artist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArtistResultDTO {
    private Integer songId;
    private Integer id;
    private String stageName;
}
