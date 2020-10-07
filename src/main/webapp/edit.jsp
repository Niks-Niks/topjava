<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 07.10.2020
  Time: 14:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
<html>
<head>
    <title>Edit</title>
</head>
<body>

<section>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <h1>ID: <span>${meal.id}</span>, редактировать</h1>
        <input type="hidden" name="key" value="${meal.id}">
        Date:
        <input type="datetime-local" name="date" size=50 value="${meal.dateTime}">

        Description:
        <input type="text" name="desc" size=50 value="${meal.description}">

        Calories:
        <input type="text" name="cal" size=50 value="${meal.calories}">

        <hr>
        <button type="submit">Сохранить</button>
        <button name="back" type="back" value="back">Отменить</button>
    </form>
</section>

</body>
</html>
