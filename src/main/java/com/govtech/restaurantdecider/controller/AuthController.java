package com.govtech.restaurantdecider.controller;

import com.govtech.restaurantdecider.dto.LoginRequest;
import com.govtech.restaurantdecider.dto.LoginResponse;
import com.govtech.restaurantdecider.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/login")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<LoginResponse> userLogin(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(userService.login(loginRequest), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> ack() {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
