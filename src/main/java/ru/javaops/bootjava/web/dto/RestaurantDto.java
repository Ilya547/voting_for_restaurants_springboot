package ru.javaops.bootjava.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * A DTO for the {@link ru.jsft.voteforlunch.model.Restaurant} entity
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantDto extends AbstractDto {
    @NotBlank(message = "The restaurant must have a name")

    String name;

    public RestaurantDto(Long id, String name) {
        super(id);
        this.name = name;
    }
}
