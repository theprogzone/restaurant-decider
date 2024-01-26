package com.govtech.restaurantdecider.service.impl;

import com.govtech.restaurantdecider.dto.RestaurantDTO;
import com.govtech.restaurantdecider.dto.RestaurantRequest;
import com.govtech.restaurantdecider.dto.RestaurantResponse;
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
import java.util.Optional;

import static org.mockito.Mockito.*;

class RestaurantServiceImplTest {
    @Mock
    RestaurantRepository restaurantRepository;
    @Mock
    SessionRepository sessionRepository;
    @InjectMocks
    RestaurantServiceImpl restaurantServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRestaurantList() {
        when(restaurantRepository.findBySession_SessionId(anyString())).thenReturn(List.of(new Restaurant()));
        when(sessionRepository.findBySessionId(anyString())).thenReturn(Optional.ofNullable(null));

        //Test null session
        Assertions.assertThrows(ValidationException.class, () -> restaurantServiceImpl.getRestaurantList("123e4567-e89b-12d3-a456-426655440000"));

        //Test when the session is not null and db has data
        User user = new User();
        user.setId(1L);

        Session session = new Session();
        session.setActive(true);
        session.setSessionId("123e4567-e89b-12d3-a456-426655440000");
        session.setId(1L);
        session.setUser(user);

        Restaurant restaurant1 = new Restaurant();
        restaurant1.setPersonName("John");
        restaurant1.setRestaurantName("Restaurant 1");
        restaurant1.setRestaurantLocation("Location 1");
        restaurant1.setId(1L);
        restaurant1.setSelected(false);

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setPersonName("Bob");
        restaurant2.setRestaurantName("Restaurant 2");
        restaurant2.setRestaurantLocation("Location 2");
        restaurant2.setId(2L);
        restaurant2.setSelected(true);
        restaurant2.setSession(session);

        when(sessionRepository.findBySessionId(anyString())).thenReturn(Optional.of(session));
        when(restaurantRepository.findBySession_SessionId(anyString())).thenReturn(List.of(restaurant1, restaurant2));

        RestaurantDTO restaurantDTO1 = new RestaurantDTO("John", "Restaurant 1", "Location 1");
        RestaurantDTO restaurantDTO2 = new RestaurantDTO("Bob", "Restaurant 2", "Location 2");

        RestaurantResponse result = restaurantServiceImpl.getRestaurantList("123e4567-e89b-12d3-a456-426655440000");

        Assertions.assertEquals(new RestaurantResponse(List.of(restaurantDTO1), restaurantDTO2), result);
    }

    @Test
    void testCreateRestaurantChoice() {
        //Test for invalid session
        when(sessionRepository.findBySessionIdAndIsActive(any(), eq(true))).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(ValidationException.class, () -> restaurantServiceImpl.createRestaurantChoice(new RestaurantRequest("123e4567-e89b-12d3-a456-426655440000", "Restaurant 1", "Location 1", "Person 1")));

        //Test for valid scenario
        User user = new User();
        user.setId(1L);

        Session session = new Session();
        session.setActive(true);
        session.setSessionId("123e4567-e89b-12d3-a456-426655440000");
        session.setId(1L);
        session.setUser(user);

        when(sessionRepository.findBySessionIdAndIsActive(any(), eq(true))).thenReturn(Optional.ofNullable(session));

        restaurantServiceImpl.createRestaurantChoice(new RestaurantRequest("123e4567-e89b-12d3-a456-426655440000", "Restaurant 1", "Location 1", "Person 1"));
        verify(restaurantRepository).save(any());
    }
}
