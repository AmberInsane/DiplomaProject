<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<div class="container">
	<hr>
	<footer>
		<security:authorize access="isAuthenticated()">
			<spring:url value="${header.referer}" var="backUrl"/>
			<button class="btn left" onclick="location.href='${backUrl}'">Назад</button>

			<spring:url value="/logout" var="logoutUrl"/>
			<button class="btn right" onclick="location.href='${logoutUrl}'"><spring:message code="home.log_out"/></button>
		</security:authorize>

		<p>&copy; AmberInsane 2020</p>
	</footer>
</div>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

<spring:url value="/resources/core/js/hello.js" var="coreJs" />
<spring:url value="/resources/core/js/bootstrap.min.js"
	var="bootstrapJs" />

<script src="${coreJs}"></script>
<script src="${bootstrapJs}"></script>


