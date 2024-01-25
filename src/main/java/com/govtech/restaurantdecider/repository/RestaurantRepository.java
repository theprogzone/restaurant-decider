package com.govtech.restaurantdecider.repository;

import com.govtech.restaurantdecider.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findBySession_SessionId(String sessionId);
}
