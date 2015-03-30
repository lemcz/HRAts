<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>



<nav class="navbar navar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">
                <spring:message code='header.message'/>
            </a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="<c:url value='/logout'/>" title='<spring:message code="header.logout"/>'><p class="displayInLine"><spring:message code="header.logout"/>&nbsp;(${user.name})</p></a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right" ng-controller="LocationController">
                <li ng-class="{'active': activeURL == 'home', '': activeURL != 'home'}" >
                    <a href="<c:url value="/"/>"
                       title='<spring:message code="header.home"/>'
                            >
                        <p><spring:message code="header.home"/></p>
                    </a>
                </li>
                <li ng-class="{'gray': activeURL == 'contacts', '': activeURL != 'contacts'}"><a title='<spring:message code="header.contacts"/>' href="<c:url value='/protected/contacts'/>"><p><spring:message code="header.contacts"/></p></a></li>
            </ul>
        </div>
    </div>
</nav>
