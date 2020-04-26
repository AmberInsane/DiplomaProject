<%--
  Created by IntelliJ IDEA.
  User: stankevich_m
  Date: 16.04.2020
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Регистрация</title>
    <spring:url value="/registration" var="userActionUrl"/>
</head>
<jsp:include page="parts/header.jsp"/>
<body>
<div class="container">
    <h1>Add User</h1>
    <br/>
    <form:form class="form-horizontal" method="post"
               modelAttribute="userForm" action="${userActionUrl}">

        <spring:bind path="username">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label">Name</label>
                <div class="col-sm-10">
                    <form:input path="username" type="text" class="form-control"
                                id="username"/>
                    <form:errors path="username" class="control-label"/>
                </div>
            </div>
        </spring:bind>

        <spring:bind path="email">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label">Email</label>
                <div class="col-sm-10">
                    <form:input path="email" class="form-control"
                                id="email"/>
                    <form:errors path="email" class="control-label"/>
                </div>
            </div>
        </spring:bind>

        <spring:bind path="password">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label">Password</label>
                <div class="col-sm-10">
                    <form:password path="password" class="form-control"
                                   id="password"/>
                    <form:errors path="password" class="control-label"/>
                </div>
            </div>
        </spring:bind>

        <spring:bind path="passwordConfirm">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <div class="col-sm-10">
                    <form:password path="passwordConfirm" class="form-control"
                                   id="password" placeholder="password"/>
                    <form:errors path="passwordConfirm" class="control-label"/>
                </div>
            </div>
        </spring:bind>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn-lg btn-primary pull-right">Add
                </button>
            </div>
        </div>

    </form:form>
</div>
<jsp:include page="parts/footer.jsp"/>
</body>
</html>
