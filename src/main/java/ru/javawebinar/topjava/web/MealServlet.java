package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealStorageMap;
import ru.javawebinar.topjava.storage.StorageMeal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);
    private final StorageMeal storage = new MealStorageMap();
    private final AtomicInteger atomicInteger = new AtomicInteger(0);
    private final int caloriesPerDay = 2000;

    {
        storage.create(new Meal(atomicInteger.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        storage.create(new Meal(atomicInteger.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        storage.create(new Meal(atomicInteger.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        storage.create(new Meal(atomicInteger.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        storage.create(new Meal(atomicInteger.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        storage.create(new Meal(atomicInteger.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        storage.create(new Meal(atomicInteger.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to mealServlet - doGet");

        String key = request.getParameter("key");
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");

        switch (action) {
            case "delete":
                log.debug("redirect to mealServlet - doGet -> delete");
                storage.delete(Integer.parseInt(key));
                response.sendRedirect("meals");
                return;
            case "edit":
                log.debug("redirect to mealServlet - doGet -> edit");
                request.setAttribute("meal", storage.get(Integer.parseInt(key)));
                request.getRequestDispatcher("edit.jsp").forward(request, response);
                break;
            case "create":
                log.debug("redirect to mealServlet - doGet -> create");
                request.getRequestDispatcher("create.jsp").forward(request, response);
                break;
            default:
                log.debug("redirect to mealServlet - doGet -> default, list");
                request.setAttribute("mealTo", MealsUtil.filteredByStreams(storage.getAll(), LocalTime.of(0, 0), LocalTime.of(23, 59), caloriesPerDay));
                request.getRequestDispatcher("list.jsp").forward(request, response);
                return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to mealServlet - doPost");
        request.setCharacterEncoding("UTF-8");

        String date = request.getParameter("date");
        String desc = request.getParameter("desc");
        int cal = Integer.parseInt(request.getParameter("cal"));

        Meal meal = request.getParameter("key") == null ?
                storage.create(new Meal(atomicInteger.incrementAndGet(), LocalDateTime.parse(date), desc, cal)) :
                storage.update(new Meal(Integer.parseInt(request.getParameter("key")), LocalDateTime.parse(date), desc, cal));

        response.sendRedirect("meals");


    }

}
