package ru.javaops.bootjava.model;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString(callSuper = true)
public class Dish extends AbstractEntity {
    @NotBlank
    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
