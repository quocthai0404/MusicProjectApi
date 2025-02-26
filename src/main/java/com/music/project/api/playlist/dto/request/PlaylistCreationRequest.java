package com.music.project.api.playlist.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PlaylistCreationRequest {
    Integer userId;
    String name;
    String slug;
    String photo;
}