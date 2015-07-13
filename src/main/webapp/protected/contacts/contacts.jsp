<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="row">
    <div ng-controller="ContactController" ng-cloak>
        <h1 class="page-header">
            <p class="text-center">
                <spring:message code='contacts.header'/>
            </p>
        </h1>
        <table st-table="displayedCollection" st-safe-src="contactsCollection" class="table table-bordered table-striped">
            <thead>
            <label>Items on page
                <select ng-init="itemsByPage = paginationOptions[0] || 10" ng-model="itemsByPage" ng-options="value as value for value in paginationOptions"></select>
            </label>
            <span>
                Total items: {{contactsCollection.length}}
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
                <tr st-select-row="contact" st-select-mode="multiple" ng-repeat="contact in displayedCollection track by $index">
                    <td>{{contact.name}}</td>
                    <td>{{contact.description}}</td>
                    <td>{{contact.dateEntered | date:'medium'}}</td>
                    <td>{{contact.dateModified | date:'medium'}}</td>
                    <td>
                        <div class="text-center">
                            <input type="hidden" value="{{contact.id}}"/>
                            <a href=""
                               role="button"
                               title="<spring:message code="update"/>&nbsp;<spring:message code="contact"/>"
                               ng-click="openModal('updateContactModal', contact)"
                               class="btn btn-default" data-toggle="modal">
                                <span class="glyphicon glyphicon-pencil"></span>
                            </a>
                            <a href=""
                               ng-click="openModal('deleteContactModal', contact)"
                               role="button"
                               title="<spring:message code="delete"/>&nbsp;<spring:message code="contact"/>"
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
               title="<spring:message code='create'/>&nbsp;<spring:message code='contact'/>"
               class="btn btn-success"
               data-toggle="modal"
               ng-click="openModal('addContactModal')">
                <span class="glyphicon glyphicon-plus"></span>&nbsp;&nbsp;<spring:message code="create"/>&nbsp;<spring:message code="contact"/>
            </a>
        </div>
        <jsp:include page="dialogs/contactsDialogs.jsp"/>
    </div>
</div>

<script src="<c:url value="/resources/js/pages/companies.js"/>"></script>
<script src="<c:url value="/resources/js/pages/contacts.js"/>"></script>