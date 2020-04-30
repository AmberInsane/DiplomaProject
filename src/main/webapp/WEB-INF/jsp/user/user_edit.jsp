<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 26.04.2020
  Time: 14:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title><spring:message code="action.update"/> <spring:message code="user.form1"/></title>
    <spring:url value="/user/my_edit" var="userActionUrl"/>
</head>
<body>
<jsp:include page="../parts/header.jsp"/>
<body>
<div class="container">
    <h1><spring:message code="action.update"/> <spring:message code="user.form1"/></h1>
    <form:form class="form-horizontal" method="post"
               modelAttribute="userInfoForm" action="${userActionUrl}">
        <form:hidden path="id"/>
        <spring:bind path="aboutUser">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label"><spring:message code="user.info.about"/></label>
                <div class="col-sm-10">
                    <form:textarea path="aboutUser" rows="5" class="form-control" id="aboutUser"/>
                    <form:errors path="aboutUser" class="control-label"/>
                </div>
            </div>
        </spring:bind>

        <spring:bind path="favoriteGenres">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label"><spring:message code="user.info.genres"/></label>
                <div class="col-sm-10">
                    <form:textarea path="favoriteGenres" rows="5" class="form-control" id="favoriteGenres"/>
                    <form:errors path="favoriteGenres" class="control-label"/>
                </div>
            </div>
        </spring:bind>

        <spring:bind path="favoriteMovies">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label"><spring:message code="user.info.movies"/></label>
                <div class="col-sm-10">
                    <form:textarea path="favoriteMovies" rows="5" class="form-control" id="favoriteMovies"/>
                    <form:errors path="favoriteMovies" class="control-label"/>
                </div>
            </div>
        </spring:bind>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn-lg btn-primary pull-right"><spring:message code="action.update"/>
                </button>
            </div>
        </div>
    </form:form>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>
