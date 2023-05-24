package ru.javaops.bootjava.web.mapper;

import org.springframework.stereotype.Component;
import ru.javaops.bootjava.model.Dish;
import ru.javaops.bootjava.web.dto.DishDto;

@Component
public class DishMapper implements Mapper<Dish, DishDto> {
    @Override
    public Dish toEntity(DishDto dto) {
        Dish dish = new Dish();
        dish.setId(dto.getId());
        dish.setName(dto.getName());
        return dish;
    }

    @Override
    public DishDto toDto(Dish entity) {
        return new DishDto(entity.getId(), entity.getName());
    }
}
