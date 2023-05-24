package ru.javaops.bootjava.web.dto;

import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;


/**
 * A DTO for the {@link ru.javaops.bootjava.model.MenuItem} entity
 */
@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MenuItemRequestDto extends AbstractDto {
    long dishId;

    @Positive int price;
}
