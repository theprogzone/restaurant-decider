package com.govtech.restaurantdecider.service.impl;

import com.govtech.restaurantdecider.config.UserAuthenticationProvider;
import com.govtech.restaurantdecider.dto.SessionRequest;
import com.govtech.restaurantdecider.dto.SessionResponse;
import com.govtech.restaurantdecider.entity.Restaurant;
import com.govtech.restaurantdecider.entity.Session;
import com.govtech.restaurantdecider.entity.User;
import com.govtech.restaurantdecider.exception.ValidationException;
import com.govtech.restaurantdecider.repository.RestaurantRepository;
import com.govtech.restaurantdecider.repository.SessionRepository;
import com.govtech.restaurantdecider.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final UserAuthenticationProvider userAuthenticationProvider;
    private final RestaurantRepository restaurantRepository;

    @Override
    public SessionResponse handleSession(Map<String, String> headers, SessionRequest sessionRequest) {
        User user = userAuthenticationProvider.getUserFrom(headers.get("authorization").replace("Bearer ", ""));
        if ("start".equalsIgnoreCase(sessionRequest.type())) {
            if (ObjectUtils.isEmpty(user)) {
                throw new ValidationException("Invalid request", HttpStatus.BAD_REQUEST);
            }
            Optional<Session> sessionOptional = sessionRepository.findByUser_IdAndIsActive(user.getId(), true);
            if (sessionOptional.isPresent()) {
                throw new ValidationException("Active session already exists", HttpStatus.BAD_REQUEST);
            }
            Session session = new Session();
            UUID uuid = UUID.randomUUID();
            session.setSessionId(uuid.toString());
            session.setUser(user);
            session.setActive(true);
            sessionRepository.save(session);
            return new SessionResponse(session.getSessionId());
        } else if ("stop".equalsIgnoreCase(sessionRequest.type())) {
            if (ObjectUtils.isEmpty(user) || !StringUtils.hasLength(sessionRequest.sessionId())) {
                throw new ValidationException("Invalid request", HttpStatus.BAD_REQUEST);
            }
            Optional<Session> sessionOptional = sessionRepository.findByUser_IdAndIsActive(user.getId(), true);
            if (sessionOptional.isEmpty()) {
                throw new ValidationException("No active session available", HttpStatus.BAD_REQUEST);
            }
            Session session = sessionOptional.get();
            session.setActive(false);
            sessionRepository.save(session);
            selectRandomRestaurant();
            return new SessionResponse(session.getSessionId());
        }
        throw new ValidationException("Invalid request", HttpStatus.BAD_REQUEST);
    }

    private void selectRandomRestaurant() {
        Random rand = new Random();
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        int listSize = restaurantList.size();
        int randomNum = rand.nextInt(((listSize - 1)) + 1);
        Restaurant restaurant = restaurantList.get(randomNum);
        restaurant.setSelected(true);
        restaurantRepository.save(restaurant);
    }


}
