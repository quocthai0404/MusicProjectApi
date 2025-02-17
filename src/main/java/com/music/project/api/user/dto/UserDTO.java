package com.music.project.api.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UserDTO {
    private Integer id;
    private String email;
    private String password;
    private String fullname;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dob;
    private String photo;
    private Boolean isActive;

    public UserDTO() {

    }

    public UserDTO(Integer id, String email, String password, String fullname, Date dob, String photo, Boolean isActive) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.dob = dob;
        this.photo = photo;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
