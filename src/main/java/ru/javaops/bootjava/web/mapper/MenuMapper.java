package ru.javaops.bootjava.web.mapper;

import org.springframework.stereotype.Component;
import ru.javaops.bootjava.model.Menu;
import ru.javaops.bootjava.repository.RestaurantRepository;
import ru.javaops.bootjava.web.dto.MenuItemRequestDto;
import ru.javaops.bootjava.web.dto.MenuRequestDto;
import ru.javaops.bootjava.web.dto.MenuResponseDto;

import java.util.stream.Collectors;

@Component
public class MenuMapper implements RequestResponseMapper<Menu, MenuRequestDto, MenuResponseDto> {

    private final RestaurantMapper restaurantMapper;
    private final MenuItemMapper menuItemMapper;
    private final RestaurantRepository restaurantRepository;

    public MenuMapper(RestaurantMapper restaurantMapper, MenuItemMapper menuItemMapper,
                      RestaurantRepository restaurantRepository) {
        this.restaurantMapper = restaurantMapper;
        this.menuItemMapper = menuItemMapper;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Menu toEntity(MenuRequestDto dto) {
        Menu menu = new Menu();
        menu.setId(dto.getId());
        menu.setDateOfMenu(dto.getDateOfMenu());
        menu.setRestaurant(restaurantRepository.getReferenceById(dto.getRestaurantId()));
        for (MenuItemRequestDto menuItemDto : dto.getMenuItems()) {
            menu.addMenuItem(menuItemMapper.toEntity(menuItemDto));
        }
        return menu;
    }

    @Override
    public MenuResponseDto toDto(Menu entity) {
        return new MenuResponseDto(
                entity.getId(),
                entity.getDateOfMenu(),
                restaurantMapper.toDto(entity.getRestaurant()),
                entity.getMenuItems().stream().map(menuItemMapper::toDto).collect(Collectors.toSet()));
    }
}
