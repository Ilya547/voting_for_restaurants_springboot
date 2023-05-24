package ru.javaops.bootjava.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.javaops.bootjava.model.Menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    @EntityGraph(attributePaths = "restaurant")
    @Query("select m from Menu m where m.dateOfMenu = :date order by m.dateOfMenu desc, m.restaurant.name asc")
    List<Menu> findAllWithRestaurantsOnDate(@NotNull @Param("date") LocalDate date);

    @EntityGraph(attributePaths = {"restaurant", "menuItems", "menuItems.dish"})
    @Query("select m from Menu m where m.id = :id")
    Optional<Menu> findByIdWithAllData(@Param("id") long id);
}