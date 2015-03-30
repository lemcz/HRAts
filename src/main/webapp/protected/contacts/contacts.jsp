<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row" ng-controller="contactsController">
    <h2>
        <p class="text-center">
            <spring:message code='contacts.header'/>
            <button href="#searchContactsModal"
               type="button"
               id="contactsHeaderButton"
               role="button"
               ng-class="{'': displaySearchButton == true, 'none': displaySearchButton == false}"
               title="<spring:message code="search"/>&nbsp;<spring:message code="contact"/>"
               class="btn btn-default" data-toggle="modal">
                <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
            </button>
        </p>
    </h2>
    <h4>
        <div ng-class="{'': state == 'list', 'none': state != 'list'}">
            <p class="text-center">
                <spring:message code="message.total.records.found"/>:&nbsp;{{page.totalContacts}}
            </p>
        </div>
    </h4>

    <div>
        <div id="loadingModal" class="modal fade in centering"
             aria-labelledby="deleteContactsModalLabel" aria-hidden="true">
            <div id="divLoadingIcon" class="text-center">
                <div class="glyphicon-align-center loading"></div>
            </div>
        </div>

        <div ng-class="{'alert badge-inverse': displaySearchMessage == true, 'none': displaySearchMessage == false}">
            <h4>
                <p class="messageToUser"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;{{page.searchMessage}}</p>
            </h4>
            <a href="#"
               role="button"
               ng-click="resetSearch();"
               ng-class="{'': displaySearchMessage == true, 'none': displaySearchMessage == false}"
               title="<spring:message code='search.reset'/>"
               class="btn btn-default" data-toggle="modal">
                <span class="glyphicon glyphicon-remove"></span> <spring:message code="search.reset"/>
            </a>
        </div>

        <div ng-class="{'alert badge-inverse': displayMessageToUser == true, 'none': displayMessageToUser == false}">
            <h4 class="displayInLine">
                <p class="messageToUser displayInLine"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;{{page.actionMessage}}</p>
            </h4>
        </div>

        <div ng-class="{'alert alert-block alert-error': state == 'error', 'none': state != 'error'}">
            <h4><span class="glyphicon glyphicon-info-sign"></span> <spring:message code="error.generic.header"/></h4><br/>

            <p><spring:message code="error.generic.text"/></p>
        </div>

        <div ng-class="{'alert alert-info': state == 'noresult', 'none': state != 'noresult'}">
            <h4><span class="glyphicon glyphicon-info-sign"></span> <spring:message code="contacts.emptyData"/></h4><br/>

            <p><spring:message code="contacts.emptyData.text"/></p>
        </div>

        <div id="gridContainer" ng-class="{'': state == 'list', 'none': state != 'list'}">
            <table class="table table-bordered table-striped">
                <thead>
                <tr>
                    <th scope="col"><spring:message code="contacts.name"/></th>
                    <th scope="col"><spring:message code="contacts.email"/></th>
                    <th scope="col"><spring:message code="contacts.phone"/></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="contact in page.source">
                    <td class="tdContactsCentered">{{contact.name}}</td>
                    <td class="tdContactsCentered">{{contact.email}}</td>
                    <td class="tdContactsCentered">{{contact.phoneNumber}}</td>
                    <td class="width15">
                        <div class="text-center">
                            <input type="hidden" value="{{contact.id}}"/>
                            <a href="#updateContactsModal"
                               ng-click="selectedContact(contact);"
                               role="button"
                               title="<spring:message code="update"/>&nbsp;<spring:message code="contact"/>"
                               class="btn btn-default" data-toggle="modal">
                                <span class="glyphicon glyphicon-pencil"></span>
                            </a>
                            <a href="#deleteContactsModal"
                               ng-click="selectedContact(contact);"
                               role="button"
                               title="<spring:message code="delete"/>&nbsp;<spring:message code="contact"/>"
                               class="btn btn-default" data-toggle="modal">
                                <span class="glyphicon glyphicon-minus"></span>
                            </a>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>

            <div class="text-center">
                <button href="#" class="btn btn-default"
                        ng-class="{'btn-default': page.currentPage != 0, 'disabled': page.currentPage == 0}"
                        ng-disabled="page.currentPage == 0" ng-click="changePage(0)"
                        title='<spring:message code="pagination.first"/>'
                        >
                    <spring:message code="pagination.first"/>
                </button>
                <button href="#"
                        class="btn btn-default"
                        ng-class="{'btn-default': page.currentPage != 0, 'disabled': page.currentPage == 0}"
                        ng-disabled="page.currentPage == 0" class="btn btn-default"
                        ng-click="changePage(page.currentPage - 1)"
                        title='<spring:message code="pagination.back"/>'
                        >&lt;</button>
                <span>{{page.currentPage + 1}} <spring:message code="pagination.of"/> {{page.pagesCount}}</span>
                <button href="#"
                        class="btn btn-default"
                        ng-class="{'btn-default': page.pagesCount - 1 != page.currentPage, 'disabled': page.pagesCount - 1 == page.currentPage}"
                        ng-click="changePage(page.currentPage + 1)"
                        ng-disabled="page.pagesCount - 1 == page.currentPage"
                        title='<spring:message code="pagination.next"/>'
                        >&gt;</button>
                <button href="#"
                        class="btn btn-default"
                        ng-class="{'btn-default': page.pagesCount - 1 != page.currentPage, 'disabled': page.pagesCount - 1 == page.currentPage}"
                        ng-disabled="page.pagesCount - 1 == page.currentPage"
                        ng-click="changePage(page.pagesCount - 1)"
                        title='<spring:message code="pagination.last"/>'
                        >
                    <spring:message code="pagination.last"/>
                </button>
            </div>
        </div>
        <div ng-class="{'text-center': displayCreateContactButton == true, 'none': displayCreateContactButton == false}">
            <br/>
            <a href="#addContactsModal"
               role="button"
               ng-click="resetContact();"
               title="<spring:message code='create'/>&nbsp;<spring:message code='contact'/>"
               class="btn btn-default"
               data-toggle="modal">
                <span class="glyphicon glyphicon-plus"></span>
                &nbsp;&nbsp;<spring:message code="create"/>&nbsp;<spring:message code="contact"/>
            </a>
        </div>

        <jsp:include page="dialogs/contactsDialogs.jsp"/>

    </div>
</div>

<script src="<c:url value="/resources/js/pages/contacts.js" />"></script>