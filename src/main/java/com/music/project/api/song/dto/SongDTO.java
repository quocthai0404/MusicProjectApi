package com.music.project.api.song.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.music.project.entities.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class SongDTO {
    String name;
    String slug;
    String urlSource;
    String photo;
    @JsonFormat(pattern = "dd/MM/yyyy")
    Date releaseDay;
    List<Integer> artistIds;
    List<Integer> genreIds;

//    Set<SongArtist> songArtists = new HashSet<SongArtist>(0);
//    Set<SongGenre> songGenres = new HashSet<SongGenre>(0);

}

