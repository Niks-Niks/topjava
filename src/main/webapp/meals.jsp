<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>

<html>
<head>
    <title>Meal list</title>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <form method="get" action="meals">
        <input type="hidden" name="action" value="filter">
        <div>
            <label>From date: </label>
            <input name="dateStart" type="date">
        </div>

        <div>
            <label>To date: </label>
            <input name="dateEnd" type="date">
        </div>

        <div>
            <label>From time: </label>
            <input name="timeStart" type="time">
        </div>

        <div>
            <label>To time: </label>
            <input name="timeEnd" type="time">
        </div>
        <button onclick="window.history.back()" type="button">Cancel</button>
        <button type="submit">Filter</button>
    </form>
    <hr/>
    <h2>Meals</h2>
    <a href="meals?action=create">Add Meal</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr style="${meal.excess ? 'color:red' : 'color:green'}">
                <td>${meal.date} ${meal.time}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>