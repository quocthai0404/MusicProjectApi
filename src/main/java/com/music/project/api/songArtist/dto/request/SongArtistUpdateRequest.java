package com.music.project.api.songArtist.dto.request;

import com.music.project.api.song.dto.request.SongCreationRequest;
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
public class SongArtistUpdateRequest {
    Set<Integer> artistIds;
}
