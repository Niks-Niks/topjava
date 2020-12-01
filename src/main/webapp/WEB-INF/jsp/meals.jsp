<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<section>

    <div class="container">
        <h3>
            <div class="filter-title"><spring:message code="meal.title"/></div>
        </h3>
        <div class="filter-box">

            <div class="filter-body row">
                <form method="get" action="meals/filter" id="filter">
                    <dl class="offset-1 col-2">
                        <dt><spring:message code="meal.startDate"/>:</dt>
                        <dd><input class="form-control" name="startDate" value="${param.startDate}"></dd>
                    </dl>
                    <dl class="col-2">
                        <dt><spring:message code="meal.endDate"/>:</dt>
                        <dd><input class="form-control" name="endDate" value="${param.endDate}"></dd>
                    </dl>
                    <dl class="offset-2 col-2">
                        <dt><spring:message code="meal.startTime"/>:</dt>
                        <dd><input class="form-control" name="startTime" value="${param.startTime}"></dd>
                    </dl>
                    <dl class="col-2">
                        <dt><spring:message code="meal.endTime"/>:</dt>
                        <dd><input class="form-control" name="endTime" value="${param.endTime}"></dd>
                    </dl>
                </form>
            </div>

            <div class="filter-footer">
                <button class="btn btn-info mr-1" onclick="clean()"><spring:message code="meal.filterBtnCancel"/></button>
                <button class="btn btn-info mr-1">
                    <spring:message code="meal.filter"/></button>
            </div>
        </div>


        <hr>

        <a href="meals/create" class="btn btn-info mr-5 addMeal"><spring:message code="meal.add"/></a>

        <hr>

        <div class="dataTables_wrapper dt-bootstrap4 no-footer">
            <div class="row">
                <table id="datatable" class="table table-striped dataTable no-footer" aria-describedby="datatable_info" role="grid"
                       border="1" cellpadding="8" cellspacing="0" aria-describedby="datatable_info">
                    <thead>
                    <tr role="row">
                        <th class="sorting_desc" tabindex="0" aria-controls="datatable" aria-sort="descending"
                            rowspan="1" colspan="1">
                            <spring:message code="meal.dateTime"/></th>
                        <th class="sorting_desc" tabindex="0" aria-controls="datatable" aria-sort="descending"
                            rowspan="1" colspan="1">
                            <spring:message code="meal.description"/></th>
                        <th class="sorting_desc" tabindex="0" aria-controls="datatable" aria-sort="descending"
                            rowspan="1" colspan="1">
                            <spring:message code="meal.calories"/></th>
                        <th class="sorting_desc" tabindex="0" aria-controls="datatable" aria-sort="descending"
                            rowspan="1" colspan="1"></th>
                        <th class="sorting_desc" tabindex="0" aria-controls="datatable" aria-sort="descending"
                            rowspan="1" colspan="1"></th>
                    </tr>
                    </thead>
                    <c:forEach items="${meals}" var="meal">
                        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealTo"/>
                        <tr data-mealExcess="${meal.excess}" role="row" class="odd">
                            <td>
                                    <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                                    <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                                    <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                                    ${fn:formatDateTime(meal.dateTime)}
                            </td>
                            <td>${meal.description}</td>
                            <td>${meal.calories}</td>
                            <td><a href="meals/update?id=${meal.id}"><spring:message code="common.update"/></a></td>
                            <td><a href="meals/delete?id=${meal.id}" onclick="deleteRow(${meal.id})"><spring:message
                                    code="common.delete"/></a></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>