package com.govtech.restaurantdecider.dto;

public record RestaurantRequest(String sessionId, String restaurantName, String restaurantLocation, String personName) {
}
