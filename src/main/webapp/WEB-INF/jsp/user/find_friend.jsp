<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 22.04.2020
  Time: 20:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="user.menu.friends"/></title>
    <spring:url value="/user/find_friends" var="findUserUrl"/>
</head>
<body>
<jsp:include page="../parts/header.jsp"/>
<div class="container">
    <c:if test="${not empty msg_code}">
        <div class="alert alert-${css} alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <strong><spring:message code="${msg_code}"/></strong>
            <c:if test="${not empty count}">
                <spring:message code="messages.found"/> ${count} <spring:message
                    code="${count_type_code}"/>
            </c:if>
        </div>
    </c:if>
    <h2><spring:message code="user.menu.friends"/></h2>
    <form:form class="form-horizontal" method="post" action="${findUserUrl}">
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <label class="col-sm-2 control-label"><spring:message code="user.username"/></label>
            <div class="col-sm-10">
                <input type="text" name="friend_name"/>
            </div>
            <button type="submit" class="btn-lg btn-primary pull-right"><spring:message code="action.find"/></button>
        </div>
    </form:form>

    <c:if test="${not empty msg}">
        <div class="alert alert-${css} alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <strong>${msg}</strong>
        </div>
    </c:if>

    <c:if test="${not empty friend_list}">
        <table class="table table-striped">
            <tr>
                <th><spring:message code="user.username"/></th>
            </tr>
            <c:forEach items="${friend_list}" var="friend">
                <tr>
                    <td>
                        <spring:url value="/user/${friend.id}" var="showUrl"/>
                        <a href="${showUrl}">${friend.username}</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>
