package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final MealService service;
    private final Integer userId = authUserId();

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal get(int mealId) {
        log.info("get {}", mealId);
        return service.get(mealId, userId);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        meal.isNew();
        return service.create(meal);
    }

    public void delete(int mealId) {
        log.info("delete {}", mealId);
        service.delete(mealId, userId);
    }

    public void update(Meal meal) {
        log.info("update {} with id={}", meal, userId);
        assureIdConsistent(meal, userId);
        service.update(meal, userId);
    }

    public List<Meal> getMealsByUserId() {
        log.info("getMealsByUserId {}", userId);
        return service.getMealsByUserId(userId);
    }

}