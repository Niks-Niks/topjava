<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 07.10.2020
  Time: 17:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create</title>
</head>
<body>
<section>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <h1>Create meal:</h1>
        Date:
        <input type="datetime-local" name="date" size=50>

        Description:
        <input type="text" name="desc" size=50>

        Calories:
        <input type="number" name="cal" size=50>

        <hr>
        <button type="submit">Create</button>
        <button name="back" type="back" value="back">Back</button>
    </form>
</section>
</body>
</html>
