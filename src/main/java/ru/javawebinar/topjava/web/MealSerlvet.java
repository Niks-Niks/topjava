package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MapStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import static org.slf4j.LoggerFactory.getLogger;

public class MealSerlvet extends HttpServlet {

    private static final Logger log = getLogger(MealSerlvet.class);
    private static final Storage storage = new MapStorage();

    {
        storage.save(1, new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        storage.save(2, new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        storage.save(3, new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        storage.save(4, new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        storage.save(5, new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        storage.save(6, new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        storage.save(7, new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to mealServlet - doGet");

        String key = request.getParameter("key");
        String action = request.getParameter("action");

        if (action == null) {
            log.debug("redirect to mealServlet - doGet -> list");
            request.setAttribute("mealTo", MealsUtil.filteredByStreams(storage.getAll(), LocalTime.of(0, 0), LocalTime.of(23, 0), 2000));
            request.getRequestDispatcher("list.jsp").forward(request, response);
            return;
        }

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
            case "save":
                log.debug("redirect to mealServlet - doGet -> save");
                break;
            default:
                log.debug("redirect to mealServlet - doGet -> default");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to mealServlet - doPost");
        request.setCharacterEncoding("UTF-8");

        int key = Integer.parseInt(request.getParameter("key"));
        String date = request.getParameter("date");
        String desc = request.getParameter("desc");
        int cal = Integer.parseInt(request.getParameter("cal"));

        storage.update(key, new Meal(key, LocalDateTime.parse(date), desc, cal));
        response.sendRedirect("meals");
    }

}
