package ru.javaops.bootjava.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Restaurant extends BaseEntity {
    @Column(name = "restaurant_name", nullable = false, unique = true)
    @NotBlank
    @Size(max = 256)
    private String name;

    @OneToMany(mappedBy = "restaurant", orphanRemoval = true)
    private Set<Menu> menus = new LinkedHashSet<>();

}
