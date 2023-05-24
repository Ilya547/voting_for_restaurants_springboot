package ru.javaops.bootjava.web.mapper;

import org.springframework.stereotype.Component;
import ru.javaops.bootjava.model.Restaurant;
import ru.javaops.bootjava.web.dto.RestaurantDto;

@Component
public class RestaurantMapper implements Mapper<Restaurant, RestaurantDto> {
    @Override
    public Restaurant toEntity(RestaurantDto dto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(dto.getId());
        restaurant.setName(dto.getName());
        return restaurant;
    }

    @Override
    public RestaurantDto toDto(Restaurant entity) {
        return new RestaurantDto(entity.getId(), entity.getName());
    }
}
