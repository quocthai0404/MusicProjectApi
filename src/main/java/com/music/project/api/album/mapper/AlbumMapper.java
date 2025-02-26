package com.music.project.api.album.mapper;

import com.music.project.api.album.dto.request.AlbumCreationRequest;
import com.music.project.api.album.dto.request.AlbumUpdateRequest;
import com.music.project.api.album.dto.response.AlbumResponse;
import com.music.project.entities.Album;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AlbumMapper {
    Album toAlbum(AlbumCreationRequest request);
    AlbumResponse toAlbumResponse(Album album);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "albumUsers", ignore = true)
    @Mapping(target = "songs", ignore = true)
    void updateAlbum(@MappingTarget Album album, AlbumUpdateRequest albumUpdateRequest);
}