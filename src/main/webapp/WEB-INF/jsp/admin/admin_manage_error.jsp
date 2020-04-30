<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 11.04.2020
  Time: 18:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title><spring:message code="error.title"/></title>
    <spring:url value="/resources/core/image/sadcat.jpg" var="catImg" />
</head>
<body>
<jsp:include page="../parts/header.jsp"/>
<div class="container">
    <h2><spring:message code="admin.error"/></h2>
    <img src="${catImg}"/>
    <br>
    <a href="${pageContext.request.contextPath}/admin/manage"><spring:message code="admin.error.ok"/></a>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>
