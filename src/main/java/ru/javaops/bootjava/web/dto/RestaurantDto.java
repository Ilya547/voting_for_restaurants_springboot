package ru.javaops.bootjava.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * A DTO for the {@link ru.javaops.bootjava.model.Restaurant} entity
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantDto extends AbstractDto {
    @NotBlank

    String name;

    public RestaurantDto(Long id, String name) {
        super(id);
        this.name = name;
    }
}
