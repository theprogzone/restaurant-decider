package com.govtech.restaurantdecider.service;

import com.govtech.restaurantdecider.dto.SessionRequest;
import com.govtech.restaurantdecider.dto.SessionResponse;

import java.util.Map;

public interface SessionService {

    SessionResponse handleSession(Map<String, String> headers, SessionRequest sessionRequest);
}
