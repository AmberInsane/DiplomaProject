<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:url value="${header.referer}" var="backUrl"/>
<spring:url value="/" var="homeUrl"/>
<spring:url value="/logout" var="logoutUrl"/>
<div class="container">
    <hr>
    <footer>
        <security:authorize access="isAuthenticated()">
            <button class="btn left" onclick="location.href='${backUrl}'"><spring:message code="action.back"/></button>
            <button class="btn left" onclick="location.href='${homeUrl}'"><spring:message code="home.main.page"/></button>
            <button class="btn right" onclick="location.href='${logoutUrl}'"><spring:message code="home.log.out"/></button>
        </security:authorize>

        <p>&copy; AmberInsane 2020</p>
    </footer>
</div>

<%--<script
        src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>--%>

<spring:url value="/resources/core/js/hello.js" var="coreJs"/>
<spring:url value="/resources/core/js/bootstrap.min.js"
            var="bootstrapJs"/>

<script src="${coreJs}"></script>
<script src="${bootstrapJs}"></script>


