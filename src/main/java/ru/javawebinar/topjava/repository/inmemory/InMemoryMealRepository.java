package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final Map<Integer, Meal> mealMapRepository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(v -> save(v, v.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            mealMapRepository.put(meal.getId(), meal);
            meal.setUserId(userId);
            log.info("create meal {} in repository", meal);
            return meal;
        }
        log.info("update meal {} in repository", meal);
        return isEqualsId(meal.getId(), userId) ? mealMapRepository.replace(meal.getId(), meal) : null;
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete id -> {} userId -> {} in repository", id, userId);
        return isEqualsId(id, userId) && mealMapRepository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get id -> {} userId -> {} in repository", id, userId);
        return isEqualsId(id, userId) ? mealMapRepository.get(id) : null;
    }

    @Override
    public List<Meal> getByUserId(int userId) {
        return getFilteredByPredicated(v -> v.getUserId().equals(userId));
    }

    @Override
    public List<Meal> getFilterData(int userId, LocalDate startDate, LocalDate endDate) {
        return getFilteredByPredicated(v -> DateTimeUtil.isBetweenHalfOpen(v.getDate(), startDate, !endDate.equals(LocalDate.MAX) ? endDate.plusDays(1) : endDate) && v.getUserId().equals(userId));
    }

    private boolean isEqualsId(int id, int userId) {
        return mealMapRepository.get(id) == null ? mealMapRepository.get(id).getUserId().equals(userId) : null;
    }

    private List<Meal> getFilteredByPredicated(Predicate<Meal> filter) {
        return mealMapRepository.values()
                .stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime))
                .collect(Collectors.toList());
    }
}