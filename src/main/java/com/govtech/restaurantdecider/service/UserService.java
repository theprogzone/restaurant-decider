package com.govtech.restaurantdecider.service;

import com.govtech.restaurantdecider.dto.LoginRequest;
import com.govtech.restaurantdecider.dto.LoginResponse;

public interface UserService {

    LoginResponse login(LoginRequest loginRequest);
}
