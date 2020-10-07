<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<html>
<head>
    <title>List meal</title>
</head>
<body>

<section class="section">
    <div class="centre">
        <a href="meals?action=create">Create new meal</a>
        <table border="1" cellpadding="8" cellspacing="0">
            <tr>
                <th>Date</th>
                <th>Description</th>
                <th>Calories</th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach items="${mealTo}" var="mealTo">
                <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo"/>
                <tr style="${mealTo.excess ? 'color:red' : 'color:green'}">
                    <td>${mealTo.date} ${mealTo.time}</td>
                    <td>${mealTo.description}</td>
                    <td>${mealTo.calories}</td>
                    <td><a href="meals?key=${mealTo.id}&action=delete">Delete</a></td>
                    <td><a href="meals?key=${mealTo.id}&action=edit">Edit</a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</section>

</body>
</html>
