package com.govtech.restaurantdecider.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RestaurantDTO {
    private String personName;
    private String restaurantName;
    private String restaurantLocation;
}
