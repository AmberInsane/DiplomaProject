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
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Purse</title>
    <spring:url value="/user/my_purse" var="userActionUrl"/>
</head>
<body>
<jsp:include page="../parts/header.jsp"/>
<body>
<div class="container">
    <h1>My Purse</h1>
    <br/>
    <div class="row">
        <label class="col-sm-2">Balance</label>
        <div class="col-sm-10">${balance}</div>
    </div>
    <form class="form-horizontal" method="post" action="${userActionUrl}">
        <div class="form-group">
            <label class="col-sm-2 control-label">Add money</label>
            <div class="col-sm-5">
                <input name="sum" type="number" step="0.01" />
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn-lg btn-primary pull-right">Add
                </button>
            </div>
        </div>
    </form>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>
