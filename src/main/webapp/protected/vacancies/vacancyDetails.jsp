<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
    <div ng-controller="VacancyDetailsController" ng-cloak>
        <input class="hidden" id="pathId" value="${pathId}"/>
        <h1 class="page-header">
            <p class="text-center">
                <spring:message code='vacancies.header'/>&nbsp;Details
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
              ng-submit="updateUsersInfo(vacancyData);">
            <div class="col-md-12">
                <button type="submit" class="btn btn-primary">Save changes</button>
                <a href=""
                   ng-click="openModal('deleteVacancyModal', vacancyData)"
                   role="button"
                   title="<spring:message code="delete"/>&nbsp;<spring:message code="vacancy"/>"
                   class="btn btn-default"
                   data-toggle="modal">
                    <span class="glyphicon glyphicon-minus"></span>&nbsp;&nbsp;<spring:message code="delete"/>&nbsp;<spring:message code="vacancy"/>
                </a>
            </div>
            <div class="col-md-12">
                <div class="col-md-4">
                    <div class="field"><strong>Name</strong>
                        <div click-to-edit="vacancyData.name"></div>
                    </div>
                    <div class="field"><strong>Number of vacancies</strong>
                        <div click-to-edit="vacancyData.numberOfVacancies"></div>
                    </div>
                    <div class="field"><strong>Offered salary</strong>
                        <div click-to-edit="vacancyData.salary"></div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="field"><strong>Company</strong>
                        <a href="" ng-click="redirect(vacancyData.department.company, 'companies')">{{vacancyData.department.company.name}}</a>
                    </div>
                    <div class="field"><strong>Department</strong>
                        {{vacancyData.department.name}}
                    </div>
                    <div class="field"><strong>Manager</strong>
                        <div class="col-md-12">
                            <a href="" ng-click="redirect(vacancyData.manager, 'contacts')">{{ vacancyData.manager.email }}</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="field"><strong>Date created</strong>
                        <div class="col-md-12">
                            {{ vacancyData.dateEntered | date}}
                        </div>
                    </div>
                    <div class="field"><strong>Date modified</strong>
                        <div class="col-md-12">
                            {{ vacancyData.dateModified | date}}
                        </div>
                    </div>
                    <div class="field"><strong>Owner</strong>
                        <div class="col-md-12">
                            <a href="mailto: {{ vacancyData.department.owner.email }}">{{ vacancyData.department.owner.email }}</a>
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
                              ng-model="vacancyData.note"
                              name="note"
                              placeholder="<spring:message code='note'/> "></textarea>
                </div>
                <div class="col-md-12">
                    <div class="field"><strong>Description</strong>
                        <div text-angular ng-model="vacancyData.description"></div>
                    </div>
                </div>
            </div>
        </form>
        <br><br>
        <h2 class="page-header">
            <p>
                <spring:message code='related'/>&nbsp;<spring:message code='candidates.header'/>
            </p>
        </h2>
        <div ng-controller="DetailsGridsController">
            <div class="grid" id="vacanciesGrid" ui-grid="candidatesGridOptions" ui-grid-auto-resize ui-grid-resize-columns ui-grid-pinning ui-grid-selection ui-grid-move-columns ui-grid-exporter ui-grid-grouping></div>
        </div>
        <jsp:include page="dialogs/vacanciesDialogs.jsp"/>
    </div>
</div>

<script src="<c:url value="/resources/js/services/candidateService.js"/>"></script>
<script src="<c:url value="/resources/js/services/contactService.js"/>"></script>
<script src="<c:url value="/resources/js/services/vacancyService.js"/>"></script>
<script src="<c:url value="/resources/js/pages/vacancies.js"/>"></script>
<script src="<c:url value="/resources/js/pages/gridConf.js"/>"></script>