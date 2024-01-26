package com.govtech.restaurantdecider.controller;

import com.govtech.restaurantdecider.dto.SessionRequest;
import com.govtech.restaurantdecider.dto.SessionResponse;
import com.govtech.restaurantdecider.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/session")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @PutMapping
    public ResponseEntity<SessionResponse> sessionHandler(@RequestHeader Map<String, String> headers, @RequestBody SessionRequest sessionRequest) {
        return new ResponseEntity<>(sessionService.handleSession(headers, sessionRequest), HttpStatus.OK);
    }
}
