package com.example.android.controller;


import com.example.android.dto.AuthenticationReqestDto;
import com.example.android.dto.SignupRequestDtoDto;
import com.example.android.dto.UserDto;
import com.example.android.entity.User;
import com.example.android.repository.UserRepository;
import com.example.android.services.auth.AuthService;
import com.example.android.utils.JwtUtil;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    private final AuthService authService;

    @PostMapping("/authenticate")
    public void createAuthenticationToken(@RequestBody AuthenticationReqestDto authenticationReqestDto, HttpServletResponse response) throws IOException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationReqestDto.getUsername(), authenticationReqestDto.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername((authenticationReqestDto.getUsername()));
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        if (optionalUser.isPresent()) {
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("userId", optionalUser.get().getId());
            jsonResponse.addProperty("username", optionalUser.get().getName());
            jsonResponse.addProperty("role", optionalUser.get().getRole().toString());
            response.getWriter().write(jsonResponse.toString());
            //add header
            response.addHeader("Access-Control-Expose-Headers", "Authorization");
            response.addHeader("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, Origin, " +
                    "X-Requested-With, Content-Type, Accept, X-Custom-header");
            response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequestDtoDto signupRequestDto) {
        if (authService.hasUserWithEmail(signupRequestDto.getEmail())) {
            return new ResponseEntity<>("User already exists", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto userDto = authService.createUser(signupRequestDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
