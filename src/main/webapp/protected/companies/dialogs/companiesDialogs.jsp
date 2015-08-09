<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/ng-template" id="addCompanyModal">
    <div class="modal-header">
        <button type="button" class="close"
                data-dismiss="modal"
                ng-click="cancel()">
            <span aria-hidden="true">&times;</span>
            <span class="sr-only">Close</span>
        </button>
        <h4 class="modal-title" id="addCompanyModalLabel">
            <spring:message code="create"/>&nbsp;<spring:message code="company"/>
        </h4>
    </div>
    <form name="newCompanyForm" role="form" novalidate ng-submit="createCompany();">
        <div class="modal-body">
            <fieldset class="form">
                <div class="form-group col-md-12">
                    <label class="control-label"><spring:message code="companies.name"/></label>
                    <label class="control-label sr-only">* <spring:message code="companies.name"/>:</label>
                    <input type="text"
                           class="form-control"
                           required
                           autofocus
                           ng-model="newCompany.name"
                           name="name"
                           placeholder="<spring:message code='companies.name'/>"/>
                </div>
                <div class="col-md-12">
                    <label class="control-label"><spring:message code="contact"/></label>
                </div>
                <div class="col-md-6">
                    <label class="control-label sr-only">* <spring:message code="address"/>:</label>
                    <input type="text"
                           class="form-control"
                           required
                           autofocus
                           ng-model="newCompany.address"
                           name="name"
                           placeholder="<spring:message code='address'/>"/>
                    <label class="control-label sr-only">* <spring:message code="city"/>:</label>
                    <input type="text"
                           class="form-control"
                           required
                           autofocus
                           ng-model="newCompany.city"
                           name="name"
                           placeholder="<spring:message code='city'/>"/>
                    <label class="control-label sr-only">* <spring:message code="country"/>:</label>
                    <input type="text"
                           class="form-control"
                           required
                           autofocus
                           ng-model="newCompany.country"
                           name="name"
                           placeholder="<spring:message code='country'/>"/>
                </div>
                <div class="col-md-6">
                    <label class="control-label sr-only">* <spring:message code="zipCode"/>:</label>
                    <input type="text"
                           class="form-control"
                           required
                           autofocus
                           ng-model="newCompany.zipCode"
                           name="name"
                           placeholder="<spring:message code='zipCode'/>"/>
                    <label class="control-label sr-only">* <spring:message code="phoneNumber"/>:</label>
                    <input type="text"
                           class="form-control"
                           required
                           autofocus
                           ng-model="newCompany.phoneNumber"
                           name="name"
                           placeholder="<spring:message code='phoneNumber'/>"/>
                    <label class="control-label sr-only">* <spring:message code="companies.website"/>:</label>
                    <input type="text"
                           class="form-control"
                           required
                           autofocus
                           ng-model="newCompany.website"
                           name="name"
                           placeholder="<spring:message code='companies.website'/>"/>
                </div>
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
            <div class="form-group col-md-6">
                <label>Sectors </label>
                <tags-input ng-model="newCompany.sectorList"
                            display-property="name">
                    <auto-complete source="loadSectors($query)"></auto-complete>
                </tags-input>
            </div>
            <div class="form-group col-md-6">
                <label>Departments </label>
                <tags-input ng-model="newCompany.departmentList"
                            display-property="name">
                </tags-input>
            </div>
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
                   ng-disabled="newCompanyForm.$invalid"
                   value='<spring:message code="create"/>'/>
            <span class="alert alert-danger" ng-show="!createCompanySuccess">
                <spring:message code="request.error"/>
            </span>
        </div>
    </form>
</script>

<script type="text/ng-template" id="updateCompanyModal">
    <div class="modal-header">
        <button type="button"
                class="close"
                ng-click="cancel()">
            <span aria-hidden="true">&times;</span>
            <span class="sr-only">Close</span>
        </button>
        <h4 class="modal-title" id="updateCompanyModalLabel">
            <spring:message code="update"/>&nbsp;<spring:message code="company"/>
        </h4>
    </div>
    <form name="updateCompanyForm" novalidate ng-submit="updateCompany(newCompany);">
    <div class="modal-body">
            <div class="form-group">
                <input type="hidden"
                       required
                       ng-model="company.id"
                       name="id"/>
                <label>* <spring:message code="companies.name"/>:</label>
                <input type="text"
                       class="form-control"
                       autofocus
                       required
                       ng-model="newCompany.name"
                       name="name"
                       placeholder="<spring:message code='company'/>&nbsp;<spring:message code='companies.name'/> "/>
                <label>
                    <span class="alert alert-danger"
                          ng-show="displayValidationError && updateCompanyForm.name.$error.required">
                        <spring:message code="required"/>
                    </span>
                </label>
            </div>
            <div class="form-group">
                <label>* <spring:message code="companies.note"/>:</label>
                <input type="text"
                       class="form-control"
                       required
                       ng-model="newCompany.note"
                       name="note"
                       placeholder="<spring:message code='sample.description'/> "/>
                <label>
                    <span class="alert alert-danger"
                          ng-show="displayValidationError && updateCompanyForm.email.$error.required">
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

<script type="text/ng-template" id="deleteCompanyModal">
    <div class="modal-header">
        <button type="button" class="close"
                ng-click="cancel()"
                data-dismiss="modal">
            <span aria-hidden="true">&times;</span>
            <span class="sr-only">Close</span>
        </button>
        <h4 id="deleteCompanyModalLabel" class="modal-title">
            <spring:message code="delete"/>&nbsp;<spring:message code="company"/>
        </h4>
    </div>
    <div class="modal-body">
        <form name="deleteCompanyForm" novalidate ng-submit="removeRow(company)">
            <p><spring:message code="delete.confirm"/>:&nbsp;{{company.name}}?</p>
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