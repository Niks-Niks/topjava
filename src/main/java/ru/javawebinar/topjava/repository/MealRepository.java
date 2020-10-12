package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    // null if not found, when updated
    Meal save(Meal meal);

    // false if not found
    boolean delete(int mealId, int userId);

    // null if not found
    Meal get(int mealId, int userId);

    List<Meal> getMealsByUserId(int userId);
}
