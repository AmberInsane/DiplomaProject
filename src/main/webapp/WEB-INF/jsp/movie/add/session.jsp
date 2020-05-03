<%--
  Created by IntelliJ IDEA.
  User: stankevich_m
  Date: 16.04.2020
  Time: 14:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title><spring:message code="session.form"/></title>
    <spring:url value="/admin/session/add" var="sessionActionUrl"/>
    <spring:url value="/movie/session" var="returnUrl"/>
    <spring:url value="/resources/core/css/index.css" var="indexCss"/>

    <link href="${indexCss}" rel="stylesheet"/>
</head>
<body>
<div class="wrapper">
    <div class="wrapper-inner">
        <jsp:include page="../../parts/header.jsp"/>
        <div class="container mt-5">
            <c:choose>
                <c:when test="${sessionForm['new']}">
                    <h2><spring:message code="action.add"/> <spring:message code="session.form"/></h2>
                </c:when>
                <c:otherwise>
                    <h2><spring:message code="action.update"/> <spring:message code="session.form"/></h2>
                </c:otherwise>
            </c:choose>
            <br/>
            <form:form class="form-horizontal" method="POST" modelAttribute="sessionForm" action="${sessionActionUrl}">
                <form:hidden path="id"/>
                <div>
                    <spring:bind path="startTime">
                        <div class="form-group ${status.error ? 'has-danger' : ''}">
                            <label class="col-sm-2 form-control-label"><spring:message code="session.time"/></label>
                            <div class="col-sm-4">
                                <form:input path="dateFormatJSP" type="datetime-local" class="input-sm form-control"
                                            id="dateInitializer"/>
                                <form:errors path="startTime" class="form-control-label"/>
                                <form:errors path="dateFormatJSP" class="form-control-label"/>
                            </div>
                        </div>
                    </spring:bind>

                    <spring:bind path="price">
                        <div class="form-group ${status.error ? 'has-danger' : ''}">
                            <label class="col-sm-2 form-control-label"><spring:message code="session.price"/></label>
                            <div class="col-sm-10">
                                <form:input path="price" type="number" step="0.01" class="form-control " id="price"/>
                                <form:errors path="price" class="form-control-label"/>
                            </div>
                        </div>
                    </spring:bind>

                    <spring:bind path="hall">
                        <div class="form-group ${status.error ? 'has-danger' : ''}">
                            <label class="col-sm-2 form-control-label"><spring:message code="hall.form"/></label>
                            <div class="col-sm-5">
                                <form:select path="hall" multiple="false" size="5" class="form-control">
                                    <form:options items="${hallList}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <form:errors path="hall" class="form-control-label"/>
                            </div>
                        </div>
                    </spring:bind>

                    <spring:bind path="movie">
                        <div class="form-group ${status.error ? 'has-danger' : ''}">
                            <label class="col-sm-2 form-control-label"><spring:message code="movie.form"/></label>
                            <div class="col-sm-10">
                                <form:select path="movie" multiple="false" size="10" class="form-control">
                                    <form:options items="${movieList}" itemValue="id" itemLabel="title"/>
                                </form:select>
                                <form:errors path="movie" class="form-control-label"/>
                            </div>
                        </div>
                    </spring:bind>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <c:choose>
                            <c:when test="${sessionForm['new']}">
                                <button type="submit" class="btn btn-primary pull-right"><spring:message
                                        code="action.add"/></button>
                            </c:when>
                            <c:otherwise>
                                <button type="submit" class="btn btn-primary pull-right"><spring:message
                                        code="action.update"/></button>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
    <jsp:include page="../../parts/footer.jsp"/>
</div>
</body>
</html>
