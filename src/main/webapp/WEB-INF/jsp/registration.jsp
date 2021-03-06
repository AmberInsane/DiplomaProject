<%--
  Created by IntelliJ IDEA.
  User: stankevich_m
  Date: 16.04.2020
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title><spring:message code="registration.title"/></title>
    <spring:url value="/registration" var="userActionUrl"/>
    <spring:url value="/resources/core/css/index.css" var="indexCss"/>

    <link href="${indexCss}" rel="stylesheet"/>
</head>

<body>

<div class="wrapper">
    <div class="wrapper-inner">
        <jsp:include page="parts/header.jsp"/>
        <div class="container mt-5">
            <h2><spring:message code="action.add"/> <spring:message code="user.form2"/></h2>
            <form:form class="form-horizontal" method="post"
                       modelAttribute="userForm" action="${userActionUrl}">
                <spring:bind path="username">
                    <div class="form-group ${status.error ? 'has-danger' : ''}">
                        <label class="col-sm-2 form-control-label"><spring:message code="user.username"/></label>
                        <div class="col-sm-10">
                            <form:input path="username" type="text" class="form-control"
                                        id="username"/>
                            <form:errors path="username" class="form-control-feedback"/>
                        </div>
                    </div>
                </spring:bind>

                <spring:bind path="email">
                    <div class="form-group ${status.error ? 'has-danger' : ''}">
                        <label class="col-sm-2 form-control-label"><spring:message code="user.email"/></label>
                        <div class="col-sm-10">
                            <form:input path="email" class="form-control"
                                        id="email"/>
                            <form:errors path="email" class="form-control-feedback"/>
                        </div>
                    </div>
                </spring:bind>

                <spring:bind path="password">
                    <div class="form-group ${status.error ? 'has-danger' : ''}">
                        <label class="col-sm-2 form-control-label"><spring:message code="user.password"/></label>
                        <div class="col-sm-10">
                            <form:password path="password" class="form-control"
                                           id="password"/>
                            <form:errors path="password" class="form-control-feedback"/>
                        </div>
                    </div>
                </spring:bind>

                <spring:bind path="passwordConfirm">
                    <div class="form-group ${status.error ? 'has-danger' : ''}">
                        <label class="col-sm-2 form-control-label"><spring:message code="user.password.confirm"/></label>
                        <div class="col-sm-10">
                            <form:password path="passwordConfirm" class="form-control"
                                           id="password"/>
                            <form:errors path="passwordConfirm" class="form-control-feedback"/>
                        </div>
                    </div>
                </spring:bind>

                <div class="form-group">
                    <div class="col-sm-10">
                        <button type="submit" class="btn btn-primary pull-right"><spring:message code="action.add"/>
                        </button>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
    <jsp:include page="parts/footer.jsp"/>
</div>
</body>
</html>
