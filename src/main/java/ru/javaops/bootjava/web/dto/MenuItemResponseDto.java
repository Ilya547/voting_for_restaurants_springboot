package ru.javaops.bootjava.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;


/**
 * A DTO for the {@link ru.javaops.bootjava.model.MenuItem} entity
 */
@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MenuItemResponseDto extends AbstractDto {
    @NotNull DishDto dish;

    @NotNull
    int price;

    public MenuItemResponseDto(Long id, DishDto dish, @NotNull int price) {
        super(id);
        this.dish = dish;
        this.price = price;
    }
}
