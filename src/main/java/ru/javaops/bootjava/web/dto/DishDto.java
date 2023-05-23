package ru.javaops.bootjava.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

/**
 * A DTO for the {@link ru.jsft.voteforlunch.model.Dish} entity
 */
@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DishDto extends AbstractDto {
    @NotBlank(message = "The dish must have a name")

    String name;

    public DishDto(Long id, String name) {
        super(id);
        this.name = name;
    }
}
