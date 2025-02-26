package com.music.project.api.album.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AlbumResponse {
    Integer id;
    String name;
    String slug;
    String photo;
    Date releaseDay;
    Date createdAt;
    Date updateAt;
}