package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            mealMapRepository.put(meal.getId(), meal);
            log.info("create meal {} in repository", meal);
            return meal;
        }
        log.info("update meal {} in repository", meal);
        // handle case: update, but not present in storage
        return mealMapRepository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int mealId, int userId) {
        log.info("delete meal - {} user - {} in repository", mealId, userId);
        return isEqualsId(mealId, userId) && mealMapRepository.remove(mealId) != null;
    }

    @Override
    public Meal get(int mealId, int userId) {
        log.info("get meal - {} user - {} in repository", mealId, userId);
        return isEqualsId(mealId, userId) ? mealMapRepository.get(mealId) : null;
    }

    @Override
    public List<Meal> getMealsByUserId(int userId) {
        log.info("getMealsByUserId in repository");
        return mealMapRepository.values()
                .stream()
                .filter(v -> v.getUserId().equals(userId))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    private boolean isEqualsId(int mealId, int userId) {
        return mealMapRepository.get(mealId).getUserId().equals(userId);
    }
}

