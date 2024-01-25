package com.govtech.restaurantdecider.service;

import com.govtech.restaurantdecider.dto.LoginRequest;
import com.govtech.restaurantdecider.dto.LoginResponse;
import com.govtech.restaurantdecider.dto.UserDTO;

public interface UserService {

    LoginResponse login(LoginRequest loginRequest);

    UserDTO findByUsername(String username);

    void register();
}
