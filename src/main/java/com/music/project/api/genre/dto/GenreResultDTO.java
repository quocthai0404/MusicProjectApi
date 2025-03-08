package com.music.project.api.genre.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenreResultDTO {
    private Integer songId;
    private Integer id;
    private String name;
}
