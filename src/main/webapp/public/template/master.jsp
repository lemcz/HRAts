<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en-EN">
    <head>
        <tiles:insertAttribute name="scriptBundle"/>
    </head>
    <body ng-app="HRAts">
    <script src="<c:url value='/resources/js/pages/master.js' />"></script>
    <tiles:insertAttribute name="header" />
    <input type="text" class="hidden" id="userId" value="${user.id}"/>
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-3 col-md-2 sidebar" ng-controller="LocationController">
                <ul class="nav nav-sidebar">
                    <li ng-class="{'active': activeURL == 'home', '': activeURL != 'home'}">
                        <a href="<c:url value="/"/>"
                           title='<spring:message code="header.home"/>'>
                            <spring:message code="header.home"/>
                        </a>
                    </li>
                </ul>
                <ul class="nav nav-sidebar">
                    <li ng-class="{'active': activeURL == 'activities', '': activeURL != 'activities'}">
                        <a href="<c:url value='/protected/activities'/>"
                           title='Activities'>
                            Activities
                        </a>
                    </li>
                    <li ng-class="{'active': activeURL == 'vacancies', '': activeURL != 'vacancies'}">
                        <a href="<c:url value='/protected/vacancies'/>"
                           title='Vacancies'>
                            Vacancies
                        </a>
                    </li>
                    <li ng-class="{'active': activeURL == 'candidates', '': activeURL != 'candidates'}">
                        <a href="<c:url value='/protected/candidates'/>"
                           title='<spring:message code="header.candidates"/>'>
                            <spring:message code="header.candidates"/>
                        </a>
                    </li>
                </ul>
                <ul class="nav nav-sidebar">
                    <li ng-class="{'active': activeURL == 'companies', '': activeURL != 'companies'}">
                        <a href="<c:url value='/protected/companies'/>"
                           title='Companies'>
                            Companies
                        </a>
                    </li>
                    <li ng-class="{'active': activeURL == 'contacts', '': activeURL != 'contacts'}">
                        <a href="<c:url value='/protected/contacts'/>"
                           title='Contacts'>
                            Contacts
                        </a>
                    </li>
                </ul>
                <ul class="nav nav-sidebar">
                    <li ng-class="{'active': activeURL == 'reports', '': activeURL != 'reports'}">
                        <a href="<c:url value='/protected/reports'/>"
                           title='Reports'>
                            Reports
                        </a>
                    </li>
                </ul>
            </div>
            <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
                <tiles:insertAttribute name="body" />
            </div>
        </div>
    </div>
    <tiles:insertAttribute name="footer" />
    </body>
</html>