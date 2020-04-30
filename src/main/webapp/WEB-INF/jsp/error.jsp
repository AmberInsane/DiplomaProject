<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page session="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="error.title"/></title>
    <spring:url value="/resources/core/image/error.jpg" var="errorImg" />
</head>
<jsp:include page="parts/header.jsp"/>
<body>
<div class="container">
    <h1><spring:message code="error.title"/></h1>
    <p>${exception.message}</p>
    ${exception.message}.
    <c:forEach items="${exception.stackTrace}" var="stackTrace">
        ${stackTrace}
    </c:forEach>
    <br>
    <img src="${errorImg}"/>
    <br>
</div>
<jsp:include page="parts/footer.jsp"/>
</body>
</html>