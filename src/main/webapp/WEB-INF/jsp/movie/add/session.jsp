<%--
  Created by IntelliJ IDEA.
  User: stankevich_m
  Date: 16.04.2020
  Time: 14:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Session</title>
    <spring:url value="/movie/add/session" var="sessionActionUrl"/>
    <spring:url value="/movie/session" var="returnUrl"/>
</head>
<body>
<jsp:include page="../../parts/header.jsp"/>


<div class="container">
    <button class="btn btn-primary" onclick="location.href='${returnUrl}'">Return to sessions</button>
    <c:choose>
        <c:when test="${sessionForm['new']}">
            <h1>Add Session</h1>
        </c:when>
        <c:otherwise>
            <h1>Update Movie</h1>
        </c:otherwise>
    </c:choose>
    <br/>
    <form:form class="form-horizontal" method="POST" modelAttribute="sessionForm" action="${sessionActionUrl}">
        <form:hidden path="id"/>
        <div>
           <%-- <spring:bind path="startDate">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-2 control-label">Start date</label>
                    <div class="col-sm-3"> &lt;%&ndash;pattern="dd.mm.yyyy HH:mm"&ndash;%&gt;
                        <form:input path="startDate" type="date" pattern="dd.mm.yyyy" class="input-sm form-control" id="startDate"/>
                        <form:errors path="startDate" class="control-label"/>
                    </div>
                </div>
            </spring:bind>--%>
            <spring:bind path="startTime">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-2 control-label">time</label>
                    <div class="col-sm-3">
                        <form:input path="dateFormatJSP" type="datetime-local" class="input-sm form-control" id="dateInitializer"/>
                        <form:errors path="dateFormatJSP" class="control-label"/>
                    </div>
                </div>
            </spring:bind>

            <spring:bind path="price">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-2 control-label">Price</label>
                    <div class="col-sm-10">
                        <form:input path="price" type="number" step="0.01" class="form-control " id="price" placeholder="Price"/>
                        <form:errors path="price" class="control-label"/>
                    </div>
                </div>
            </spring:bind>

            <spring:bind path="hall">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-2 control-label">Hall</label>
                    <div class="col-sm-5">
                        <form:select path="hall" multiple="false" size="5" class="form-control">
                            <form:options items="${hallList}" itemValue="id" itemLabel="name"/>
                        </form:select>
                        <form:errors path="hall" class="control-label"/>
                    </div>
                </div>
            </spring:bind>

            <spring:bind path="movie">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-2 control-label">Movie</label>
                    <div class="col-sm-5">
                        <form:select path="movie" multiple="false" size="5" class="form-control">
                            <form:options items="${movieList}" itemValue="id" itemLabel="title"/>
                        </form:select>
                        <form:errors path="movie" class="control-label"/>
                    </div>
                </div>
            </spring:bind>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${sessionForm['new']}">
                        <button type="submit" class="btn-lg btn-primary pull-right">Add</button>
                    </c:when>
                    <c:otherwise>
                        <button type="submit" class="btn-lg btn-primary pull-right">Update</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</div>
<jsp:include page="../../parts/footer.jsp"/>
</body>
</html>
