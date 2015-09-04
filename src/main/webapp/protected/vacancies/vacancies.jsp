<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
    <div ng-controller="VacancyController" ng-cloak>
        <h1 class="page-header">
            <p class="text-center">
                <spring:message code='vacancies.header'/>
            </p>
        </h1>
        <div ng-controller="UiGridController" ng-cloak>
            <a href=""
               ng-click="openModal('addVacancyModal')"
               role="button"
               title="<spring:message code='create'/>&nbsp;<spring:message code='vacancy'/>"
               class="btn btn-success"
               data-toggle="modal">
                <span class="glyphicon glyphicon-plus"></span>&nbsp;&nbsp;<spring:message code="create"/>&nbsp;<spring:message code="vacancy"/>
            </a>
            <a href="{{row.id}}"
               role="button"
               ng-disabled="gridOptions.multipleSelected"
               title="<spring:message code='view'/>&nbsp;<spring:message code='details'/>"
               class="btn btn-info">
                <span class="glyphicon glyphicon-file"></span>&nbsp;&nbsp;<spring:message code="view"/>&nbsp;<spring:message code="details"/>
            </a>
            <a href=""
               ng-click="openModal('updateVacancyModal', row)"
               ng-disabled="gridOptions.multipleSelected"
               role="button"
               title="<spring:message code="edit"/>&nbsp;<spring:message code="vacancy"/>"
               class="btn btn-default" data-toggle="modal">
                <span class="glyphicon glyphicon-pencil"></span>&nbsp;&nbsp;<spring:message code="edit"/>&nbsp;<spring:message code="vacancy"/>
            </a>
            <a href=""
               ng-click="openModal('deleteVacancyModal', row)"
               ng-disabled="gridOptions.multipleSelected"
               role="button"
               title="<spring:message code="delete"/>&nbsp;<spring:message code="vacancy"/>"
               class="btn btn-default"
               data-toggle="modal">
                <span class="glyphicon glyphicon-minus"></span>&nbsp;&nbsp;<spring:message code="delete"/>&nbsp;<spring:message code="vacancy"/>
            </a>
            <br><br>
            <div class="table" id="grid1" ui-grid="gridOptions" ui-grid-auto-resize ui-grid-resize-columns ui-grid-pinning ui-grid-selection ui-grid-move-columns ui-grid-exporter ui-grid-grouping class="grid"></div>
        </div>
        <jsp:include page="dialogs/vacanciesDialogs.jsp"/>
    </div>
</div>

<script src="<c:url value="/resources/js/pages/gridConf.js"/>"></script>
<script src="<c:url value="/resources/js/services/companyService.js"/>"></script>
<script src="<c:url value="/resources/js/services/vacancyService.js"/>"></script>
<script src="<c:url value="/resources/js/services/departmentService.js"/>"></script>
<script src="<c:url value="/resources/js/pages/vacancies.js"/>"></script>