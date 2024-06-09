package com.example.android.services.auth;

import com.example.android.dto.SignupRequestDtoDto;
import com.example.android.dto.UserDto;


public interface AuthService {

    public UserDto createUser(SignupRequestDtoDto signupRequest);

    public boolean hasUserWithEmail(String email);
}
