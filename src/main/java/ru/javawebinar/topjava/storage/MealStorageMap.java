package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MealStorageMap implements StorageMeal {
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
    public Meal create(Meal meal) {
        return mealMap.put(meal.getId(), meal);
    }

    @Override
    public Meal update(Meal meal) {
        return mealMap.replace(meal.getId(), meal);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealMap.values());
    }
}
