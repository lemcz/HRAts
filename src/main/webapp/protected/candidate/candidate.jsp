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
        <table st-table="displayedCollection" st-safe-src="candidatesCollection" class="table table-bordered table-striped">
            <thead>
            <label>Items on page
                <select ng-init="itemsByPage = paginationOptions[0] || 10" ng-model="itemsByPage" ng-options="value as value for value in paginationOptions"></select>
            </label>
            <span>
                Total items: {{candidatesCollection.length}}
            </span>
            <tr>
                <th colspan="6">
                    <input st-search="" class="form-control" placeholder="Search" type="text"/>
                </th>
            </tr>
            <tr>
                <th st-sort="name" st-sort-default="true">Name</th>
                <th st-sort="description">Description</th>
                <th st-sort="dateEntered">Date Entered</th>
                <th st-sort="dateModified">Date Modified</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <tr st-select-row="candidate" st-select-mode="multiple" ng-repeat="candidate in displayedCollection track by $index">
                <td>{{candidate.name}}</td>
                <td>{{candidate.description}}</td>
                <td>{{candidate.dateEntered | date:'medium'}}</td>
                <td>{{candidate.dateModified | date:'medium'}}</td>
                <td>
                    <div class="text-center">
                        <input type="hidden" value="{{candidate.id}}"/>
                        <a href=""
                           role="button"
                           title="<spring:message code="update"/>&nbsp;<spring:message code="candidate"/>"
                           ng-click="openModal('updateCandidateModal', candidate)"
                           class="btn btn-default" data-toggle="modal">
                            <span class="glyphicon glyphicon-pencil"></span>
                        </a>
                        <a href=""
                           role="button"
                           title="Add to vacancy"
                           ng-click="openModal('addToVacancyModal', candidate)"
                           class="btn btn-default" data-toggle="modal">
                            <span class="glyphicon glyphicon-link"></span>
                        </a>
                        <a href=""
                           role="button"
                           title="Log activity"
                           ng-click="openModal('logActivityModal', candidate)"
                           class="btn btn-default" data-toggle="modal">
                            <span class="glyphicon glyphicon-tasks"></span>
                        </a>
                        <a href=""
                           ng-click="openModal('deleteCandidateModal', candidate)"
                           role="button"
                           title="<spring:message code="delete"/>&nbsp;<spring:message code="candidate"/>"
                           class="btn btn-default"
                           data-toggle="modal">
                            <span class="glyphicon glyphicon-minus"></span>
                        </a>
                    </div>
                </td>
            </tbody>
            <tfoot>
            <td colspan="6" class="text-center">
                <div st-pagination="" st-items-by-page="itemsByPage" st-displayed-pages="5"></div>
            </td>
            </tfoot>
        </table>
        <div>
            <a href=""
               role="button"
               title="<spring:message code='create'/>&nbsp;<spring:message code='candidate'/>"
               class="btn btn-success"
               data-toggle="modal"
               ng-click="openModal('addCandidateModal')">
                <span class="glyphicon glyphicon-plus"></span>&nbsp;&nbsp;<spring:message code="create"/>&nbsp;<spring:message code="candidate"/>
            </a>
        </div>
        <jsp:include page="dialogs/candidateDialogs.jsp"/>
    </div>
</div>

<script src="<c:url value="/resources/js/services/activityService.js"/>"></script>
<script src="<c:url value="/resources/js/services/lookupService.js"/>"></script>
<script src="<c:url value="/resources/js/services/vacancyService.js"/>"></script>
<script src="<c:url value="/resources/js/pages/candidate.js"/>"></script>