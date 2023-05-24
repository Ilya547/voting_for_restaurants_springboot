package ru.javaops.bootjava.web.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;
import java.util.Set;

/**
 * A DTO for the {@link ru.javaops.bootjava.model.Menu} entity
 */
@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MenuRequestDto extends AbstractDto {
    @NotNull
    LocalDate dateOfMenu;

    long restaurantId;

    @NotEmpty
    Set<MenuItemRequestDto> menuItems;
}
