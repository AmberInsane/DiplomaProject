<%--
  Created by IntelliJ IDEA.
  User: stankevich_m
  Date: 30.04.2020
  Time: 13:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="user.action.balance"/></title>
    <spring:url value="/user/my_purse" var="userActionUrl"/>
</head>
<body>
<jsp:include page="../parts/header.jsp"/>
<body>
<div class="container">
    <h1><spring:message code="user.action.balance"/></h1>
    <c:if test="${not empty msg_code}">
        <div class="alert alert-${css} alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <strong><spring:message code="${msg_code}"/></strong>
        </div>
    </c:if>
    <div class="row">
        <label class="col-sm-2"><spring:message code="user.balance"/></label>
        <div class="col-sm-10">${balance}</div>
    </div>
    <form class="form-horizontal" method="post" action="${userActionUrl}">
        <div class="form-group">
            <label class="col-sm-2 form-control-label"><spring:message code="text.sum"/></label>
            <div class="col-sm-5">
                <input name="sum" type="number" step="0.01"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-primary pull-right"><spring:message code="action.add"/>
                </button>
            </div>
        </div>
    </form>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>
