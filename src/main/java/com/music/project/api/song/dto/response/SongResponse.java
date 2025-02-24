package com.music.project.api.song.dto.response;

import com.music.project.entities.Album;
import lombok.*;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SongResponse {
     Integer id;
     Album album;
     String name;
     String slug;
     String urlSource;
     String photo;
     Date releaseDay;
     Date createdAt;
     Date updateAt;
}
