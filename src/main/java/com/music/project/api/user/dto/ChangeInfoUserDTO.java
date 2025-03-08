package com.music.project.api.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeInfoUserDTO {
    private String email;
    private String password;
    private String rePassword;
    private String fullname;


    public ChangeInfoUserDTO() {

    }
    public ChangeInfoUserDTO(String email, String password, String rePassword, String fullname) {
        this.email = email;
        this.password = password;
        this.rePassword = rePassword;
        this.fullname = fullname;
    }


}
