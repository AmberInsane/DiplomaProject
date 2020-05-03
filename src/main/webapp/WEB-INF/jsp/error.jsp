<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page session="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="error.title"/></title>
    <spring:url value="/resources/core/image/error.jpg" var="errorImg"/>

    <spring:url value="/resources/core/css/index.css" var="indexCss"/>

    <link href="${indexCss}" rel="stylesheet"/>
</head>
<body>
<div class="wrapper">
    <div class="wrapper-inner">
        <jsp:include page="parts/header.jsp"/>
        <div class="container mt-5">
            <h1><spring:message code="error.title"/></h1>
            <c:choose>
                <c:when test="${not empty exception}">
                    <p>${exception.message}</p>
                    ${exception.message}
                    ${errorMsg}
                    <c:forEach items="${exception.stackTrace}" var="stackTrace">
                        ${stackTrace}
                    </c:forEach>
                </c:when>
                <c:when test="${not empty errorCode}">
                    <spring:message code="${errorCode}"/>
                </c:when>
            </c:choose>

            <br>
            <img src="${errorImg}"/>
            <br>
        </div>
    </div>
    <jsp:include page="parts/footer.jsp"/>
</div>
</body>
</html>