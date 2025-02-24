package com.music.project.securities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignInRequestDTO {

    private String email;
    private String password;
}
