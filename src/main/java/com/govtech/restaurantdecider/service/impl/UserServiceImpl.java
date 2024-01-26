package com.govtech.restaurantdecider.service.impl;

import com.govtech.restaurantdecider.config.PasswordConfig;
import com.govtech.restaurantdecider.config.UserAuthenticationProvider;
import com.govtech.restaurantdecider.dto.LoginRequest;
import com.govtech.restaurantdecider.dto.LoginResponse;
import com.govtech.restaurantdecider.entity.User;
import com.govtech.restaurantdecider.exception.ValidationException;
import com.govtech.restaurantdecider.repository.UserRepository;
import com.govtech.restaurantdecider.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordConfig passwordConfig;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.username())
                .orElseThrow(() -> new ValidationException("Unknown user", HttpStatus.NOT_FOUND));
        if (passwordConfig.passwordEncoder().matches(CharBuffer.wrap(loginRequest.password()), user.getPassword())) {
            return new LoginResponse(user.getFirstName(), user.getLastName(), userAuthenticationProvider.createToken(user));
        }
        throw new ValidationException("Invalid password", HttpStatus.BAD_REQUEST);
    }
}
