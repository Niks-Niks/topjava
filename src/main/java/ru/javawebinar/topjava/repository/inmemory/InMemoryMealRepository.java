package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final Map<Integer, Meal> mealMapRepository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        log.info("1 - {}", meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            mealMapRepository.put(meal.getId(), meal);
            log.info("create meal {} in repository", meal);
            return meal;
        }
        log.info("update meal {} in repository", meal);
        // handle case: update, but not present in storage
        if (mealMapRepository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal) == null) {
            return mealMapRepository.put(meal.getId(), meal);
        } else {
            return mealMapRepository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
    }

    @Override
    public boolean delete(int id) {
        log.info("delete meal {} in repository", id);
        return mealMapRepository.remove(id) != null;
    }

    @Override
    public Meal get(int id) {
        log.info("get meal {} in repository", id);
        return mealMapRepository.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        log.info("getAllMeal in repository");
        return mealMapRepository.values();
    }

    @Override
    public List<Meal> getMealsByUserId(int userId) {
        List<Meal> listMealByUserId = new ArrayList<>();
        for (Meal meals : mealMapRepository.values()) {
            if (userId == meals.getUserId()) {
                listMealByUserId.add(meals);
            }
        }
        Collections.reverse(listMealByUserId);
        log.info("getMealsByUserId {} in repository", listMealByUserId);
        return listMealByUserId;
    }
}

