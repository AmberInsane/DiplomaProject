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
    <title>Hall</title>
    <spring:url value="/admin/hall/add" var="hallActionUrl"/>
    <spring:url value="/admin/hall" var="returnUrl"/>
</head>
<body>
<jsp:include page="../../parts/header.jsp"/>
<div class="container">
    <security:authorize access="isAuthenticated()">
        <security:authorize access="hasRole('ADMIN')">
            <div class="container">
                <button class="btn btn-primary" onclick="location.href='${returnUrl}'">Return to halls</button>
                <form:form method="POST" modelAttribute="hallForm" action="${hallActionUrl}">
                    <c:choose>
                        <c:when test="${hallForm['new']}">
                            <h1>Add Hall</h1>
                        </c:when>
                        <c:otherwise>
                            <h1>Update Hall</h1>
                        </c:otherwise>
                    </c:choose>
                    <br/>
                    <form:hidden path="id"/>
                    <div>
                        <spring:bind path="name">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <label class="col-sm-2 control-label">Name</label>
                                <div class="col-sm-10">
                                    <form:input path="name" type="text" class="form-control " id="name"
                                                placeholder="Name"/>
                                    <form:errors path="name" class="control-label"/>
                                </div>
                            </div>
                        </spring:bind>

                        <spring:bind path="capacity">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <label class="col-sm-2 control-label">Capacity</label>
                                <div class="col-sm-10">
                                    <form:input type="number" path="capacity" step="1" placeholder="Capacity"
                                                class="form-control " id="capacity" autofocus=""/>
                                    <form:errors path="capacity" class="control-label"/>
                                </div>
                            </div>
                        </spring:bind>

                        <spring:bind path="description">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <label class="col-sm-2 control-label">Description</label>
                                <div class="col-sm-10">
                                    <form:textarea path="description" rows="5" class="form-control" id="description"
                                                   placeholder="Description"/>
                                    <form:errors path="description" class="control-label"/>
                                </div>
                            </div>
                        </spring:bind>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <c:choose>
                                <c:when test="${hallForm['new']}">
                                    <button type="submit" class="btn-lg btn-primary pull-right">Add</button>
                                </c:when>
                                <c:otherwise>
                                    <button type="submit" class="btn-lg btn-primary pull-right">Update</button>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </form:form>
            </div>
        </security:authorize>
    </security:authorize>
</div>
<jsp:include page="../../parts/footer.jsp"/>
</body>
</html>
