package com.govtech.restaurantdecider.dto;

import java.util.List;

public record RestaurantResponse(List<RestaurantDTO> restaurantList, RestaurantDTO selectedRestaurant) {
}
