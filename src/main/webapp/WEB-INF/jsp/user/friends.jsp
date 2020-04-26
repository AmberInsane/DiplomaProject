<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 26.04.2020
  Time: 17:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Friends</title>
</head>
<body>
<jsp:include page="../parts/header.jsp"/>
<div class="container">
    <security:authorize access="isAuthenticated()">
        <h2>my friends</h2>
        <c:if test="${inRequests.size() > 0}">
            <div>
                <h3>Входящие заявки</h3>
                <table class="table table-striped">
                    <c:forEach items="${inRequests}" var="request">
                        <tr>
                            <td>
                                <spring:url value="/user/${request.userRequest.id}" var="showUrl"/>
                                <a href="${showUrl}">${request.userRequest.username}</a>
                            </td>
                            <td>
                                <spring:url value="/user/request/accept/${request.id}" var="acceptUrl"/>
                                <button class="btn btn-primary" onclick="this.disabled=true;post('${acceptUrl}')">
                                    Принять
                                </button>
                            </td>
                            <td>
                                <spring:url value="/user/request/deny/${request.id}" var="denyUrl"/>
                                <button class="btn btn-warning" onclick="this.disabled=true;post('${denyUrl}')">Отклонить
                                </button>
                            </td>
                            <td>
                                <spring:url value="/user/friend/${request.userRequest.id}/black" var="blackUrl"/>
                                <button class="btn btn-danger" onclick="this.disabled=true;post('${blackUrl}')">
                                    Отправить в
                                    черный список
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </c:if>
        <c:choose>
            <c:when test="${friends.size() > 0}">
                <div>
                    <h3>My Friends</h3>
                    <table class="table table-striped">
                        <c:forEach items="${friends}" var="friend">
                            <tr>
                                <td>
                                    <spring:url value="/user/${friend.id}" var="showUrl"/>
                                    <a href="${showUrl}">${friend.username}</a>
                                </td>
                                <td>
                                    <spring:url value="/user/friend/${friend.id}/delete" var="denyUrl"/>
                                    <button class="btn btn-warning" onclick="this.disabled=true;post('${denyUrl}')">
                                        Удалить
                                    </button>
                                </td>
                                <td>
                                    <spring:url value="/user/friend/${friend.id}/block" var="blockUrl"/>
                                    <button class="btn btn-danger" onclick="this.disabled=true;post('${blockUrl}')">
                                        Отправить в
                                        черный список
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </c:when>
            <c:otherwise>
                <div>
                    <label class="text-info">You don't have friends. YET</label>
                </div>
            </c:otherwise>
        </c:choose>
        <c:if test="${outRequests.size() > 0}">
            <div>
                <h3>Исходящие заявки</h3>
                <table class="table table-striped">
                    <c:forEach items="${outRequests}" var="request">
                        <tr>
                            <td>
                                <spring:url value="/user/${request.userResponse.id}" var="showUrl"/>
                                <a href="${showUrl}">${request.userResponse.username}</a>
                            </td>
                            <td>
                                <spring:url value="/user/request/cancel/${request.id}" var="cancelUrl"/>
                                <button class="btn btn-warning" onclick="this.disabled=true;post('${cancelUrl}')">
                                    Отменить запрос
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </c:if>
    </security:authorize>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>
