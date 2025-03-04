package com.music.project.api.artist.dto;

import com.music.project.entities.User;
import lombok.Builder;

public class ArtistDTO {
    private int userId;
    private String stageName;
    private String about;
    private String avatar = "https://res.cloudinary.com/dhee9ysz4/image/upload/v1741076584/ypj7xyshwjq9zhuazfq9.webp";

    private String cover = "https://res.cloudinary.com/dhee9ysz4/image/upload/v1741076673/u9ghxfako0emycevvqsu.jpg";



    public ArtistDTO(int userId, String stageName, String about, String avatar, String cover) {
        this.userId = userId;
        this.stageName = stageName;
        this.about = about;
        this.avatar = avatar;
        this.cover = cover;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
