<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 07.04.2020
  Time: 20:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="admin.title"/></title>
    <spring:url value="/resources/core/css/index.css" var="indexCss"/>

    <link href="${indexCss}" rel="stylesheet"/>
</head>
<body>
<div class="wrapper">
    <div class="wrapper-inner">
        <jsp:include page="../parts/header.jsp"/>
        <div class="container mt-5">
            <c:if test="${not empty msg_code}">
                <div class="alert alert-${css} alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <strong><spring:message code="${msg_code}"/></strong>
                </div>
            </c:if>
            <h3><spring:message code="admin.title"/></h3>
            <table class="table table-hover">
                <c:forEach items="${adminUsers}" var="user">
                    <tr>
                        <td>${user.username}</td>
                        <td>
                            <c:if test="${!pageContext.request.userPrincipal.name.equals(user.username)}">
                                <spring:url value="manage/delete_admin/${user.id}" var="deleteAdminUrl"/>
                                <button class="btn btn-outline-danger" onclick="location.href='${deleteAdminUrl}'">
                                    <spring:message code="admin.action.remove.privileges"/></button>
                            </c:if>
                            <c:if test="${pageContext.request.userPrincipal.name.equals(user.username)}">
                                <label><spring:message code="admin.you"/></label>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <h3><spring:message code="user.form3"/></h3>
            <table class="table table-hover">
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.username}</td>
                        <td>
                            <spring:url value="manage/add_admin/${user.id}" var="addAdminUrl"/>
                            <button class="btn btn-outline-primary" onclick="location.href='${addAdminUrl}'"><spring:message
                                    code="admin.action.add.privileges"/></button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
    <jsp:include page="../parts/footer.jsp"/>
</div>
</body>
</html>
