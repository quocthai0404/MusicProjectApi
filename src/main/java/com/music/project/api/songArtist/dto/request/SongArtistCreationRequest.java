package com.music.project.api.songArtist.dto.request;

import com.music.project.api.song.dto.request.SongCreationRequest;
import com.music.project.entities.Song;
import com.music.project.entities.SongArtist;
import com.music.project.entities.SongArtistId;
import com.music.project.entities.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class SongArtistCreationRequest {
    SongCreationRequest song;
    Set<Integer> artistIds;
}
