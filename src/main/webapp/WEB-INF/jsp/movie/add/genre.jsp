<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 19.04.2020
  Time: 15:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Genre</title>
    <spring:url value="/admin/genre/add" var="genreActionUrl"/>
    <spring:url value="/resources/core/css/index.css" var="indexCss"/>

    <link href="${indexCss}" rel="stylesheet"/>
</head>
<body>
<div class="wrapper">
    <div class="wrapper-inner">
        <jsp:include page="../../parts/header.jsp"/>
        <div class="container mt-5">
            <security:authorize access="isAuthenticated()">
                <security:authorize access="hasRole('ADMIN')">
                    <div class="container">
                        <form:form method="POST" modelAttribute="genreForm" action="${genreActionUrl}">
                            <c:choose>
                                <c:when test="${genreForm['new']}">
                                    <h2><spring:message code="action.add"/> <spring:message code="genre.form"/></h2>
                                </c:when>
                                <c:otherwise>
                                    <h2><spring:message code="action.update"/> <spring:message code="genre.form"/></h2>
                                </c:otherwise>
                            </c:choose>
                            <br/>
                            <form:hidden path="id"/>
                            <div>
                                <spring:bind path="name">
                                    <div class="form-group ${status.error ? 'has-danger' : ''}">
                                        <label class="col-sm-2 form-control-label"><spring:message
                                                code="text.name"/></label>
                                        <div class="col-sm-10">
                                            <form:input path="name" type="text" class="form-control " id="name"/>
                                            <form:errors path="name" class="form-control-label"/>
                                        </div>
                                    </div>
                                </spring:bind>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <c:choose>
                                        <c:when test="${genreForm['new']}">
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
                </security:authorize>
            </security:authorize>
        </div>
    </div>
    <jsp:include page="../../parts/footer.jsp"/>
</div>
</body>
</html>
