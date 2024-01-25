package com.govtech.restaurantdecider.service.impl;

import com.govtech.restaurantdecider.config.PasswordConfig;
import com.govtech.restaurantdecider.config.UserAuthenticationProvider;
import com.govtech.restaurantdecider.dto.LoginRequest;
import com.govtech.restaurantdecider.dto.LoginResponse;
import com.govtech.restaurantdecider.dto.UserDTO;
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
            return new LoginResponse(user.getFirstName(), user.getLastName(), userAuthenticationProvider.createToken(user), null);
        }
        throw new ValidationException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    @Override
    public UserDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ValidationException("Unknown user", HttpStatus.NOT_FOUND));
        return UserDTO.valueOf(user);
    }

    @Override
    public void register() {
        //Optional<User> optionalUser = userRepository.findByUsername(userDto.getLogin());

        /*if (optionalUser.isPresent()) {
            throw new ValidationException("Login already exists", HttpStatus.BAD_REQUEST);
        }*/

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("admin");
        user.setPassword(passwordConfig.passwordEncoder().encode("admin1234"));

        User savedUser = userRepository.save(user);

        //return userMapper.toUserDto(savedUser);
    }
}
