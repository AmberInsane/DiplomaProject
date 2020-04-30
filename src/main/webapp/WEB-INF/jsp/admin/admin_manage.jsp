<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 07.04.2020
  Time: 20:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="admin.title"/></title>
</head>
<body>
<jsp:include page="../parts/header.jsp"/>
<div class="container">
    <h1><spring:message code="admin.title"/></h1>
    <table class="table table-striped">
        <tr>
            <th><spring:message code="user.username"/></th>
            <th></th>
        </tr>
        <c:forEach items="${adminUsers}" var="user">
            <tr>
                <td>${user.username}</td>
                <td>
                        <c:if test="${!pageContext.request.userPrincipal.name.equals(user.username)}">
                            <spring:url value="manage/delete_admin/${user.id}" var="deleteAdminUrl"/>
                            <button class="btn btn-danger" onclick="location.href='${deleteAdminUrl}'"><spring:message code="admin.action.remove.privileges"/></button>
                        </c:if>
                        <c:if test="${pageContext.request.userPrincipal.name.equals(user.username)}">
                            <label><spring:message code="admin.you"/></label>
                        </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
    <h1><spring:message code="user.form3"/></h1>
    <table class="table table-striped">
        <tr>
            <th>${user.username}</th>
            <th></th>
        </tr>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.username}</td>
                <td>
                    <spring:url value="manage/add_admin/${user.id}" var="addAdminUrl"/>
                    <button class="btn btn-primary" onclick="location.href='${addAdminUrl}'"><spring:message code="admin.action.add.privileges"/></button>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>
