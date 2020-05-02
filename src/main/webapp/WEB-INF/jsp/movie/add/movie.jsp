<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 11.04.2020
  Time: 20:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Title</title>
    <spring:url value="/admin/movie/add" var="movieActionUrl"/>
    <spring:url value="/movies" var="returnUrl"/>
</head>
<body>
<jsp:include page="../../parts/header.jsp"/>
<div class="container">
    <button class="btn btn-primary" onclick="location.href='${returnUrl}'"><spring:message code="action.back"/></button>
    <c:choose>
        <c:when test="${movieForm['new']}">
            <h1><spring:message code="action.add"/> <spring:message code="movie.form"/></h1>
        </c:when>
        <c:otherwise>
            <h1><spring:message code="action.update"/> <spring:message code="movie.form"/></h1>
        </c:otherwise>
    </c:choose>
    <br/>
    <form:form class="form-horizontal" method="POST" modelAttribute="movieForm" action="${movieActionUrl}">
        <form:hidden path="id"/>
        <div>
            <spring:bind path="title">
                <div class="form-group ${status.error ? 'has-danger' : ''}">
                    <label class="col-sm-2 form-control-label"><spring:message code="movie.title"/></label>
                    <div class="col-sm-10">
                        <form:input path="title" type="text" class="form-control " id="title" placeholder="Title"/>
                        <form:errors path="title" class="form-control-label"/>
                    </div>
                </div>
            </spring:bind>

            <spring:bind path="description">
                <div class="form-group ${status.error ? 'has-danger' : ''}">
                    <label class="col-sm-2 form-control-label"><spring:message code="movie.description"/></label>
                    <div class="col-sm-10">
                        <form:textarea path="description" rows="5" class="form-control" id="description"
                                       placeholder="Description"/>
                        <form:errors path="description" class="form-control-label"/>
                    </div>
                </div>
            </spring:bind>

            <spring:bind path="year">
                <div class="form-group ${status.error ? 'has-danger' : ''}">
                    <label class="col-sm-2 form-control-label"><spring:message code="movie.year"/></label>
                    <div class="col-sm-10">
                        <form:input path="year" cols="4" class="form-control" id="year" placeholder="Year"/>
                        <form:errors path="year" class="form-control-label"/>
                    </div>
                </div>
            </spring:bind>

            <spring:bind path="genre">
                <div class="form-group ${status.error ? 'has-danger' : ''}">
                    <label class="col-sm-2 form-control-label"><spring:message code="genre.form"/></label>
                    <div class="col-sm-5">
                        <form:select path="genre" multiple="true" size="5" class="form-control">
                            <form:options items="${genreList}" itemValue="id" itemLabel="name"/>
                        </form:select>
                        <form:errors path="genre" class="form-control-label"/>
                    </div>
                    <div class="col-sm-5"></div>
                </div>
            </spring:bind>

            <spring:bind path="timeLength">
                <div class="form-group ${status.error ? 'has-danger' : ''}">
                    <label class="col-sm-2 form-control-label"><spring:message code="movie.time"/> (<spring:message code="movie.time.minutes"/>)</label>
                    <div class="col-sm-10">
                        <form:input path="timeLength" cols="4" class="form-control" id="timeLength"
                                    placeholder="TimeLength"/>
                        <form:errors path="timeLength" class="form-control-label"/>
                    </div>
                </div>
            </spring:bind>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${movieForm['new']}">
                        <button type="submit" class="btn btn-primary pull-right"><spring:message code="action.add"/></button>
                    </c:when>
                    <c:otherwise>
                        <button type="submit" class="btn btn-primary pull-right"><spring:message code="action.update"/></button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</div>
<jsp:include page="../../parts/footer.jsp"/>
</body>
</html>
