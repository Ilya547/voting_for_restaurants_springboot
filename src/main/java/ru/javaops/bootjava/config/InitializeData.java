package ru.javaops.bootjava.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.model.*;
import ru.javaops.bootjava.repository.*;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Component
public class InitializeData implements CommandLineRunner {
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;
    private final Clock clock;

    public InitializeData(VoteRepository voteRepository, UserRepository userRepository, MenuRepository menuRepository,
                          RestaurantRepository restaurantRepository, DishRepository dishRepository, Clock clock) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
        this.clock = clock;
    }

    @Override
    @Transactional
    public void run(String... args) {
        Dish coffee = new Dish("Coffee");
        Dish salad = new Dish("Salad");
        Dish burger = new Dish("Burger");
        Dish soup = new Dish("Soup");
        Dish pizza = new Dish("Pizza");

        dishRepository.saveAll(List.of(coffee, burger, soup, pizza, salad));

        Restaurant uLudmily = new Restaurant("U Ludmily");
        Restaurant vkusnoAndDot = new Restaurant("VkusnoAndTochka");
        restaurantRepository.saveAll(List.of(uLudmily, vkusnoAndDot));

        User admin = new User("admin@gmail.com", "admin", "admin", "{noop}admin",
                Role.ADMIN, true);
        User user = new User("user@gmail.com", "user", "user", "{noop}user",
                Role.USER, true);

        List<User> userList = new ArrayList<>(100);
        userList.add(admin);
        userList.add(user);
        for (int i = 0; i < 202; i++) {
            userList.add(new User("user" + i + "@mail.com", "userName" + i, "userSurname" + i,
                    "{noop}user" + i, Role.USER, true));
        }
        userRepository.saveAll(userList);


        LocalDate nowMinusTwoDays = LocalDate.now(clock).minusDays(2);
        LocalDate nowMinusOneDay = LocalDate.now(clock).minusDays(1);

        Menu menuForULudmily1 = new Menu();
        menuForULudmily1.setDateOfMenu(nowMinusTwoDays);
        menuForULudmily1.setRestaurant(uLudmily);
        menuForULudmily1.setMenuItems(new TreeSet<>(Set.of(
                new MenuItem(coffee, 1, menuForULudmily1),
                new MenuItem(burger, 1, menuForULudmily1)
        )));
        Menu menuForULudmily2 = new Menu();
        menuForULudmily2.setDateOfMenu(nowMinusOneDay);
        menuForULudmily2.setRestaurant(uLudmily);
        menuForULudmily2.setMenuItems(new TreeSet<>(Set.of(
                new MenuItem(soup, 2, menuForULudmily2),
                new MenuItem(burger, 1, menuForULudmily2)
        )));
        Menu menuForVkusnoAndTochka1 = new Menu();
        menuForVkusnoAndTochka1.setDateOfMenu(nowMinusTwoDays);
        menuForVkusnoAndTochka1.setRestaurant(vkusnoAndDot);
        menuForVkusnoAndTochka1.setMenuItems(new TreeSet<>(Set.of(
                new MenuItem(coffee, 1, menuForVkusnoAndTochka1),
                new MenuItem(pizza, 2, menuForVkusnoAndTochka1)
        )));
        Menu menuForVkusnoAndTochka2 = new Menu();
        menuForVkusnoAndTochka2.setDateOfMenu(nowMinusOneDay);
        menuForVkusnoAndTochka2.setRestaurant(vkusnoAndDot);
        menuForVkusnoAndTochka2.setMenuItems(new TreeSet<>(Set.of(
                new MenuItem(salad, 2, menuForVkusnoAndTochka2),
                new MenuItem(coffee, 1, menuForVkusnoAndTochka2)
        )));
        menuRepository.saveAll(List.of(menuForULudmily1, menuForULudmily2, menuForVkusnoAndTochka1, menuForVkusnoAndTochka2));

        List<Vote> votes = new ArrayList<>();
        Random random = new Random();
        userList.stream().skip(2L).forEach(usr -> votes.add(new Vote(
                usr,
                random.nextBoolean() ? vkusnoAndDot : uLudmily,
                LocalDate.now(clock),
                LocalTime.of((int) (Math.random() * 11), (int) (Math.random() * 60)))));
        voteRepository.saveAll(votes);
    }
}
