<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
    <div ng-controller="ContactDetailsController" ng-cloak>
        <input class="hidden" id="pathId" value="${pathId}"/>
        <h1 class="page-header">
            <p class="text-center">
                <spring:message code='contacts.header'/>&nbsp;Details
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
              ng-submit="updateUsersInfo(contactData);">
            <div class="col-md-12">
                <button type="submit" class="btn btn-primary">Save changes</button>
                <a href=""
                   ng-click="openModal('logActivityModal', candidateData)"
                   role="button"
                   title="Log Activity"
                   class="btn btn-default"
                   data-toggle="modal">
                    <span class="glyphicon glyphicon-edit"></span>&nbsp;&nbsp;Log&nbsp;Activity
                </a>
                <a href=""
                   ng-click="openModal('deleteContactModal', candidateData)"
                   role="button"
                   title="<spring:message code="delete"/>&nbsp;<spring:message code="contact"/>"
                   class="btn btn-default"
                   data-toggle="modal">
                    <span class="glyphicon glyphicon-minus"></span>&nbsp;&nbsp;<spring:message code="delete"/>&nbsp;<spring:message code="contact"/>
                </a>
            </div>
            <div class="col-md-12">
                <div class="col-md-4">
                    <div class="field"><strong>Name</strong>
                        <div click-to-edit="contactData.name"></div>
                    </div>
                    <div class="field"><strong>Middle Name</strong>
                        <div click-to-edit="contactData.middleName"></div>
                    </div>

                    <div class="field"><strong>Last Name</strong>
                        <div click-to-edit="contactData.lastName"></div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="field"><strong>Email</strong>
                        <div class="col-md-12">{{contactData.email}}</div>
                    </div>
                    <div class="field"><strong>Phone</strong>
                        <div click-to-edit="contactData.phoneNumber"></div>
                    </div>
                    <div class="field"><strong>Date created</strong>
                        <div class="col-md-12">
                            {{ contactData.dateEntered | date}}
                        </div>
                    </div>
                    <div class="field"><strong>Date modified</strong>
                        <div class="col-md-12">
                            {{ contactData.dateModified | date}}
                        </div>
                    </div>
                    <div class="field"><strong>Owner</strong>
                        <div class="col-md-12">
                            <a href="mailto:{{contactData.owner.email}}">{{ contactData.owner.email }}</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-12">
                    <strong>Note</strong>
                        <textarea rows="4"
                                  cols="50"
                                  type="text"
                                  class="form-control"
                                  required
                                  ng-model="contactData.note"
                                  name="note"
                                  placeholder="<spring:message code='note'/> "></textarea>
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
            <div class="grid" id="vacanciesGrid" ui-grid="vacanciesGridOptions" ui-grid-auto-resize ui-grid-resize-columns ui-grid-pinning ui-grid-selection ui-grid-move-columns ui-grid-exporter ui-grid-grouping></div>
            <h2 class="page-header">
                <p>
                    <spring:message code='relatedActivites'/>
                </p>
            </h2>
            <div class="grid" id="activitiesGrid" ui-grid="activitiesGridOptions" ui-grid-auto-resize ui-grid-resize-columns ui-grid-pinning ui-grid-selection ui-grid-move-columns ui-grid-exporter ui-grid-grouping></div>
        </div>
        <jsp:include page="dialogs/contactsDialogs.jsp"/>
    </div>
</div>

<script src="<c:url value="/resources/js/services/activityService.js"/>"></script>
<script src="<c:url value="/resources/js/services/companyService.js"/>"></script>
<script src="<c:url value="/resources/js/services/contactService.js"/>"></script>
<script src="<c:url value="/resources/js/services/departmentService.js"/>"></script>
<script src="<c:url value="/resources/js/services/lookupService.js"/>"></script>
<script src="<c:url value="/resources/js/services/vacancyService.js"/>"></script>
<script src="<c:url value="/resources/js/pages/contacts.js"/>"></script>
<script src="<c:url value="/resources/js/pages/gridConf.js"/>"></script>