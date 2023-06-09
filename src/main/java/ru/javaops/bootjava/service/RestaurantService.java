package ru.javaops.bootjava.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.model.Restaurant;
import ru.javaops.bootjava.repository.RestaurantRepository;

import java.util.List;

import static ru.javaops.bootjava.validation.ValidationUtils.checkEntityExist;
import static ru.javaops.bootjava.validation.ValidationUtils.checkEntityWasFound;

@Service
@Slf4j
public class RestaurantService {
    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Restaurant findById(long id) {
        log.info("Find restaurant with id = {}", id);
        return checkEntityWasFound(repository.findById(id), id, Restaurant.class);
    }

    public List<Restaurant> findAll() {
        log.info("Find all restaurants");
        return repository.findAll(Sort.by("name"));
    }

    public Restaurant create(Restaurant restaurant) {
        log.info("Create restaurant: {}", restaurant);
        return repository.save(restaurant);
    }

    public void delete(long id) {
        log.info("Delete restaurant with id = {}", id);
        repository.deleteById(id);
    }

    @Transactional
    public Restaurant update(int id, Restaurant restaurant) {
        log.info("Update restaurant with id = {}", restaurant.getId());
        checkEntityExist(repository.existsById((long) id), id, Restaurant.class);
        restaurant.setId((long) id);
        return repository.save(restaurant);
    }
}
