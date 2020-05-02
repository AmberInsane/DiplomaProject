<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 27.04.2020
  Time: 12:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="action.buy"/> <spring:message code="ticket.form2"/></title>
    <spring:url value="/ticket/add" var="ticketActionUrl"/>
</head>
<body>
<jsp:include page="../../parts/header.jsp"/>
<div class="container">
    <h1><spring:message code="action.buy"/> <spring:message code="ticket.form2"/></h1>

    <c:if test="${not empty msg_code}">
        <div class="alert alert-${css} alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <strong><spring:message code="${msg_code}"/></strong>
        </div>
    </c:if>

    <div class="row">
        <label class="col-sm-2"><spring:message code="movie.form"/></label>
        <div class="col-sm-10">${ticketForm.session.movie.title}</div>
    </div>

    <div class="row">
        <label class="col-sm-2"><spring:message code="session.time"/></label>
        <div class="col-sm-10">${ticketForm.session.dateFormatText}</div>
    </div>

    <div class="row">
        <label class="col-sm-2"><spring:message code="session.price"/></label>
        <div class="col-sm-10">${ticketForm.session.price}</div>
    </div>

    <div class="row">
        <label class="col-sm-2"><spring:message code="user.balance"/></label>
        <div class="col-sm-10">${ticketForm.userBy.balance}</div>
    </div>

    <form:form class="form-horizontal" method="POST" modelAttribute="ticketForm" action="${ticketActionUrl}">
        <form:hidden path="id"/>
        <form:hidden path="session"/>
        <form:hidden path="userBy"/>
        <div>
            <spring:bind path="usersFor">
                <div class="form-group ${status.error ? 'has-danger' : ''}">
                    <label class="col-sm-2 form-control-label"><spring:message code="ticket.for.users"/></label>
                    <div class="col-sm-5">
                        <form:select path="usersFor" multiple="true" size="5" class="form-control">
                            <form:options items="${friends}" itemValue="id" itemLabel="username"/>
                        </form:select>
                        <form:errors path="usersFor" class="form-control-label"/>
                    </div>
                    <div class="col-sm-5"></div>
                </div>
            </spring:bind>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-primary pull-right"><spring:message code="action.buy"/></button>
            </div>
        </div>
    </form:form>
</div>
<jsp:include page="../../parts/footer.jsp"/>
</body>
</html>
