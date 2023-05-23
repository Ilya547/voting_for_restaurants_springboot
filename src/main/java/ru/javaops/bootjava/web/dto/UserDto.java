package ru.javaops.bootjava.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.jsft.voteforlunch.model.Role;

@Value
@EqualsAndHashCode(callSuper = true)
public class UserDto extends AbstractDto {
    @Size(max = 128)

    @Email(message = "Please enter valid e-mail")
    String email;

    @Size(max = 128)
    @NotBlank(message = "First name must not be empty")
    String firstName;

    @Size(max = 128)
    @NotBlank(message = "Last name must not be empty")
    String lastName;

    @Size(max = 256)
    @NotBlank(message = "Password must not be empty")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    String password;

    boolean enabled;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Role role;

    public UserDto(Long id, String email, String firstName, String lastName, String password, boolean enabled, Role role) {
        super(id);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
    }
}
