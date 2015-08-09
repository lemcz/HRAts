<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
    <div ng-controller="CandidateController" ng-cloak>
        <h1 class="page-header">
            <p class="text-center">
                <spring:message code='candidates.header'/>
            </p>
        </h1>
        <div ng-controller="UiGridController" ng-cloak>
            <a href=""
               ng-click="openModal('addCandidateModal')"
               role="button"
               title="<spring:message code='create'/>&nbsp;<spring:message code='candidate'/>"
               class="btn btn-success"
               data-toggle="modal">
                <span class="glyphicon glyphicon-plus"></span>&nbsp;&nbsp;<spring:message code="create"/>&nbsp;<spring:message code="candidate"/>
            </a>
            <a href=""
               ng-click="openModal('updateCandidateModal', row)"
               ng-disabled="gridOptions.multipleSelected"
               role="button"
               title="<spring:message code="edit"/>&nbsp;<spring:message code="candidate"/>"
               class="btn btn-default" data-toggle="modal">
                <span class="glyphicon glyphicon-pencil"></span>&nbsp;&nbsp;<spring:message code="edit"/>&nbsp;<spring:message code="candidate"/>
            </a>
            <a href=""
               ng-click="openModal('deleteCandidateModal', row)"
               ng-disabled="gridOptions.multipleSelected"
               role="button"
               title="<spring:message code="delete"/>&nbsp;<spring:message code="candidate"/>"
               class="btn btn-default"
               data-toggle="modal">
                <span class="glyphicon glyphicon-minus"></span>&nbsp;&nbsp;<spring:message code="delete"/>&nbsp;<spring:message code="candidate"/>
            </a>
            <a href=""
               ng-click="openModal('addToVacancyModal', row)"
               ng-disabled="gridOptions.multipleSelected"
               role="button"
               title="Add To Vacancy"
               class="btn btn-default"
               data-toggle="modal">
                <span class="glyphicon glyphicon-minus"></span>&nbsp;&nbsp;Add&nbsp;To&nbsp;Vacancy
            </a>
            <a href=""
               ng-click="openModal('logActivityModal', row)"
               ng-disabled="gridOptions.multipleSelected"
               role="button"
               title="Log Activity"
               class="btn btn-default"
               data-toggle="modal">
                <span class="glyphicon glyphicon-minus"></span>&nbsp;&nbsp;Log&nbsp;Activity
            </a>
            <br><br>
            <div class="table" id="grid1" ui-grid="gridOptions" ui-grid-cellNav ui-grid-auto-resize ui-grid-resize-columns ui-grid-pinning ui-grid-selection ui-grid-move-columns ui-grid-exporter ui-grid-grouping class="grid"></div>
        </div>
        <jsp:include page="dialogs/candidateDialogs.jsp"/>
    </div>
</div>

<script src="<c:url value="/resources/js/pages/gridConf.js"/>"></script>
<script src="<c:url value="/resources/js/services/activityService.js"/>"></script>
<script src="<c:url value="/resources/js/services/companyService.js"/>"></script>
<script src="<c:url value="/resources/js/services/departmentService.js"/>"></script>
<script src="<c:url value="/resources/js/services/lookupService.js"/>"></script>
<script src="<c:url value="/resources/js/services/vacancyService.js"/>"></script>
<script src="<c:url value="/resources/js/pages/candidate.js"/>"></script>