package com.music.project.api.album.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AlbumCreationRequest {
    String name;
    String slug;
    String photo;
    Date releaseDay;
}