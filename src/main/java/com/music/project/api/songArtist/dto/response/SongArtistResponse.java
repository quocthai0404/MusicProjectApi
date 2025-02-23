package com.music.project.api.songArtist.dto.response;

import com.music.project.entities.Song;
import com.music.project.entities.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SongArtistResponse {
     Song song;
     User user;
}
