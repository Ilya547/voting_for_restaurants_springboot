package ru.javaops.bootjava.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "menu")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Menu extends BaseEntity {
    @Column(name = "dish_name", nullable = false)
    @NotBlank
    @Size(max = 64)
    private String dishName;

    @Column(name = "price", nullable = false)
    @NotBlank
    @Size(max = 10)
    private BigDecimal price;

//    @Column(name = "restaurant_id", insert="false" update="false")
//    @NotBlank
//    @Size(max = 10)
//    private int restaurantId;

    @ManyToMany
    @JoinTable(name = "menu_users",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id"))
    private Set<User> voicedUsers = new LinkedHashSet<>();

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

}
