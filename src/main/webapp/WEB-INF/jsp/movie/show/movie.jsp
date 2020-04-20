<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 20.04.2020
  Time: 18:40
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="../../parts/header.jsp"/>

<div class="container">
    <c:if test="${not empty msg}">
        <div class="alert alert-${css} alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <strong>${msg}</strong>
        </div>
    </c:if>

    <h1>Movie Detail</h1>
    <br/>

    <div class="row">
        <label class="col-sm-2">Title</label>
        <div class="col-sm-10">${movie.title}</div>
    </div>

    <div class="row">
        <label class="col-sm-2">Description</label>
        <div class="col-sm-10">${movie.description}</div>
    </div>

    <div class="row">
        <label class="col-sm-2">Year</label>
        <div class="col-sm-10">
            <div>
                <spring:url value="/movie/year/${movie.year}" var="yearUrl"/>
                <a href="${yearUrl}">${movie.year}</a>
            </div>
        </div>
    </div>

    <div class="row">
        <label class="col-sm-2">Genre</label>
        <div class="col-sm-10">
            <c:forEach var="genre" items="${movie.genre}" varStatus="loop">
                <spring:url value="/movie/genre/${genre.id}" var="genreUrl"/>
                <a href="${genreUrl}">${genre.name}</a>
                <c:if test="${not loop.last}">,</c:if>
            </c:forEach>
        </div>
    </div>

    <div class="row">
        <label class="col-sm-2">Time</label>
        <div class="col-sm-10">${movie.timeLength}</div>
    </div>

    <spring:url value="/movie/session/movie/${movie.id}" var="sessionsUrl"/>
    <button class="btn btn-primary" onclick="location.href='${sessionsUrl}'">Find Sessions</button>

</div>

<jsp:include page="../../parts/footer.jsp"/>
</body>
</html>
