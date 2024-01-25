package com.govtech.restaurantdecider.service.impl;

import com.govtech.restaurantdecider.dto.RestaurantDTO;
import com.govtech.restaurantdecider.dto.RestaurantRequest;
import com.govtech.restaurantdecider.dto.RestaurantResponse;
import com.govtech.restaurantdecider.entity.Restaurant;
import com.govtech.restaurantdecider.entity.Session;
import com.govtech.restaurantdecider.exception.ValidationException;
import com.govtech.restaurantdecider.repository.RestaurantRepository;
import com.govtech.restaurantdecider.repository.SessionRepository;
import com.govtech.restaurantdecider.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final SessionRepository sessionRepository;

    @Override
    public RestaurantResponse getRestaurantList(String sessionId) {
        Optional<Session> sessionOptional = sessionRepository.findBySessionId(sessionId);
        if (sessionOptional.isEmpty()) {
            throw new ValidationException("Invalid session", HttpStatus.BAD_REQUEST);
        }
        List<Restaurant> restaurants = restaurantRepository.findBySession_SessionId(sessionId);
        RestaurantDTO selectedRestaurant = new RestaurantDTO();
        List<RestaurantDTO> restaurantDTOList = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            if (restaurant.isSelected()) {
                selectedRestaurant = new RestaurantDTO(restaurant.getPersonName(), restaurant.getRestaurantName(), restaurant.getRestaurantLocation());
            } else {
                restaurantDTOList.add(new RestaurantDTO(restaurant.getPersonName(), restaurant.getRestaurantName(), restaurant.getRestaurantLocation()));
            }
        }
        return new RestaurantResponse(restaurantDTOList, selectedRestaurant);
    }

    @Override
    public void createRestaurantChoice(RestaurantRequest restaurantRequest) {
        Restaurant restaurant = new Restaurant();
        Optional<Session> sessionOptional = sessionRepository.findBySessionIdAndIsActive(restaurantRequest.sessionId(), true);
        if (sessionOptional.isEmpty()) {
            throw new ValidationException("Invalid session", HttpStatus.BAD_REQUEST);
        }
        restaurant.setSession(sessionOptional.get());
        restaurant.setRestaurantLocation(restaurantRequest.restaurantLocation());
        restaurant.setRestaurantName(restaurantRequest.restaurantName());
        restaurant.setPersonName(restaurantRequest.personName());
        restaurantRepository.save(restaurant);
    }
}
