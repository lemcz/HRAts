<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
    <div ng-controller="ActivityController" ng-cloak>
        <h1 class="page-header">
            <p class="text-center">
                <spring:message code='activities.header'/>
            </p>
        </h1>
        <table st-table="displayedCollection" st-safe-src="activitiesCollection" class="table table-bordered table-striped">
            <thead>
            <label>Items on page
                <select ng-init="itemsByPage = paginationOptions[0] || 10" ng-model="itemsByPage" ng-options="value as value for value in paginationOptions"></select>
            </label>
            <span>
                Total items: {{activitiesCollection.length}}
            </span>
            <tr>
                <th colspan="6">
                    <input st-search="" class="form-control" placeholder="Search" type="text"/>
                </th>
            </tr>
            <tr>
                <th st-sort="name" st-sort-default="true">Name</th>
                <th st-sort="description">Note</th>
                <th st-sort="dateEntered">Date Entered</th>
            </tr>
            </thead>
            <tbody>
                <tr st-select-row="activity" st-select-mode="multiple" ng-repeat="activity in displayedCollection track by $index">
                    <td>{{activity.name}}</td>
                    <td>{{activity.note}}</td>
                    <td>{{activity.dateEntered | date:'medium'}}</td>
            </tbody>
            <tfoot>
                <td colspan="6" class="text-center">
                    <div st-pagination="" st-items-by-page="itemsByPage" st-displayed-pages="5"></div>
                </td>
            </tfoot>
        </table>
    </div>
</div>

<script src="<c:url value="/resources/js/pages/activities.js"/>"></script>