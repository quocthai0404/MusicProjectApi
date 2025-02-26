package com.music.project.api.playlist.mapper;

import com.music.project.api.playlist.dto.request.PlaylistCreationRequest;
import com.music.project.api.playlist.dto.request.PlaylistUpdateRequest;
import com.music.project.api.playlist.dto.response.PlaylistResponse;
import com.music.project.entities.Playlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PlaylistMapper {
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "playlistSongs", ignore = true)
    @Mapping(target = "id", ignore = true)
    Playlist toPlaylist(PlaylistCreationRequest request);
    
    PlaylistResponse toPlaylistResponse(Playlist playlist);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "playlistSongs", ignore = true)
    void updatePlaylist(@MappingTarget Playlist playlist, PlaylistUpdateRequest request);
}