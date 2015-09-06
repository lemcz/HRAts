<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
    <div ng-controller="CompanyDetailsController" ng-cloak>
        <input class="hidden" id="pathId" value="${pathId}"/>
        <h1 class="page-header">
            <p class="text-center">
                <spring:message code='companies.header'/>&nbsp;Details
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
              ng-submit="updateUsersInfo(companyData);">
            <div class="col-md-12">
                <button type="submit" class="btn btn-primary">Save changes</button>
                <a href=""
                   ng-click="openModal('addDepartmentModal', companyData)"
                   ng-disabled="gridOptions.multipleSelected"
                   role="button"
                   title="<spring:message code="add"/>&nbsp;<spring:message code="department"/>"
                   class="btn btn-default"
                   data-toggle="modal">
                    <span class="glyphicon glyphicon-copy"></span>&nbsp;&nbsp;<spring:message code="add"/>&nbsp;<spring:message code="department"/>
                </a>
                <a href=""
                   ng-click="openModal('deleteCompanyModal', companyData)"
                   role="button"
                   title="<spring:message code="delete"/>&nbsp;<spring:message code="company"/>"
                   class="btn btn-default"
                   data-toggle="modal">
                    <span class="glyphicon glyphicon-minus"></span>&nbsp;&nbsp;<spring:message code="delete"/>&nbsp;<spring:message code="company"/>
                </a>
            </div>
            <div class="col-md-12">
                <div class="col-md-4">
                    <div class="field"><strong>Name</strong>
                        <div click-to-edit="companyData.name"></div>
                    </div>
                    <div class="field"><strong>Country</strong>
                        <div click-to-edit="companyData.country"></div>
                    </div>
                    <div class="field"><strong>City</strong>
                        <div click-to-edit="companyData.city"></div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="field"><strong>Address</strong>
                        <div click-to-edit="companyData.address"></div>
                    </div>
                    <div class="field"><strong>Zip-code</strong>
                        <div click-to-edit="companyData.zipCode"></div>
                    </div>
                    <div class="field"><strong>Phone</strong>
                        <div click-to-edit="companyData.phoneNumber"></div>
                    </div>
                    <div class="field"><strong>Website</strong>
                        <div class="col-md-12">
                            <a href="http://{{companyData.website}}">{{ companyData.website  }}</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="field"><strong>Date created</strong>
                        <div class="col-md-12">
                            {{ companyData.dateEntered | date}}
                        </div>
                    </div>
                    <div class="field"><strong>Date modified</strong>
                        <div class="col-md-12">
                            {{ companyData.dateModified | date}}
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
                              ng-model="companyData.note"
                              name="note"
                              placeholder="<spring:message code='note'/> "></textarea>
                </div>
                <div class="col-md-6">
                    <div class="col-md-12"><Strong>Sectors</Strong></div>
                    <div class="col-md-4" ng-repeat="sector in companyData.sectorList">{{sector.name}}</div>
                    <div class="col-md-12"><strong>Departments</strong></div>
                    <div class="col-md-4" ng-repeat="department in departmentsCollection">{{department.name}}</div>
                </div>
            </div>
        </form>
        <br><br>
        <h2 class="page-header">
            <p>
                <spring:message code='relatedVacancies'/>
            </p>
        </h2>
        <div ng-controller="DetailsGridsController">
            <div class="grid" id="vacanciesGrid" ui-grid="vacanciesGridOptions" ui-grid-auto-resize ui-grid-resize-columns ui-grid-pinning ui-grid-selection ui-grid-move-columns ui-grid-exporter ui-grid-grouping></div>
            <h2 class="page-header">
                <p>
                    <spring:message code='relatedContacts'/>
                </p>
            </h2>
            <div class="grid" id="contactsGrid" ui-grid="contactsGridOptions" ui-grid-auto-resize ui-grid-resize-columns ui-grid-pinning ui-grid-selection ui-grid-move-columns ui-grid-exporter ui-grid-grouping></div>
        </div>
        <jsp:include page="dialogs/companiesDialogs.jsp"/>
    </div>
</div>

<script src="<c:url value="/resources/js/services/contactService.js"/>"></script>
<script src="<c:url value="/resources/js/services/companyService.js"/>"></script>
<script src="<c:url value="/resources/js/services/departmentService.js"/>"></script>
<script src="<c:url value="/resources/js/services/vacancyService.js"/>"></script>
<script src="<c:url value="/resources/js/pages/companies.js"/>"></script>
<script src="<c:url value="/resources/js/pages/gridConf.js"/>"></script>