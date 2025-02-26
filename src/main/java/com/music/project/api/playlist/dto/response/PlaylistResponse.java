package com.music.project.api.playlist.dto.response;

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
public class PlaylistResponse {
    Integer id;
    User user;
    String name;
    String slug;
    String photo;
    Date createdAt;
    Date updateAt;
}