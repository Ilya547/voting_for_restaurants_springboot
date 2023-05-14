package ru.javaops.bootjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javaops.bootjava.model.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
}