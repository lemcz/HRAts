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
    <form name="newContactForm" role="form" novalidate ng-submit="createContact(files);" class="form">
        <div class="modal-body">
            <fieldset class="form-inline">
                <div class="col-md-12">
                    <label class="control-label"><spring:message code="user.name"/></label>
                </div>
                <div class="col-md-12">
                <div class="form-group">
                        <label class="control-label sr-only">* <spring:message code="user.name"/>:</label>
                        <input type="text"
                               class="form-control"
                               required
                               autofocus
                               ng-model="newContact.name"
                               name="name"
                               placeholder="<spring:message code='user.name'/>"/>
                </div>
                <div class="form-group">
                    <label class="control-label sr-only">* <spring:message code="user.middleName"/>:</label>
                    <input type="text"
                           class="form-control"
                               required
                               autofocus
                               ng-model="newContact.middleName"
                               name="name"
                               placeholder="<spring:message code='user.middleName'/>"/>
                </div>
                <div class="form-group">
                    <label class="control-label sr-only">* <spring:message code="user.lastName"/>:</label>
                    <input type="text"
                           class="form-control"
                           required
                           autofocus
                           ng-model="newContact.lastName"
                           name="name"
                           placeholder="<spring:message code='user.lastName'/>"/>
                </div>
                    </div>
            </fieldset>
            <fieldset class="form-inline">
                    <div class="col-md-12">
                        <label class="control-label"><spring:message code="contact"/></label>
                    </div>
                <div class="col-md-12">
                <div class="form-group">
                        <label class="control-label sr-only">* <spring:message code="phoneNumber"/>:</label>
                        <input type="tel"
                               class="form-control"
                               required
                               autofocus
                               ng-model="newContact.phoneNumber"
                               name="phoneNumber"
                               placeholder="<spring:message code='sample.phone'/>"/>
                    </div>
                    <div class="form-group">
                        <label class="control-label sr-only">* <spring:message code="email"/>:</label>
                        <input type="email"
                               class="form-control"
                               required
                               autofocus
                               ng-model="newContact.email"
                               name="email"
                               placeholder="<spring:message code='sample.email'/>"/>
                    </div>
                </div>
            </fieldset>
            <fieldset class="form">
                <div class="form-group col-sm-12 col-md-12">
                    <label class="control-label"><spring:message code="note"/></label>
                    <textarea rows="4"
                              cols="50"
                              type="text"
                              class="form-control"
                              required
                              ng-model="newContact.note"
                              name="note"
                              placeholder="<spring:message code='note'/> "/>
                </div>
            </fieldset>
            <fieldset class="form">
                <label class="control-label col-md-12"><spring:message code="assign.relations"/></label>
                <div class="col-md-6">
                <ui-select ng-model="newContact.company"
                           on-select="fetchRelatedDepartments(newContact.company)"
                           theme="bootstrap"
                           ng-disabled="disabled"
                           reset-search-input="false">
                    <ui-select-match placeholder="Assign company">{{$select.selected.name}}</ui-select-match>
                    <ui-select-choices repeat="company in companiesCollection | propsFilter: {id: $select.search, name: $select.search}">
                        <div ng-bind-html="company.name | highlight: $select.search"></div>
                        <small>
                            id: <span ng-bind-html="''+company.id | highlight: $select.search"></span>
                            name: {{company.name}}
                        </small>
                    </ui-select-choices>
                </ui-select>
                </div>
                <div class="col-md-6">
                <ui-select ng-model="newContact.departmentList[0]"
                           theme="bootstrap"
                           ng-disabled="disabled"
                           reset-search-input="false"
                           tagging="departmentTransform">
                    <ui-select-match placeholder="Assign department">{{$select.selected.name}}</ui-select-match>
                    <ui-select-choices repeat="department in newContact.company.departmentList | propsFilter: {id: $select.search, name: $select.search}">
                        <div ng-bind-html="department.name | highlight: $select.search"></div>
                        <small>
                            id: <span ng-bind-html="''+department.id | highlight: $select.search"></span>
                            name: {{department.name}}
                        </small>
                    </ui-select-choices>
                </ui-select>
                </div>
            </fieldset>
            <fieldset class="form">
                <label class="control-label col-md-12"><spring:message code="file.attach"/></label>
                <div class = "form-group col-md-6">
                <div class="well well-lg text-center" ngf-drop ngf-select ng-model="files" ngf-multiple="true" ngf-keep="true" ngf-keep-distinct="true">Select Files Or Drop Them Here</div>
            </div>
            <div class="col-md-6">
                Files:
            <ul>
                <li ng-repeat="f in files" >{{f.name}}
                    <span class="glyphicon glyphicon-minus"
                          ng-click="removeFromArray(files, f)"
                          role="button"
                          title="<spring:message code="delete"/>&nbsp;<spring:message code="contact"/>"
                          class="btn btn-default"
                          data-toggle="modal">
                   </span>
                </li>
            </ul>
            </div>
            </fieldset>
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
                       placeholder="<spring:message code='contacts.name'/> "/>
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