package ru.javaops.bootjava.web.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.javaops.bootjava.model.User;
import ru.javaops.bootjava.web.dto.UserDto;

@Component
public class UserMapper implements Mapper<User, UserDto> {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User toEntity(UserDto dto) {
        User user = new User(
                dto.getEmail(),
                dto.getFirstName(),
                dto.getLastName(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getRole(),
                dto.isEnabled()
        );
        user.setId(dto.getId());
        return user;
    }

    @Override
    public UserDto toDto(User entity) {
        return new UserDto(
                entity.getId(),
                entity.getEmail(),
                entity.getFirstName(),
                entity.getLastName(),
                "***", // do not expose password to frontend
                entity.isEnabled(),
                entity.getRole()
        );
    }
}
