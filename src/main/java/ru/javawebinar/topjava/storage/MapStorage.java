package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage implements Storage {

    private Map<Integer, Meal> mealMap = new HashMap<>();

    @Override
    public void delete(int key) {
        mealMap.remove(key);
    }

    @Override
    public Meal get(int key) {
        return mealMap.get(key);
    }

    @Override
    public void save(int key, Meal meal) {
        mealMap.put(key, meal);
    }

    @Override
    public void update(int key, Meal meal) {
        mealMap.put(key, meal);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealMap.values());
    }
}
