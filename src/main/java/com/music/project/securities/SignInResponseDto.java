package com.music.project.securities;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SignInResponseDto {
    private String token;
    private String type = "Bearer";
    private Integer id;
    private String username;
    private String email;
    private String fullname;
    private List<String> roles;
}
