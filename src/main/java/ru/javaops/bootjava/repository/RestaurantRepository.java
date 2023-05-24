package ru.javaops.bootjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javaops.bootjava.model.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}