package com.example.android.services.account;

import com.example.android.dto.UserDto;
import com.example.android.entity.User;
import com.example.android.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class AccountServiceImpl implements AccountService{
    private final UserRepository userRepository;


    @Override
    public List<UserDto> getAllAccounts() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(User::getUserDto).collect(Collectors.toList());
    }


    @Override
    public UserDto getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            UserDto userDto = optionalUser.get().getUserDto();
            return userDto;
        }
        return null;

    }
}
