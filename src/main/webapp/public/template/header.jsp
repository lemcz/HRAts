<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="<c:url value="/"/>">
                <spring:message code='header.message'/>
            </a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="#"><spring:message code="header.welcome"/>&nbsp;${user.name}</a>
                </li>
                <li>
                    <a href="<c:url value='/settings'/>" title='<spring:message code="header.settings"/>'>
                        <spring:message code="header.settings"/>
                    </a>
                </li>
                <li>
                    <a href="<c:url value='/logout'/>" title='<spring:message code="header.logout"/>'>
                        <spring:message code="header.logout"/>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>
