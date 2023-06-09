package ru.javaops.bootjava.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.bootjava.model.User;
import ru.javaops.bootjava.service.UserService;
import ru.javaops.bootjava.web.dto.UserDto;
import ru.javaops.bootjava.web.mapper.UserMapper;
import ru.javaops.bootjava.web.security.AuthorizedUser;

import java.net.URI;
import java.util.List;

import static ru.javaops.bootjava.validation.ValidationUtils.checkNew;

@Tag(name = "UserController")
@RestController
@RequestMapping(value = UserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    public static final String REST_URL = "/api/v1/users";

    private final UserService service;
    private final UserMapper mapper;

    public UserController(UserService service, UserMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(service.findAllSorted().stream().map(mapper::toDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> get(@PathVariable long id) {
        return ResponseEntity.ok(mapper.toDto(service.findById(id)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto userDto) {
        checkNew(userDto);
        User created = service.create(mapper.toEntity(userDto));
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
    public ResponseEntity<UserDto> update(@PathVariable long id, @Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(mapper.toDto(service.update(id, mapper.toEntity(userDto))));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getProfile(@AuthenticationPrincipal AuthorizedUser user) {
        return get(user.id());
    }

    @PutMapping(path = "/profile", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> update(@AuthenticationPrincipal AuthorizedUser user, @Valid @RequestBody UserDto userDto) {
        return update(user.id(), userDto);
    }
}
