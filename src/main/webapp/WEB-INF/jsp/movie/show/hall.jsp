<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 29.04.2020
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>${hall.name}</title>
</head>
<body>
<jsp:include page="../../parts/header.jsp"/>

<div class="container">
    <h2>${hall.name}</h2>
    <div class="row">
        <label class="col-sm-2"><spring:message code="hall.description"/></label>
        <div class="col-sm-10">${hall.description}</div>
    </div>
    <div class="row">
        <label class="col-sm-2"><spring:message code="hall.capacity"/></label>
        <div class="col-sm-10">${hall.capacity}</div>
    </div>
</div>
<jsp:include page="../../parts/footer.jsp"/>
</body>
</html>
