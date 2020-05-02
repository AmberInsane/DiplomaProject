<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<head>
    <title>BuddiesMovie</title>
    <spring:url value="/logout" var="logoutUrl"/>
    <spring:url value="/resources/core/css/hello.css" var="coreCss"/>
    <spring:url value="/resources/core/css/bootstrap.min.css"
                var="bootstrapCss"/>
    <spring:url value="/resources/core/css/header.css" var="headerCss"/>

    <link href="${bootstrapCss}" rel="stylesheet"/>
    <link href="${coreCss}" rel="stylesheet"/>
    <link href="${headerCss}" rel="stylesheet"/>
</head>

<spring:url value="/" var="urlHome"/>
<spring:url value="/users/add" var="urlAddUser"/>


<nav class="navbar navbar-light">
    <a class="navbar-brand brand font-weight-bold" href="${urlHome}">BuddiesMovie</a>

    <div>
        <security:authorize access="isAuthenticated()">
            <button class="btn btn-secondary right" onclick="location.href='${logoutUrl}'"><spring:message
                    code="home.log.out"/></button>
        </security:authorize>

        <ul class="languages">
            <jsp:include page="language_bar.jsp"/>
        </ul>
    </div>
</nav>