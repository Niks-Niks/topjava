package ru.javawebinar.topjava;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.javawebinar.topjava.service.*;

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

