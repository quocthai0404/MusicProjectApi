package com.music.project.securities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@AllArgsConstructor
public class SignInRequestDTO {

    private String email;
    private String password;

}
