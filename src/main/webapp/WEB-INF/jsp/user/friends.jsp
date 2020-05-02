<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 26.04.2020
  Time: 17:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title><spring:message code="user.action.friends.my"/></title>
    <spring:url value="/resources/core/css/index.css" var="indexCss"/>

    <link href="${indexCss}" rel="stylesheet"/>
</head>
<body>
<div class="wrapper">
    <div class="wrapper-inner">
        <jsp:include page="../parts/header.jsp"/>
        <div class="container mt-5">
            <security:authorize access="isAuthenticated()">
                <h2><spring:message code="user.action.friends.my"/></h2>
                <c:if test="${inRequests.size() > 0}">
                    <div>
                        <h3><spring:message code="user.action.friends.in"/></h3>
                        <table class="table table-striped">
                            <c:forEach items="${inRequests}" var="request">
                                <tr>
                                    <td>
                                        <spring:url value="/user/${request.userRequest.id}" var="showUrl"/>
                                        <a href="${showUrl}">${request.userRequest.username}</a>
                                    </td>
                                    <td>
                                        <spring:url value="/user/request/accept/${request.id}" var="acceptUrl"/>
                                        <button class="btn btn-primary"
                                                onclick="this.disabled=true;post('${acceptUrl}')">
                                            <spring:message code="user.action.friends.accept"/>
                                        </button>
                                    </td>
                                    <td>
                                        <spring:url value="/user/request/refuse/${request.id}" var="refuseUrl"/>
                                        <button class="btn btn-warning"
                                                onclick="this.disabled=true;post('${refuseUrl}')">
                                            <spring:message code="user.action.friends.refuse"/>
                                        </button>
                                    </td>
                                    <td>
                                        <spring:url value="/user/friend/${request.userRequest.id}/block"
                                                    var="blockUrl"/>
                                        <button class="btn btn-danger" onclick="this.disabled=true;post('${blockUrl}')">
                                            <spring:message code="user.action.friends.block"/>
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
                            <h3><spring:message code="user.action.friends.my"/></h3>
                            <table class="table table-striped">
                                <c:forEach items="${friends}" var="friend">
                                    <tr>
                                        <td>
                                            <spring:url value="/user/${friend.id}" var="showUrl"/>
                                            <a href="${showUrl}">${friend.username}</a>
                                        </td>
                                        <td>
                                            <spring:url value="/user/friend/${friend.id}/delete" var="deleteUrl"/>
                                            <button class="btn btn-warning"
                                                    onclick="this.disabled=true;post('${deleteUrl}')">
                                                <spring:message code="user.action.friends.delete"/>
                                            </button>
                                        </td>
                                        <td>
                                            <spring:url value="/user/friend/${friend.id}/block" var="blockUrl"/>
                                            <button class="btn btn-danger"
                                                    onclick="this.disabled=true;post('${blockUrl}')">
                                                <spring:message code="user.action.friends.block"/>
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div>
                            <label class="text-info"><spring:message code="user.action.friends.no"/></label>
                        </div>
                    </c:otherwise>
                </c:choose>
                <c:if test="${outRequests.size() > 0}">
                    <div>
                        <h3><spring:message code="user.action.friends.out"/></h3>
                        <table class="table table-striped">
                            <c:forEach items="${outRequests}" var="request">
                                <tr>
                                    <td>
                                        <spring:url value="/user/${request.userResponse.id}" var="showUrl"/>
                                        <a href="${showUrl}">${request.userResponse.username}</a>
                                    </td>
                                    <td>
                                        <spring:url value="/user/request/cancel/${request.id}" var="cancelUrl"/>
                                        <button class="btn btn-warning"
                                                onclick="this.disabled=true;post('${cancelUrl}')">
                                            <spring:message code="user.action.friends.cancel"/>
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </c:if>
            </security:authorize>
        </div>
    </div>
    <jsp:include page="../parts/footer.jsp"/>
</div>
</body>
</html>
