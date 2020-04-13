<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 11.04.2020
  Time: 20:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="../parts/header.jsp"/>


<div class="container">
    <c:choose>
        <c:when test="${movieForm['new']}">
            <h1>Add Movie</h1>
        </c:when>
        <c:otherwise>
            <h1>Update Movie</h1>
        </c:otherwise>
    </c:choose>
    <br/>

    <spring:url value="/movie/add_movie" var="movieActionUrl"/>

    <form:form class="form-horizontal" method="POST" modelAttribute="movieForm" action="${movieActionUrl}">
        <form:hidden path="id"/>
        <div>
            <spring:bind path="title">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-2 control-label">Titile</label>
                    <div class="col-sm-10">
                        <form:input path="title" type="text" class="form-control " id="title" placeholder="Title"/>
                        <form:errors path="title" class="control-label"/>
                    </div>
                </div>
            </spring:bind>

            <spring:bind path="description">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-2 control-label">Description</label>
                    <div class="col-sm-10">
                        <form:textarea path="description" rows="5" class="form-control" id="description"
                                       placeholder="Description"/>
                        <form:errors path="description" class="control-label"/>
                    </div>
                </div>
            </spring:bind>

            <spring:bind path="year">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-2 control-label">Year</label>
                    <div class="col-sm-10">
                        <form:input path="year" cols="4" class="form-control" id="year" placeholder="Year"/>
                        <form:errors path="year" class="control-label"/>
                    </div>
                </div>
            </spring:bind>

            <spring:bind path="genre">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-2 control-label">Genre</label>
                    <div class="col-sm-10">
                        <form:select path="genre" items="${genreList}" multiple="true" size="5" class="form-control" />
                        <form:errors path="genre" class="control-label"/>
                    </div>
                </div>
            </spring:bind>

            <spring:bind path="timeLength">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-2 control-label">Genre</label>
                    <div class="col-sm-10">
                        <form:input path="timeLength" cols="4" class="form-control" id="timeLength" placeholder="TimeLength"/>
                        <form:errors path="timeLength" class="control-label"/>
                    </div>
                </div>
            </spring:bind>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${movieForm['new']}">
                        <button type="submit" class="btn-lg btn-primary pull-right">Add</button>
                    </c:when>
                    <c:otherwise>
                        <button type="submit" class="btn-lg btn-primary pull-right">Update</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>


    </form:form>
    <a href="${pageContext.request.contextPath}/">Назад</a>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>
