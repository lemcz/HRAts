<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row" ng-controller="CandidatesController" ng-cloak>
    <h1 class="page-header">
        <p class="text-center">
            <spring:message code='candidates.header'/>
            <button class="btn btn-default"
                    href="#searchCandidatesModal"
                    type="button"
                    id="candidatesHeaderButton"
                    role="button"
                    ng-show="displaySearchButton"
                    title="<spring:message code="search"/>&nbsp;<spring:message code="candidate"/>"
                    data-toggle="modal">
                <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
            </button>
        </p>
    </h1>
    <h4>
        <div ng-class="{'': state == 'list', 'none': state != 'list'}">
            <p class="text-center">
                <spring:message code="message.total.records.found"/>:&nbsp;{{page.totalCandidates}}
            </p>
        </div>
    </h4>

    <div>
        <div ng-class="{'alert badge-inverse': displaySearchMessage == true, 'none': displaySearchMessage == false}">
            <h4>
                <p class="messageToUser"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;{{page.searchMessage}}</p>
            </h4>
            <a href="#"
               role="button"
               ng-click="resetSearch();"
               ng-show="displaySearchMessage"
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
            <h4><span class="glyphicon glyphicon-info-sign"></span> <spring:message code="candidates.emptyData"/></h4><br/>

            <p><spring:message code="candidates.emptyData.text"/></p>
        </div>

        <div id="gridContainer" ng-class="{'': state == 'list', 'none': state != 'list'}">
            <table class="table table-bordered table-striped">
                <thead>
                <tr>
                    <th scope="col"><spring:message code="candidates.name"/></th>
                    <th scope="col"><spring:message code="candidates.email"/></th>
                    <th scope="col"><spring:message code="candidates.phone"/></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="candidate in page.source track by $index">
                    <td class="tdCandidatesCentered">{{candidate.name}}</td>
                    <td class="tdCandidatesCentered">{{candidate.email}}</td>
                    <td class="tdCandidatesCentered">{{candidate.phoneNumber}}</td>
                    <td class="width15">
                        <div class="text-center">
                            <input type="hidden" value="{{candidate.id}}"/>
                            <a href="#updateCandidatesModal"
                               ng-click="selectedCandidate(candidate);"
                               role="button"
                               title="<spring:message code="update"/>&nbsp;<spring:message code="candidate"/>"
                               class="btn btn-default" data-toggle="modal">
                                <span class="glyphicon glyphicon-pencil"></span>
                            </a>
                            <a href="#deleteCandidatesModal"
                               ng-click="selectedCandidate(candidate);"
                               role="button"
                               title="<spring:message code="delete"/>&nbsp;<spring:message code="candidate"/>"
                               class="btn btn-default"
                               data-toggle="modal">
                                <span class="glyphicon glyphicon-minus"></span>
                            </a>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>

            <div class="text-center">
                <button href="#"
                        class="btn btn-default"
                        ng-class="{'btn-default': page.currentPage != 0, 'disabled': page.currentPage == 0}"
                        ng-disabled="page.currentPage == 0" ng-click="changePage(0)"
                        title='<spring:message code="pagination.first"/>'
                        >
                    <spring:message code="pagination.first"/>
                </button>
                <button href="#"
                        class="btn btn-default"
                        ng-class="{'btn-default': page.currentPage != 0, 'disabled': page.currentPage == 0}"
                        ng-disabled="page.currentPage == 0"
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
        <div ng-class="{'text-center': displayCreateCandidateButton == true, 'none': displayCreateCandidateButton == false}">
            <br/>
            <a href="#addCandidatesModal"
               role="button"
               ng-click="resetCandidate();"
               title="<spring:message code='create'/>&nbsp;<spring:message code='candidate'/>"
               class="btn btn-default"
               data-toggle="modal">
                <span class="glyphicon glyphicon-plus"></span>
                &nbsp;&nbsp;<spring:message code="create"/>&nbsp;<spring:message code="candidate"/>
            </a>
        </div>
        <jsp:include page="dialogs/candidatesDialogs.jsp"/>
    </div>
</div>
<div class="row">
    <h1 class="page-header">Candidate details</h1>
    <div class="row">
        <div class="col-md-4">
            <p>
                <b>Name:</b> Anna Nowak
            </p>
            <p>
                <b>Phone number:</b> 12345678
            </p>
            <p>
                <b>E-Mail:</b> ANowak@gmail.com
            </p>
        </div>
        <div class="col-md-4">
            <p>
                <b>Address:</b> 7013 Forbes Rd
                         <p>    Zephyrhills, Florida 33541</p>
            </p>
            <p>
                <b>Date Available:</b> 01-12-2015
            </p>
            <p>
                <b>Can Relocate:</b> Yes
            </p>
        </div>
        <div class="col-md-4">
            <p>
                <b>Owner:</b> John Anderson
            </p>
            <p>
                <b>Created:</b> 07-06-2015 22:37:12
            </p>
            <p>
                <b>Key Skills:</b> Constructions builder
            </p>
        </div>
    </div>
</div>

<script src="<c:url value="/resources/js/pages/candidates.js"/>"></script>