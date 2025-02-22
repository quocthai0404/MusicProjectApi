package com.music.project.api.song.mapper;

import com.music.project.api.song.dto.request.SongCreationRequest;
import com.music.project.api.song.dto.response.SongResponse;
import com.music.project.entities.Song;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SongMapper {
    Song toSong(SongCreationRequest request);
    SongResponse toSongReponse(Song song);
}
