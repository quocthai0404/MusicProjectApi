package com.music.project.api.songArtist.mapper;

import com.music.project.api.song.dto.request.SongCreationRequest;
import com.music.project.api.song.dto.response.SongResponse;
import com.music.project.api.songArtist.dto.request.SongArtistCreationRequest;
import com.music.project.api.songArtist.dto.response.SongArtistResponse;
import com.music.project.entities.Song;
import com.music.project.entities.SongArtist;
import com.music.project.entities.SongArtistId;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface SongArtistMapper {
    SongArtistId toSongArtist(SongArtistCreationRequest request);
    List<SongArtistResponse> toSongArtistReponse(List<SongArtist> songArtists);
}