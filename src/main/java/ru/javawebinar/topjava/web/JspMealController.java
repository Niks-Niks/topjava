package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController {
    @Autowired
    private MealService service;

    private int userId = SecurityUtil.authUserId();

    @GetMapping
    public String meals(Model model) {
        model.addAttribute("meals", MealsUtil.getTos(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay()));
        return "/meals";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int id) {
        service.delete(id, userId);
        return "redirect:/meals";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "mealForm";
    }

    @GetMapping("/update")
    public String update(@RequestParam int id, Model model) {
        model.addAttribute("meal", service.get(id, userId));
        return "mealForm";
    }

    @GetMapping("/meals")
    public String filter(HttpServletRequest request, Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));

        model.addAttribute("meals", MealsUtil.getFilteredTos(service.getBetweenInclusive(startDate, endDate, userId),
                SecurityUtil.authUserCaloriesPerDay(), startTime, endTime));
        return "meals";
    }

    @PostMapping
    public String save(HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (!StringUtils.hasText(request.getParameter("id"))) {
            service.create(meal, Integer.parseInt(request.getParameter("id")));
        } else {
            service.update(meal, Integer.parseInt(request.getParameter("id")));
        }
        return "redirect:/meals";
    }
}