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
                    <div class="field"><strong>Number of vacancies</strong>
                        <div click-to-edit="candidateData.numberOfVacancies"></div>
                    </div>
                    <div class="field"><strong>Offered salary</strong>
                        <div click-to-edit="candidateData.salary"></div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="field"><strong>Company</strong>
                        <a href="" ng-click="redirect(candidateData.department.company, 'companies')">{{candidateData.department.company.name}}</a>
                    </div>
                    <div class="field"><strong>Department</strong>
                        {{candidateData.department.name}}
                    </div>
                    <div class="field"><strong>Manager</strong>
                        <div class="col-md-12">
                            <a href="" ng-click="redirect(candidateData.manager, 'contacts')">{{ candidateData.manager.email }}</a>
                        </div>
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
                <div class="col-md-12">
                    <div class="field"><strong>Description</strong>
                        <div text-angular ng-model="candidateData.description"></div>
                    </div>
                </div>
            </div>
        </form>
        <br><br>
        <div ng-controller="DetailsGridsController">
        <h2 class="page-header">
            <p>
                <spring:message code='relatedVacancies'/>
            </p>
        </h2>
            <div class="grid" id="candidatesGrid" ui-grid="vacanciesGridOptions" ui-grid-auto-resize ui-grid-resize-columns ui-grid-pinning ui-grid-selection ui-grid-move-columns ui-grid-exporter ui-grid-grouping></div>
            <h2 class="page-header">
                <p>
                    <spring:message code='relatedActivites'/>
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