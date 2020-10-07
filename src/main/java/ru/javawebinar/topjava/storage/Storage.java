package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Storage {
    void delete(int key);

    Meal get(int key);

    void save(int key, Meal meal);

    void update(int key, Meal meal);

    List<Meal> getAll();
}
