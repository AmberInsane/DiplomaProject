<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 05.04.2020
  Time: 18:53
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Log in with your account</title>
    <spring:url value="/login" var="loginUrl"/>
</head>

<body>
<jsp:include page="parts/header.jsp"/>
<div class="container">

    <form class="form-horizontal" name="loginForm" method="POST" action="${loginUrl}">
        <h3>Вход в систему</h3>

        <%-- <spring:bind path="username">
             <div class="form-group ${status.error ? 'has-error' : ''}">
                 <label class="col-sm-2 control-label">Title</label>
                 <div class="col-sm-10">--%>
        <%--     <form:input path="username" type="text" class="form-control " id="username"
                         placeholder="username"/>--%>
        <div class="form-group">
            <label class="col-sm-2 control-label">Username</label>
            <div class="col-sm-5">
                <input name="username" type="text" placeholder="Username"/>
            </div>
        </div>

        <%--      <form:errors path="username" class="control-label"/>
          </div>
      </div>
    </spring:bind>--%>

        <%--  <spring:bind path="password">
              <div class="form-group ${status.error ? 'has-error' : ''}">
                  <label class="col-sm-2 control-label">Password</label>
                  <div class="col-sm-10">--%>
        <div class="form-group">
            <label class="col-sm-2 control-label">Password</label>
            <div class="col-sm-5">
                <div class="col-sm-5">
                    <input name="password" type="password" placeholder="Password"/>
                </div>
            </div>
        </div>


        <%--            <form:errors path="password" class="control-label"/>
                </div>
            </div>
        </spring:bind>--%>

        <button type="submit" class="btn center-block btn-primary">Log In</button>

        <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
            <div class="error">
                Your login attempt was not successful due to <br/><br/>
                <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
            </div>
        </c:if>
    </form>
</div>
<jsp:include page="parts/footer.jsp"/>
</body>
</html>
