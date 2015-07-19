<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/ng-template" id="addCandidateModal">
    <div class="modal-header">
        <button type="button" class="close"
                data-dismiss="modal"
                ng-click="cancel()">
            <span aria-hidden="true">&times;</span>
            <span class="sr-only">Close</span>
        </button>
        <h4 class="modal-title" id="addCandidateModalLabel">
            <spring:message code="create"/>&nbsp;<spring:message code="candidate"/>
        </h4>
    </div>
    <form name="newCandidateForm" role="form" novalidate ng-submit="createCandidate();">
        <div class="modal-body">
            <div class="form-group">
                <label>* <spring:message code="candidates.name"/>:</label>
                <input type="text"
                       class="form-control"
                       required
                       autofocus
                       ng-model="newCandidate.name"
                       name="name"
                       placeholder="<spring:message code='candidate'/>&nbsp;<spring:message code='candidates.name'/>"/>
                <label>* Email</label>
                <input type="email",
                       class="form-control",
                       required
                       ng-model="newCandidate.email",
                       name="email"
                       placeholder="<spring:message code='sample.email'/> "/>
                <label>
                    <span class="alert alert-danger"
                          ng-show="displayValidationError && newCandidateForm.name.$error.required">
                            <spring:message code="required"/>
                    </span>
                </label>
            </div>
            <div class="form-group">
                <label>* <spring:message code="candidates.note"/>:</label>
                <input type="text"
                       class="form-control"
                       required
                       ng-model="newCandidate.note"
                       name="note"
                       placeholder="<spring:message code='sample.description'/> "/>
            </div>
        </div>
        <div class="modal-footer">
            <button class="btn btn-default"
                    type="button"
                    data-dismiss="modal"
                    ng-click="cancel()"
                    aria-hidden="true">
                <spring:message code="cancel"/>
            </button>
            <input type="submit"
                   class="btn btn-primary"
                   ng-disabled="newCandidateForm.$invalid"
                   value='<spring:message code="create"/>'/>
            <span class="alert alert-danger" ng-show="!createCandidateSuccess">
                <spring:message code="request.error"/>
            </span>
        </div>
    </form>
</script>

<script type="text/ng-template" id="updateCandidateModal">
    <div class="modal-header">
        <button type="button"
                class="close"
                ng-click="cancel()">
            <span aria-hidden="true">&times;</span>
            <span class="sr-only">Close</span>
        </button>
        <h4 class="modal-title" id="updateCandidateModalLabel">
            <spring:message code="update"/>&nbsp;<spring:message code="candidate"/>
        </h4>
    </div>
    <form name="updateCandidateForm" novalidate ng-submit="updateCandidate(newCandidate);">
        <div class="modal-body">
            <div class="form-group">
                <input type="hidden"
                       required
                       ng-model="candidate.id"
                       name="id"
                       value="{{candidate.id}}"/>
                <label>* <spring:message code="candidates.name"/>:</label>
                <input type="text"
                       class="form-control"
                       autofocus
                       required
                       ng-model="newCandidate.name"
                       name="name"
                       placeholder="<spring:message code='candidate'/>&nbsp;<spring:message code='candidates.name'/> "/>
                <label>
                    <span class="alert alert-danger"
                          ng-show="displayValidationError && updateCandidateForm.name.$error.required">
                        <spring:message code="required"/>
                    </span>
                </label>
            </div>
            <div class="form-group">
                <label>* <spring:message code="candidates.note"/>:</label>
                <input type="text"
                       class="form-control"
                       required
                       ng-model="newCandidate.note"
                       name="note"
                       placeholder="<spring:message code='sample.description'/> "/>
                <label>
                    <span class="alert alert-danger"
                          ng-show="displayValidationError && updateCandidateForm.email.$error.required">
                        <spring:message code="required"/>
                    </span>
                </label>
            </div>
        </div>
        <div class="modal-footer">
            <button class="btn btn-default"
                    type="button"
                    ng-click="cancel();"
                    aria-hidden="true">
                <spring:message code="cancel"/></button>
            <input type="submit"
                   class="btn btn-primary"
                   value='<spring:message code="update"/>'/>
        <span class="alert alert-danger dialogErrorMessage"
              ng-show="errorOnSubmit">
            <spring:message code="request.error"/>
        </span>
        </div>
    </form>
</script>

<script type="text/ng-template" id="deleteCandidateModal">
    <div class="modal-header">
        <button type="button" class="close"
                ng-click="cancel()"
                data-dismiss="modal">
            <span aria-hidden="true">&times;</span>
            <span class="sr-only">Close</span>
        </button>
        <h4 id="deleteCandidateModalLabel" class="modal-title">
            <spring:message code="delete"/>&nbsp;<spring:message code="candidate"/>
        </h4>
    </div>
    <div class="modal-body">
        <form name="deleteCandidateForm" novalidate ng-submit="removeRow(candidate)">
            <p><spring:message code="delete.confirm"/>:&nbsp;{{candidate.name}}?</p>
            <button class="btn btn-default"
                    type="button"
                    data-dismiss="modal"
                    ng-click="cancel()"
                    aria-hidden="true">
                <spring:message code="cancel"/>
            </button>
            <input type="submit"
                   class="btn btn-danger"
                   value='<spring:message code="delete"/>'/>
        </form>
    </div>
    <span class="alert alert-danger dialogErrorMessage"
          ng-show="errorOnSubmit">
        <spring:message code="request.error"/>
    </span>
    <span class="alert alert-danger dialogErrorMessage"
          ng-show="errorIllegalAccess">
        <spring:message code="request.illegal.access"/>
    </span>
</script>

<script type="text/ng-template" id="addToVacancyModal">
    <div class="modal-header">
        <button type="button" class="close"
                data-dismiss="modal"
                ng-click="cancel()">
            <span aria-hidden="true">&times;</span>
            <span class="sr-only">Close</span>
        </button>
        <h4 class="modal-title" id="addToVacancyModalLabel">
            <spring:message code="create"/>&nbsp;<spring:message code="candidate"/>
        </h4>
    </div>
    <form name="addToVacancyForm" role="form" novalidate ng-submit="addToVacancy(candidate);">
        <div class="modal-body">
            <div class="form-group">
                <input type="hidden"
                       required
                       ng-model="candidate.id"
                       name="id"
                       value="{{candidate.id}}"/>
                <label>* <spring:message code="candidates.vacancy"/>: </label>
                <ui-select ng-model="candidate.vacancyUserCandidateList[0].vacancy"
                           theme="bootstrap"
                           ng-disabled="disabled"
                           reset-search-input="false"
                           style="width: 300px;">
                    <ui-select-match placeholder="Select a vacancy">{{$select.selected.name}}</ui-select-match>
                    <ui-select-choices repeat="vacancy in vacancyCollection | propsFilter: {id: $select.search, name: $select.search}">
                        <div ng-bind-html="vacancy.name | highlight: $select.search"></div>
                        <small>
                            id: <span ng-bind-html="''+vacancy.id | highlight: $select.search"></span>
                            name: {{ vacancy.name }}
                            description: {{ vacancy.description }}
                        </small>
                    </ui-select-choices>
                </ui-select>
                <label>* <spring:message code="candidates.activityType.note"/>: </label>
                <input type="text",
                       class="form-control",
                       ng-model="candidate.vacancyUserCandidateList[0].note",
                       name="note"/>
            </div>
        </div>
        <div class="modal-footer">
            <button class="btn btn-default"
                    type="button"
                    data-dismiss="modal"
                    ng-click="cancel()"
                    aria-hidden="true">
                <spring:message code="cancel"/>
            </button>
            <input type="submit"
                   class="btn btn-primary"
                   ng-disabled="newCandidateForm.$invalid"
                   value='<spring:message code="create"/>'/>
            <span class="alert alert-danger" ng-show="!createCandidateSuccess">
                <spring:message code="request.error"/>
            </span>
        </div>
    </form>
</script>

<script type="text/ng-template" id="logActivityModal">
    <div class="modal-header">
        <button type="button" class="close"
                data-dismiss="modal"
                ng-click="cancel()">
            <span aria-hidden="true">&times;</span>
            <span class="sr-only">Close</span>
        </button>
        <h4 class="modal-title" id="logActivityModalLabel">
            <spring:message code="create"/>&nbsp;<spring:message code="activity"/>
        </h4>
    </div>
    <form name="logActivityForm" role="form" novalidate ng-submit="logActivity(candidate);">
        <div class="modal-body">
            <div class="form-group">
                <input type="hidden"
                       required
                       ng-model="candidate.id"
                       name="id"
                       value="{{candidate.id}}"/>
                <label>* <spring:message code="candidates.activityType"/>: </label>
                <ui-select ng-model="activity.activityType"
                           theme="bootstrap"
                           ng-disabled="disabled"
                           reset-search-input="false"
                           style="width: 300px;">
                    <ui-select-match placeholder="Select activity type">{{$select.selected.name}}</ui-select-match>
                    <ui-select-choices repeat="activityType in activityTypeCollection | propsFilter: {id: $select.search, name: $select.search}">
                        <div ng-bind-html="activityType.name | highlight: $select.search"></div>
                        <small>
                            id: <span ng-bind-html="''+activityType.id | highlight: $select.search"></span>
                            name: {{activityType.name}}
                        </small>
                    </ui-select-choices>
                </ui-select>
                <label>* <spring:message code="candidates.activityType.note"/>: </label>
                <input type="text"
                       class="form-control"
                       ng-model="activity.note"
                       name="note"/>
            </div>
        </div>
        <div class="modal-footer">
            <button class="btn btn-default"
                    type="button"
                    data-dismiss="modal"
                    ng-click="cancel()"
                    aria-hidden="true">
                <spring:message code="cancel"/>
            </button>
            <input type="submit"
                   class="btn btn-primary"
                   ng-disabled="newCandidateForm.$invalid"
                   value='<spring:message code="create"/>'/>
            <span class="alert alert-danger" ng-show="!createCandidateSuccess">
                <spring:message code="request.error"/>
            </span>
        </div>
    </form>
</script>