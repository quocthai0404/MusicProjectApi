package com.music.project.securities;

import com.music.project.helpers.base.response.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {


    ResponseEntity<ResponseObject<?>> signIn(SignInRequestDTO request);
}
