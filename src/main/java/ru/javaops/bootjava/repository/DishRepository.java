package ru.javaops.bootjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.javaops.bootjava.model.Dish;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

}

