package ru.javaops.bootjava.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.bootjava.model.Menu;
import ru.javaops.bootjava.service.MenuService;
import ru.javaops.bootjava.web.dto.MenuListDto;
import ru.javaops.bootjava.web.dto.MenuRequestDto;
import ru.javaops.bootjava.web.dto.MenuResponseDto;
import ru.javaops.bootjava.web.mapper.MenuListMapper;
import ru.javaops.bootjava.web.mapper.MenuMapper;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.javaops.bootjava.validation.ValidationUtils.checkNew;

@Tag(name = "MenuController")
@RestController
@RequestMapping(value = MenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController {
    public static final String REST_URL = "/api/v1/menus";

    private final MenuService service;
    private final MenuMapper mapper;
    private final MenuListMapper listMapper;

    public MenuController(MenuService service, MenuMapper mapper, MenuListMapper listMapper) {
        this.service = service;
        this.mapper = mapper;
        this.listMapper = listMapper;
    }

    @GetMapping("/on-date")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<MenuListDto>> getListOfMenusOnDate(
            @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @NotNull LocalDate date) {

        return ResponseEntity.ok(service.findAllWithRestaurants(date).stream().map(listMapper::toDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuResponseDto> get(@PathVariable long id) {
        return ResponseEntity.ok(mapper.toDto(service.findByIdWithAllData(id)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<MenuResponseDto> create(@Valid @RequestBody MenuRequestDto menuDto) {
        checkNew(menuDto);
        Menu created = service.create(mapper.toEntity(menuDto));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(mapper.toDto(created));
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<MenuResponseDto> update(@PathVariable long id, @Valid @RequestBody MenuRequestDto menuDto) {
        return ResponseEntity.ok(mapper.toDto(service.updateAndReturnWithDetails(id, mapper.toEntity(menuDto))));
    }
}
