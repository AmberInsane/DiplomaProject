<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 22.04.2020
  Time: 20:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Find friend</title>
    <spring:url value="/user/find_friends" var="findUserUrl"/>
</head>
<body>
<jsp:include page="../parts/header.jsp"/>
<div class="container">
    <h2>Find friends</h2>
    <form:form class="form-horizontal" method="post" action="${findUserUrl}">
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <label class="col-sm-2 control-label">User name to find</label>
            <div class="col-sm-10">
                <input type="text" name="friend_name"/>
            </div>
            <button type="submit" class="btn-lg btn-primary pull-right">Find</button>
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
                <th>Name</th>
                <th>Birthday</th>
                <th>Send request</th>
            </tr>
            <c:forEach items="${friend_list}" var="friend">
                <tr>
                    <td>
                        <spring:url value="/user/${friend.id}" var="showUrl"/>
                        <a href="${showUrl}">${friend.username}</a></td>
                    <td>${friend.birthday}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>
