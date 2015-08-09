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
            <fieldset class="form">
                <div class="col-md-12">
                    <label class="control-label"><spring:message code="user.name"/></label>
                </div>
                <div class="col-md-4">
                    <label class="control-label sr-only">* <spring:message code="user.name"/>:</label>
                    <input type="text"
                           class="form-control"
                           required
                           autofocus
                           ng-model="newCandidate.name"
                           name="name"
                           placeholder="<spring:message code='user.name'/>"/>
                </div>
                <div class="col-md-4">
                    <label class="control-label sr-only">* <spring:message code="user.middleName"/>:</label>
                    <input type="text"
                           class="form-control"
                           required
                           autofocus
                           ng-model="newCandidate.middleName"
                           name="name"
                           placeholder="<spring:message code='user.middleName'/>"/>
                </div>
                <div class="col-md-4">
                    <label class="control-label sr-only">* <spring:message code="user.lastName"/>:</label>
                    <input type="text"
                           class="form-control"
                           required
                           autofocus
                           ng-model="newCandidate.lastName"
                           name="name"
                           placeholder="<spring:message code='user.lastName'/>"/>
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
                           ng-model="newCandidate.candidateInformation.address"
                           name="name"
                           placeholder="<spring:message code='address'/>"/>
                    <label class="control-label sr-only">* <spring:message code="city"/>:</label>
                    <input type="text"
                           class="form-control"
                           required
                           autofocus
                           ng-model="newCandidate.candidateInformation.city"
                           name="name"
                           placeholder="<spring:message code='city'/>"/>
                    <label class="control-label sr-only">* <spring:message code="country"/>:</label>
                    <input type="text"
                           class="form-control"
                           required
                           autofocus
                           ng-model="newCandidate.candidateInformation.country"
                           name="name"
                           placeholder="<spring:message code='country'/>"/>
                </div>
                <div class="col-md-6">
                    <label class="control-label sr-only">* <spring:message code="zipCode"/>:</label>
                    <input type="text"
                           class="form-control"
                           required
                           autofocus
                           ng-model="newCandidate.candidateInformation.zipCode"
                           name="name"
                           placeholder="<spring:message code='zipCode'/>"/>
                    <label class="control-label sr-only">* <spring:message code="phoneNumber"/>:</label>
                    <input type="text"
                           class="form-control"
                           required
                           autofocus
                           ng-model="newCandidate.phoneNumber"
                           name="name"
                           placeholder="<spring:message code='phoneNumber'/>"/>
                    <label class="control-label sr-only">* <spring:message code="email"/>:</label>
                    <input type="email"
                           class="form-control"
                           required
                           autofocus
                           ng-model="newCandidate.email"
                           name="email"
                           placeholder="<spring:message code='sample.email'/>"/>
                </div>
                <div class="form-group col-sm-12 col-md-12">
                    <label class="control-label"><spring:message code="note"/></label>
                    <textarea rows="4"
                              cols="50"
                              class="form-control"
                              required
                              ng-model="newCandidate.note"
                              name="note"
                              placeholder="<spring:message code='note'/>"></textarea>
                </div>
                <label class="control-label col-md-12">Job Preferences Info</label>
                <div class="col-md-6">
                    <label class="control-label sr-only">* <spring:message code="financialReqNetto"/>:</label>
                    <input type="number"
                           class="form-control"
                           required
                           autofocus
                           ng-model="newCandidate.candidateInformation.financialReqNetto"
                           name="financialReqNetto"
                           placeholder="<spring:message code='sample.financialReqNetto'/>"/>

                    <label class="control-label sr-only">* <spring:message code="financialReqBrutto"/>:</label>
                    <input type="number"
                           class="form-control"
                           required
                           autofocus
                           ng-model="newCandidate.candidateInformation.financialReqBrutto"
                           name="financialReqBrutto"
                           placeholder="<spring:message code='sample.financialReqBrutto'/>"/>
                </div>
                <div class="col-md-6">
                    <label class="control-label sr-only">* <spring:message code="sample.startDate"/>:</label>
                    <input type="date"
                           class="form-control"
                           required
                           autofocus
                           ng-model="newCandidate.candidateInformation.startDate"
                           name="startDate"
                           placeholder="<spring:message code='sample.startDate'/>"/>
                </div>
                <div class="col-md-6">
                    <ui-select ng-model="querySelection.selectedContractType"
                               reset-search-input="false"
                               ng-disabled="disabled">
                        <ui-select-match placeholder="Select contract type">{{$select.selected.name}}</ui-select-match>
                        <ui-select-choices group-by="someGroupFn" repeat="contract in contractsCollection | propsFilter: {name: $select.search, id: $select.search}">
                            <div ng-bind-html="contract.name | highlight: $select.search"></div>
                            <small>
                                id: {{contract.id}}
                            </small>
                        </ui-select-choices>
                    </ui-select>
                </div>
                <label class="control-label col-md-12"><spring:message code="assign.relations"/></label>
                <div class="col-md-6">
                    <ui-select multiple
                               on-select="fetchRelatedDepartments(querySelection.selectedCompanies)"
                               on-remove="fetchRelatedDepartments(querySelection.selectedCompanies)"
                               ng-model="querySelection.selectedCompanies"
                               theme="bootstrap" ng-disabled="disabled">
                        <ui-select-match placeholder="Select companies">{{$item.name}}</ui-select-match>
                        <ui-select-choices group-by="someGroupFn" repeat="company in companiesCollection | propsFilter: {name: $select.search, id: $select.search}">
                            <div ng-bind-html="company.name | highlight: $select.search"></div>
                            <small>
                                id: {{company.id}}
                                name: <span ng-bind-html="''+company.name | highlight: $select.search"></span>
                            </small>
                        </ui-select-choices>
                    </ui-select>
                </div>
                <div class="col-md-6">
                    <ui-select multiple
                               on-select="fetchRelatedVacancies(querySelection.selectedDepartments)"
                               on-remove="fetchRelatedVacancies(querySelection.selectedDepartments)"
                               ng-model="querySelection.selectedDepartments"
                               theme="bootstrap" ng-disabled="disabled">
                        <ui-select-match placeholder="Select departments">{{$item.name}}</ui-select-match>
                        <ui-select-choices group-by="someGroupFn" repeat="department in departmentsCollection | propsFilter: {name: $select.search, id: $select.search}">
                            <div ng-bind-html="department.name | highlight: $select.search"></div>
                            <small>
                                id: {{department.id}}
                            </small>
                        </ui-select-choices>
                    </ui-select>
                </div>
                <div class="col-md-12">
                    <ui-select multiple
                               ng-model="querySelection.selectedVacancies"
                               ng-disabled="disabled">
                        <ui-select-match placeholder="Select vacancies">{{$item.name}}</ui-select-match>
                        <ui-select-choices group-by="someGroupFn" repeat="vacancy in vacancyCollection | propsFilter: {name: $select.search, id: $select.search}">
                            <div ng-bind-html="vacancy.name | highlight: $select.search"></div>
                            <small>
                                id: {{vacancy.id}}
                                department: {{vacancy.name}}
                                company: {{vacancy.department.company.name}}
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
                <label>* <spring:message code="note"/>:</label>
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