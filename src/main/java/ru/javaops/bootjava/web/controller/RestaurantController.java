package ru.javaops.bootjava.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.bootjava.model.Restaurant;
import ru.javaops.bootjava.service.RestaurantService;
import ru.javaops.bootjava.web.dto.RestaurantDto;
import ru.javaops.bootjava.web.mapper.RestaurantMapper;

import java.net.URI;
import java.util.List;

import static ru.javaops.bootjava.validation.ValidationUtils.checkNew;

@Tag(name = "RestaurantController")
@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    public static final String REST_URL = "/api/v1/restaurants";

    private final RestaurantService service;
    private final RestaurantMapper mapper;

    public RestaurantController(RestaurantService service, RestaurantMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDto>> getAll() {
        return ResponseEntity.ok(service.findAll().stream()
                .map(mapper::toDto)
                .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDto> get(@PathVariable long id) {
        return ResponseEntity.ok(mapper.toDto(service.findById(id)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantDto> create(@Valid @RequestBody RestaurantDto restaurantDto) {
        checkNew(restaurantDto);
        Restaurant created = service.create(mapper.toEntity(restaurantDto));
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
    public ResponseEntity<RestaurantDto> update(@PathVariable long id, @Valid @RequestBody RestaurantDto restaurantDto) {
        return ResponseEntity.ok(mapper.toDto(service.update((int) id, mapper.toEntity(restaurantDto))));
    }
}
