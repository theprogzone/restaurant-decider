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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;

class SessionServiceImplTest {
    @Mock
    SessionRepository sessionRepository;
    @Mock
    UserAuthenticationProvider userAuthenticationProvider;
    @Mock
    RestaurantRepository restaurantRepository;
    @InjectMocks
    SessionServiceImpl sessionServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleStartSession() {
        // Test for invalid token
        when(userAuthenticationProvider.getUserFrom(any())).thenReturn(null);
        Assertions.assertThrows(ValidationException.class, ()->sessionServiceImpl.handleSession(Map.of("authorization", "dfasfweaghthbegbfdhsfhsdfe"), new SessionRequest("start", "123e4567-e89b-12d3-a456-426655440000")));

        // Test for session availability
        User user = new User();
        user.setId(1L);

        Session session = new Session();
        session.setActive(true);
        session.setSessionId("123e4567-e89b-12d3-a456-426655440000");
        session.setId(1L);
        session.setUser(user);

        when(userAuthenticationProvider.getUserFrom(any())).thenReturn(user);
        when(sessionRepository.findByUser_IdAndIsActive(anyLong(), anyBoolean())).thenReturn(Optional.of(session));
        Assertions.assertThrows(ValidationException.class, ()->sessionServiceImpl.handleSession(Map.of("authorization", "dfasfweaghthbegbfdhsfhsdfe"), new SessionRequest("start", "123e4567-e89b-12d3-a456-426655440000")));

        // Test for valid scenario
        when(userAuthenticationProvider.getUserFrom(any())).thenReturn(user);
        when(sessionRepository.findByUser_IdAndIsActive(anyLong(), anyBoolean())).thenReturn(Optional.ofNullable(null));
        sessionServiceImpl.handleSession(Map.of("authorization", "dfasfweaghthbegbfdhsfhsdfe"), new SessionRequest("start", "123e4567-e89b-12d3-a456-426655440000"));

        verify(sessionRepository).save(any());
    }

    @Test
    void testHandleStopSession() {
        // Test for invalid token
        when(userAuthenticationProvider.getUserFrom(any())).thenReturn(null);
        Assertions.assertThrows(ValidationException.class, ()->sessionServiceImpl.handleSession(Map.of("authorization", "dfasfweaghthbegbfdhsfhsdfe"), new SessionRequest("stop", "123e4567-e89b-12d3-a456-426655440000")));

        // Test for session availability
        User user = new User();
        user.setId(1L);

        when(userAuthenticationProvider.getUserFrom(any())).thenReturn(user);
        when(sessionRepository.findByUser_IdAndIsActive(anyLong(), anyBoolean())).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(ValidationException.class, ()->sessionServiceImpl.handleSession(Map.of("authorization", "dfasfweaghthbegbfdhsfhsdfe"), new SessionRequest("stop", "123e4567-e89b-12d3-a456-426655440000")));

        // Test for valid scenario
        Session session = new Session();
        session.setActive(true);
        session.setSessionId("123e4567-e89b-12d3-a456-426655440000");
        session.setId(1L);
        session.setUser(user);

        Restaurant restaurant = new Restaurant();
        restaurant.setSession(session);
        restaurant.setRestaurantLocation("Location 1");
        restaurant.setRestaurantName("Restaurant 1");
        restaurant.setSelected(false);

        when(userAuthenticationProvider.getUserFrom(any())).thenReturn(user);
        when(sessionRepository.findByUser_IdAndIsActive(anyLong(), anyBoolean())).thenReturn(Optional.ofNullable(session));
        when(restaurantRepository.findAll()).thenReturn(List.of(restaurant));
        sessionServiceImpl.handleSession(Map.of("authorization", "dfasfweaghthbegbfdhsfhsdfe"), new SessionRequest("stop", "123e4567-e89b-12d3-a456-426655440000"));

        verify(sessionRepository).save(any());
        verify(restaurantRepository).save(any());
    }
}
