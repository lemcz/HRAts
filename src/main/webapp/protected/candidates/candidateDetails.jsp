<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
    <div ng-controller="CandidateDetailsController" ng-cloak>
        <input class="hidden" id="pathId" value="${pathId}"/>
        <h1 class="page-header">
            <p class="text-center">
                <spring:message code='candidates.header'/>&nbsp;Details
            </p>
        </h1>
        <h2 class="page-header">
            <p>
                <spring:message code='basicInformation'/>
            </p>
        </h2>
        <form name="updateUsersInfoForm"
              role="form"
              novalidate
              autocomplete="off"
              ng-submit="updateUsersInfo(candidateData);">
            <div class="col-md-12">
                <button type="submit" class="btn btn-primary">Save changes</button>
                <a href=""
                   ng-click="openModal('addToVacancyModal', row)"
                   ng-disabled="gridOptions.multipleSelected"
                   role="button"
                   title="Add To Vacancy"
                   class="btn btn-default"
                   data-toggle="modal">
                    <span class="glyphicon glyphicon-link"></span>&nbsp;&nbsp;Add&nbsp;To&nbsp;Vacancy
                </a>
                <a href=""
                   ng-click="openModal('logActivityModal', row)"
                   ng-disabled="gridOptions.multipleSelected"
                   role="button"
                   title="Log Activity"
                   class="btn btn-default"
                   data-toggle="modal">
                    <span class="glyphicon glyphicon-edit"></span>&nbsp;&nbsp;Log&nbsp;Activity
                </a>
                <a href=""
                   ng-click="openModal('deleteCandidateModal', candidateData)"
                   role="button"
                   title="<spring:message code="delete"/>&nbsp;<spring:message code="candidate"/>"
                   class="btn btn-default"
                   data-toggle="modal">
                    <span class="glyphicon glyphicon-minus"></span>&nbsp;&nbsp;<spring:message code="delete"/>&nbsp;<spring:message code="candidate"/>
                </a>
            </div>
            <div class="col-md-12">
                <div class="col-md-4">
                    <div class="field"><strong>Name</strong>
                        <div click-to-edit="candidateData.name"></div>
                    </div>
                    <div class="field"><strong>Middle Name</strong>
                        <div click-to-edit="candidateData.middleName"></div>
                    </div>

                    <div class="field"><strong>Last Name</strong>
                        <div click-to-edit="candidateData.lastName"></div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="field"><strong>Email</strong>
                        <div class="col-md-12">{{candidateData.email}}</div>
                    </div>
                    <div class="field"><strong>Phone</strong>
                        <div click-to-edit="candidateData.phoneNumber"></div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="field"><strong>Date created</strong>
                        <div class="col-md-12">
                            {{ candidateData.dateEntered | date}}
                        </div>
                    </div>
                    <div class="field"><strong>Date modified</strong>
                        <div class="col-md-12">
                            {{ candidateData.dateModified | date}}
                        </div>
                    </div>
                    <div class="field"><strong>Owner</strong>
                        <div class="col-md-12">
                            <a href="mailto: {{ candidateData.owner.email }}">{{ candidateData.owner.email }}</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-12">
                <h3>
                    <p>
                        <spring:message code='addressInformation'/>
                    </p>
                </h3>
                <div class="col-md-4">
                    <div class="field"><strong>Country</strong>
                        <div click-to-edit="candidateData.candidateInformation.country"></div>
                    </div>
                    <div class="field"><strong>City</strong>
                        <div click-to-edit="candidateData.candidateInformation.city"></div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="field"><strong>Address</strong>
                        <div click-to-edit="candidateData.candidateInformation.address"></div>
                    </div>
                    <div class="field"><strong>Zip-code</strong>
                        <div click-to-edit="candidateData.candidateInformation.zipcode"></div>
                    </div>
                </div>
            </div>
            <div class="col-md-12">
                <h3>
                    <p>
                        <spring:message code='contractInformation'/>
                    </p>
                </h3>
                <div class="col-md-4">
                    <div class="field"><strong>Prefered contract type</strong></div>
                    <ui-select ng-model="candidateData.candidateInformation.contractType"
                               required
                               reset-search-input="false">
                        <ui-select-match placeholder="Select contract type">{{$select.selected.name}}</ui-select-match>
                        <ui-select-choices group-by="someGroupFn" repeat="contract in contractsCollection | propsFilter: {name: $select.search, id: $select.search}">
                            <div ng-bind-html="contract.name | highlight: $select.search"></div>
                            <small>
                                id: {{contract.id}}
                            </small>
                        </ui-select-choices>
                    </ui-select>
                    <div class="field"><strong>Financial requirements netto</strong>
                        <div click-to-edit="candidateData.candidateInformation.financialReqNetto"></div>
                    </div>
                    <div class="field"><strong>Financial requirements brutto</strong>
                        <div click-to-edit="candidateData.candidateInformation.financialReqBrutto"></div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="field"><strong>Current job termination date</strong>
                        <div click-to-edit="candidateData.candidateInformation.datePrevJobTermination"></div>
                    </div>
                    <div class="field"><strong>Possible start date</strong>
                        <div click-to-edit="candidateData.candidateInformation.startDate | date"></div>
                    </div>
                </div>
            </div>
            <div class="col-md-12">
                <div class="col-md-6">
                    <strong>Note</strong>
                    <textarea rows="4"
                              cols="50"
                              type="text"
                              class="form-control"
                              required
                              ng-model="candidateData.note"
                              name="note"
                              placeholder="<spring:message code='note'/> "></textarea>
                </div>
                <div class="col-md-6">
                    <div class="col-md-12"><Strong>Attachments</Strong></div>
                    <div class="col-md-4" ng-repeat="attachment in companyData.candidateAttachmentList">{{sector.name}}</div>
                </div>
            </div>
        </form>
        <br><br>
        <div ng-controller="DetailsGridsController">
        <h2 class="page-header">
            <p>
                <spring:message code='related'/>&nbsp;<spring:message code='vacancies.header'/>
            </p>
        </h2>
            <div class="grid" id="candidatesGrid" ui-grid="vacanciesGridOptions" ui-grid-auto-resize ui-grid-resize-columns ui-grid-pinning ui-grid-selection ui-grid-move-columns ui-grid-exporter ui-grid-grouping></div>
            <h2 class="page-header">
                <p>
                    <spring:message code='related'/>&nbsp;<spring:message code='activities.header'/>
                </p>
            </h2>
            <div class="grid" id="activitiesGrid" ui-grid="activitiesGridOptions" ui-grid-auto-resize ui-grid-resize-columns ui-grid-pinning ui-grid-selection ui-grid-move-columns ui-grid-exporter ui-grid-grouping></div>
        </div>
        <jsp:include page="dialogs/candidatesDialogs.jsp"/>
    </div>
</div>

<script src="<c:url value="/resources/js/services/activityService.js"/>"></script>
<script src="<c:url value="/resources/js/services/candidateService.js"/>"></script>
<script src="<c:url value="/resources/js/services/companyService.js"/>"></script>
<script src="<c:url value="/resources/js/services/contactService.js"/>"></script>
<script src="<c:url value="/resources/js/services/departmentService.js"/>"></script>
<script src="<c:url value="/resources/js/services/lookupService.js"/>"></script>
<script src="<c:url value="/resources/js/services/vacancyService.js"/>"></script>
<script src="<c:url value="/resources/js/services/vacancyUserService.js"/>"></script>
<script src="<c:url value="/resources/js/pages/gridConf.js"/>"></script>
<script src="<c:url value="/resources/js/pages/candidates.js"/>"></script>