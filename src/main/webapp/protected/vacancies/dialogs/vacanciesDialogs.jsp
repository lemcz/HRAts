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
    <form name="newVacancyForm" class="form" role="form" novalidate ng-submit="createVacancy(files);">
        <div class="modal-body">
            <fieldset class="form">
                <div class="form-group col-md-12">
                    <label><spring:message code="vacancies.name"/></label>
                    <input type="text"
                           class="form-control"
                           required
                           autofocus
                           ng-model="newVacancy.name"
                           name="name"
                           placeholder="<spring:message code='vacancy'/>&nbsp;<spring:message code='vacancies.name'/>"/>
                </div>
                <div class="form-group col-md-12">
                    <label><spring:message code="vacancies.description"/></label>
                    <div text-angular ng-model="newVacancy.description"></div>
                </div>
                <div class="form-group col-md-12">
                    <label class="control-label"><spring:message code="note"/></label>
                    <textarea rows="4"
                              cols="50"
                              class="form-control"
                              required
                              ng-model="newVacancy.note"
                              name="note"
                              placeholder="<spring:message code='note'/>"></textarea>
                </div>
                <div class="col-md-12">
                    <label>Assign relations</label>
                </div>
                <div class="form-group col-md-6">
                    <ui-select ng-model="newVacancy.company"
                               on-select="fetchRelatedDepartments([newVacancy.company])"
                               theme="bootstrap"

                               reset-search-input="false">
                        <ui-select-match placeholder="Select a company">{{$select.selected.name}}</ui-select-match>
                        <ui-select-choices repeat="company in companiesCollection | propsFilter: {id: $select.search, name: $select.search}">
                            <div ng-bind-html="company.name | highlight: $select.search"></div>
                            <small>
                                id: <span ng-bind-html="''+company.id | highlight: $select.search"></span>
                            </small>
                        </ui-select-choices>
                    </ui-select>
                </div>
                <div class="form-group col-md-6">
                    <ui-select ng-model="newVacancy.department"
                               theme="bootstrap"
                               reset-search-input="false">
                        <ui-select-match placeholder="Select a department">{{$select.selected.name}}</ui-select-match>
                        <ui-select-choices repeat="department in departmentList | propsFilter: {id: $select.search, name: $select.search}">
                            <div ng-bind-html="department.name | highlight: $select.search"></div>
                            <small>
                                id: <span ng-bind-html="''+department.id | highlight: $select.search"></span>
                                manager: {{department.manager.name}}
                            </small>
                        </ui-select-choices>
                    </ui-select>
                </div>
                <div class="form-group">
                    <div class=" col-md-6">
                        <label><spring:message code="vacancies.numberOfVacancies"/></label>
                        <input type="number"
                               class="form-control"
                               required
                               ng-model="newVacancy.numberOfVacancies"
                               ng-pattern="/^[1-9][0-9]*$/"
                               name="numberOfVacancies"
                               placeholder="<spring:message code='sample.number'/> "/>
                    </div>
                    <div class=" col-md-6">
                        <label><spring:message code="vacancies.salary"/></label>
                        <input type="number"
                               class="form-control"
                               ng-model="newVacancy.salary"
                               name="salary"
                               placeholder="<spring:message code='sample.number'/> "/>
                    </div>
                </div>
                <div class="col-md-6">
                    <label class="control-label"><spring:message code="startDate"/></label>
                    <input type="date"
                           class="form-control"
                           required
                           autofocus
                           ng-model="newVacancy.startDate"
                           name="startDate"/>
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
                   ng-disabled="newVacancyForm.$invalid"
                   value='<spring:message code="create"/>'/>
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
                <label><spring:message code="vacancies.name"/>:</label>
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
                <label><spring:message code="vacancies.description"/></label>
                <input type="text"
                       class="form-control"
                       required
                       ng-model="newVacancy.description"
                       name="description"
                       placeholder="<spring:message code='sample.description'/> "/>
                <label>
                    <span class="alert alert-danger"
                          ng-show="displayValidationError && updateVacancyForm.description.$error.required">
                        <spring:message code="required"/>
                    </span>
                </label>
            </div>
            <div class="form-group">
                <label><spring:message code="vacancies.numberOfVacancies"/>:</label>
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

<script type="text/ng-template" id="addToVacancyModal">
    <div class="modal-header">
        <button type="button" class="close"
                data-dismiss="modal"
                ng-click="cancel()">
            <span aria-hidden="true">&times;</span>
            <span class="sr-only">Close</span>
        </button>
        <h4 class="modal-title" id="addToVacancyModalLabel">
            <spring:message code="addToVacancy"/>
        </h4>
    </div>
    <form name="addToVacancyForm" role="form" novalidate ng-submit="addToVacancy(vacancy);">
        <div class="modal-body">
            <fieldset class="form">
                <div class="form-group">
                    <input type="hidden"
                           required
                           ng-model="vacancy.id"
                           name="id"
                           value="{{vacancy.id}}"/>
                    <label class="control-label col-md-12"><spring:message code="assign.relations"/></label>
                    <div class="col-md-12">
                        <ui-select multiple
                                   ng-model="querySelection.selectedCandidates"
                                   theme="bootstrap" >
                            <ui-select-match placeholder="Select candidates">{{$item.name}}</ui-select-match>
                            <ui-select-choices group-by="someGroupFn" repeat="candidate in candidatesCollection | propsFilter: {name: $select.search, id: $select.search, email: $select.search}">
                                <div ng-bind-html="candidate.name | highlight: $select.search"></div>
                                <small>
                                    id: {{candidate.id}}
                                    last name: <span ng-bind-html="''+candidate.lastName | highlight: $select.search"></span>
                                    e-mail: {{candidate.email}}
                                </small>
                            </ui-select-choices>
                        </ui-select>
                    </div>
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
                   ng-disabled="addToVacancyForm.$invalid"
                   value='<spring:message code="add"/>'/>
        </div>
    </form>
</script>