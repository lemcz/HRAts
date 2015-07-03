<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/ng-template" id="addContactModal">
    <div class="modal-header">
        <button type="button" class="close"
                data-dismiss="modal"
                ng-click="cancel()">
            <span aria-hidden="true">&times;</span>
            <span class="sr-only">Close</span>
        </button>
        <h4 class="modal-title" id="addContactModalLabel">
            <spring:message code="create"/>&nbsp;<spring:message code="contact"/>
        </h4>
    </div>
    <form name="newContactForm" role="form" novalidate ng-submit="createContact();">
        <div class="modal-body">
            <div class="form-group">
                <label>* <spring:message code="contacts.name"/>:</label>
                <input type="text"
                       class="form-control"
                       required
                       autofocus
                       ng-model="newContact.name"
                       name="name"
                       placeholder="<spring:message code='contact'/>&nbsp;<spring:message code='contacts.name'/>"/>
                <label>
                    <span class="alert alert-danger"
                          ng-show="displayValidationError && newContactForm.name.$error.required">
                            <spring:message code="required"/>
                    </span>
                </label>
            </div>
            <div class="form-group">
                <label>* <spring:message code="contacts.note"/>:</label>
                <input type="text"
                       class="form-control"
                       required
                       ng-model="newContact.note"
                       name="note"
                       placeholder="<spring:message code='sample.description'/> "/>
            </div>
            <div class="form-group">
                <p>Selected: {{newContact.company}}</p>
                <ui-select ng-model="newContact.company"
                           on-select="fetchRelatedDepartments(newContact.company)"
                           theme="bootstrap"
                           ng-disabled="disabled"
                           reset-search-input="false"
                           style="width: 300px;">
                    <ui-select-match placeholder="Select a company">{{$select.selected.name}}</ui-select-match>
                    <ui-select-choices repeat="company in companiesCollection | propsFilter: {id: $select.search, name: $select.search}">
                        <div ng-bind-html="company.name | highlight: $select.search"></div>
                        <small>
                            id: <span ng-bind-html="''+company.id | highlight: $select.search"></span>
                            name: {{company.name}}
                        </small>
                    </ui-select-choices>
                </ui-select>

                <p>Selected: {{newContact.department}}</p>
                <ui-select ng-model="newContact.department"
                           on-select="fetchRelatedDepartments(newContact.company)"
                           theme="bootstrap"
                           ng-disabled="disabled"
                           reset-search-input="false"
                           tagging="departmentTransform"
                           style="width: 300px;">
                    <ui-select-match placeholder="Select a department">{{$select.selected.name}}</ui-select-match>
                    <ui-select-choices repeat="department in newContact.departments | propsFilter: {id: $select.search, name: $select.search}">
                        <div ng-bind-html="department.name | highlight: $select.search"></div>
                        <small>
                            id: <span ng-bind-html="''+department.id | highlight: $select.search"></span>
                            name: {{department.name}}
                        </small>
                    </ui-select-choices>
                </ui-select>

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
                   ng-disabled="newContactForm.$invalid"
                   value='<spring:message code="create"/>'/>
            <span class="alert alert-danger" ng-show="!createContactSuccess">
                <spring:message code="request.error"/>
            </span>
        </div>
    </form>
</script>

<script type="text/ng-template" id="updateContactModal">
    <div class="modal-header">
        <button type="button"
                class="close"
                ng-click="cancel()">
            <span aria-hidden="true">&times;</span>
            <span class="sr-only">Close</span>
        </button>
        <h4 class="modal-title" id="updateContactModalLabel">
            <spring:message code="update"/>&nbsp;<spring:message code="contact"/>
        </h4>
    </div>
    <form name="updateContactForm" novalidate ng-submit="updateContact(newContact);">
    <div class="modal-body">
            <div class="form-group">
                <input type="hidden"
                       required
                       ng-model="contact.id"
                       name="id"/>
                <label>* <spring:message code="contacts.name"/>:</label>
                <input type="text"
                       class="form-control"
                       autofocus
                       required
                       ng-model="newContact.name"
                       name="name"
                       placeholder="<spring:message code='contact'/>&nbsp;<spring:message code='contacts.name'/> "/>
                <label>
                    <span class="alert alert-danger"
                          ng-show="displayValidationError && updateContactForm.name.$error.required">
                        <spring:message code="required"/>
                    </span>
                </label>
            </div>
            <div class="form-group">
                <label>* <spring:message code="contacts.note"/>:</label>
                <input type="text"
                       class="form-control"
                       required
                       ng-model="newContact.note"
                       name="note"
                       placeholder="<spring:message code='sample.description'/> "/>
                <label>
                    <span class="alert alert-danger"
                          ng-show="displayValidationError && updateContactForm.email.$error.required">
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

<script type="text/ng-template" id="deleteContactModal">
    <div class="modal-header">
        <button type="button" class="close"
                ng-click="cancel()"
                data-dismiss="modal">
            <span aria-hidden="true">&times;</span>
            <span class="sr-only">Close</span>
        </button>
        <h4 id="deleteContactModalLabel" class="modal-title">
            <spring:message code="delete"/>&nbsp;<spring:message code="contact"/>
        </h4>
    </div>
    <div class="modal-body">
        <form name="deleteContactForm" novalidate ng-submit="removeRow(contact)">
            <p><spring:message code="delete.confirm"/>:&nbsp;{{contact.name}}?</p>
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