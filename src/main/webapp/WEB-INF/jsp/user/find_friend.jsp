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
                    <c:if test="${not empty count}">
                        <spring:message code="messages.found"/> ${count} <spring:message
                            code="${count_type_code}"/>
                    </c:if>
                </div>
            </c:if>
            <h2><spring:message code="user.menu.friends"/></h2>
            <form:form class="form-horizontal" method="post" action="${findUserUrl}">
                <div class="form-group ${status.error ? 'has-danger' : ''}">
                    <label class="col-sm-2 form-control-label"><spring:message code="user.username"/></label>
                    <input type="text" name="friend_name" class="col-sm-7"/>
                    <button type="submit" class="btn btn-primary col-sm-2"><spring:message
                            code="action.find"/></button>
                </div>
            </form:form>

            <c:if test="${not empty friend_list}">
                <table class="table table-hover">
                    <tbody>

                    <c:forEach items="${friend_list}" var="friend">
                        <tr>
                            <td>
                                <spring:url value="/user/${friend.id}" var="showUrl"/>
                                <a href="${showUrl}">${friend.username}</a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>
    <jsp:include page="../parts/footer.jsp"/>
</div>
</body>
</html>
