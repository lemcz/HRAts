<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/ng-template" id="addVacancyModal">
    <div class="modal-header">
        <button type="button" class="close"
                data-dismiss="modal"
                ng-click="cancel()">
            <span aria-hidden="true">&times;</span>
            <span class="sr-only">Close</span>
        </button>
        <h4 class="modal-title" id="addVacancyModalLabel">
            <spring:message code="create"/>&nbsp;<spring:message code="vacancy"/>
        </h4>
    </div>
    <form name="newVacancyForm" role="form" novalidate ng-submit="createVacancy();">
        <div class="modal-body">
            <div class="form-group">
                <label>* <spring:message code="vacancies.name"/>:</label>
                <input type="text"
                       class="form-control"
                       required
                       autofocus
                       ng-model="newVacancy.name"
                       name="name"
                       placeholder="<spring:message code='vacancy'/>&nbsp;<spring:message code='vacancies.name'/>"/>
                <label>
                    <span class="alert alert-danger"
                          ng-show="displayValidationError && newVacancyForm.name.$error.required">
                            <spring:message code="required"/>
                    </span>
                </label>
            </div>
            <div class="form-group">
                <label>* <spring:message code="vacancies.description"/>:</label>
                <input type="text"
                       class="form-control"
                       required
                       ng-model="newVacancy.description"
                       name="description"
                       placeholder="<spring:message code='sample.description'/> "/>
            </div>
            <div class="form-group">
                <label>* <spring:message code="vacancies.numberOfVacancies"/>:</label>
                <input type="number"
                       class="form-control"
                       required
                       ng-model="newVacancy.numberOfVacancies"
                       ng-pattern="/^[1-9][0-9]*$/"
                       name="numberOfVacancies"
                       placeholder="<spring:message code='sample.number'/> "/>
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
                   ng-disabled="newVacancyForm.$invalid"
                   value='<spring:message code="create"/>'/>
            <span class="alert alert-danger" ng-show="!createVacancySuccess">
                <spring:message code="request.error"/>
            </span>
        </div>
    </form>
</script>

<script type="text/ng-template" id="updateVacancyModal">
    <div class="modal-header">
        <button type="button"
                class="close"
                ng-click="cancel()">
            <span aria-hidden="true">&times;</span>
            <span class="sr-only">Close</span>
        </button>
        <h4 class="modal-title" id="updateVacancyModalLabel">
            <spring:message code="update"/>&nbsp;<spring:message code="vacancy"/>
        </h4>
    </div>
    <form name="updateVacancyForm" novalidate ng-submit="updateVacancy(newVacancy);">
    <div class="modal-body">
            <div class="form-group">
                <input type="hidden"
                       required
                       ng-model="vacancy.id"
                       name="id"/>
                <label>* <spring:message code="vacancies.name"/>:</label>
                <input type="text"
                       class="form-control"
                       autofocus
                       required
                       ng-model="newVacancy.name"
                       name="name"
                       placeholder="<spring:message code='vacancy'/>&nbsp;<spring:message code='vacancies.name'/> "/>
                <label>
                    <span class="alert alert-danger"
                          ng-show="displayValidationError && updateVacancyForm.name.$error.required">
                        <spring:message code="required"/>
                    </span>
                </label>
            </div>
            <div class="form-group">
                <label>* <spring:message code="vacancies.description"/>:</label>
                <input type="text"
                       class="form-control"
                       required
                       ng-model="newVacancy.description"
                       name="description"
                       placeholder="<spring:message code='sample.description'/> "/>
                <label>
                    <span class="alert alert-danger"
                          ng-show="displayValidationError && updateVacancyForm.email.$error.required">
                        <spring:message code="required"/>
                    </span>
                </label>
            </div>
            <div class="form-group">
                <label>* <spring:message code="vacancies.numberOfVacancies"/>:</label>
                <input type="number"
                       class="form-control"
                       required
                       ng-model="newVacancy.numberOfVacancies"
                       name="numberOfVacancies"
                       placeholder="<spring:message code='sample.phone'/> "/>
                <label>
                    <span class="alert alert-danger"
                          ng-show="displayValidationError && updateVacancyForm.phoneNumber.$error.required">
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

<script type="text/ng-template" id="deleteVacancyModal">
    <div class="modal-header">
        <button type="button" class="close"
                ng-click="cancel()"
                data-dismiss="modal">
            <span aria-hidden="true">&times;</span>
            <span class="sr-only">Close</span>
        </button>
        <h4 id="deleteVacancyModalLabel" class="modal-title">
            <spring:message code="delete"/>&nbsp;<spring:message code="vacancy"/>
        </h4>
    </div>
    <div class="modal-body">
        <form name="deleteVacancyForm" novalidate ng-submit="removeRow(vacancy)">
            <p><spring:message code="delete.confirm"/>:&nbsp;{{vacancy.name}}?</p>
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