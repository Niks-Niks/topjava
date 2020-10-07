package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface StorageMeal {
    void delete(int key);

    Meal get(int key);

    Meal create(Meal meal);

    Meal update(Meal meal);

    List<Meal> getAll();
}
