package com.example.android.services.account;

import com.example.android.dto.UserDto;

import java.util.List;

public interface AccountService {
    List<UserDto> getAllAccounts();

    UserDto getUserById(Long userId);
}
