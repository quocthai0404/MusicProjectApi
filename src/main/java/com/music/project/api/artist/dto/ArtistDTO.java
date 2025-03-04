package com.music.project.api.artist.dto;

import com.music.project.entities.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArtistDTO {
    private Integer userId;
    private String stageName;
    private String about;



    public ArtistDTO(int userId, String stageName, String about) {
        this.userId = userId;
        this.stageName = stageName;
        this.about = about;

    }




}
