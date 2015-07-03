<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
    <div ng-controller="CompanyController" ng-cloak>
        <h1 class="page-header">
            <p class="text-center">
                <spring:message code='companies.header'/>
            </p>
        </h1>
        <table st-table="displayedCollection" st-safe-src="companiesCollection" class="table table-bordered table-striped">
            <thead>
            <label>Items on page
                <select ng-init="itemsByPage = paginationOptions[0] || 10" ng-model="itemsByPage" ng-options="value as value for value in paginationOptions"></select>
            </label>
            <span>
                Total items: {{companiesCollection.length}}
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
                <tr st-select-row="company" st-select-mode="multiple" ng-repeat="company in displayedCollection track by $index">
                    <td>{{company.name}}</td>
                    <td>{{company.description}}</td>
                    <td>{{company.dateEntered | date:'medium'}}</td>
                    <td>{{company.dateModified | date:'medium'}}</td>
                    <td>
                        <div class="text-center">
                            <input type="hidden" value="{{company.id}}"/>
                            <a href=""
                               role="button"
                               title="<spring:message code="update"/>&nbsp;<spring:message code="company"/>"
                               ng-click="openModal('updateCompanyModal', company)"
                               class="btn btn-default" data-toggle="modal">
                                <span class="glyphicon glyphicon-pencil"></span>
                            </a>
                            <a href=""
                               ng-click="openModal('deleteCompanyModal', company)"
                               role="button"
                               title="<spring:message code="delete"/>&nbsp;<spring:message code="company"/>"
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
               title="<spring:message code='create'/>&nbsp;<spring:message code='company'/>"
               class="btn btn-success"
               data-toggle="modal"
               ng-click="openModal('addCompanyModal')">
                <span class="glyphicon glyphicon-plus"></span>&nbsp;&nbsp;<spring:message code="create"/>&nbsp;<spring:message code="company"/>
            </a>
        </div>
        <jsp:include page="dialogs/companiesDialogs.jsp"/>
    </div>
</div>

<script src="<c:url value="/resources/js/pages/companies.js"/>"></script>