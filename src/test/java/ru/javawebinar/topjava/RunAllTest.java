package ru.javawebinar.topjava;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.javawebinar.topjava.service.datajpa.MealDataJpaServiceTest;
import ru.javawebinar.topjava.service.datajpa.UserDataJpaServiceTest;
import ru.javawebinar.topjava.service.jdbc.MealJdbcServiceTest;
import ru.javawebinar.topjava.service.jdbc.UserJdbcServiceTest;
import ru.javawebinar.topjava.service.jpa.MealJpaServiceTest;
import ru.javawebinar.topjava.service.jpa.UserJpaServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MealJpaServiceTest.class,
        MealJdbcServiceTest.class,
        MealDataJpaServiceTest.class,
        UserJpaServiceTest.class,
        UserJdbcServiceTest.class,
        UserDataJpaServiceTest.class
})
public class RunAllTest {
}

