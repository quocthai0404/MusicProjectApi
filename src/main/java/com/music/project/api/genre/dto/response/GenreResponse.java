package com.music.project.api.genre.dto.response;

import com.music.project.entities.Song;
import com.music.project.entities.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GenreResponse {
    private Integer id;
    private String name;
    private String slug;
    private String photo;
}