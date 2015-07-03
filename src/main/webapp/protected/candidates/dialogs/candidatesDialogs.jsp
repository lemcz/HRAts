<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="addCandidatesModal"
     class="modal fade"
     role="dialog"
     aria-labelledby="addCandidatesModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal">
                    <span aria-hidden="true">&times;</span>
                    <span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title" id="addCandidatesModalLabel">
                    <spring:message code="create"/>&nbsp;<spring:message code="candidate"/>
                </h4>
            </div>
            <form name="newCandidateForm" role="form" novalidate ng-submit="createCandidate(newCandidateForm);">
            <div class="modal-body">
                        <div class="form-group">
                            <label>* <spring:message code="candidates.name"/>:</label>
                            <input type="text"
                                   class="form-control"
                                   required
                                   autofocus
                                   ng-model="candidate.name"
                                   name="name"
                                   placeholder="<spring:message code='candidate'/>&nbsp;<spring:message code='candidates.name'/>"/>
                            <label>
                                <span class="alert alert-danger"
                                      ng-show="displayValidationError && newCandidateForm.name.$error.required">
                                        <spring:message code="required"/>
                                </span>
                            </label>
                        </div>
                        <div class="form-group">
                            <label>* <spring:message code="candidates.email"/>:</label>
                            <input type="email"
                                   class="form-control"
                                   required
                                   ng-model="candidate.email"
                                   name="email"
                                   placeholder="<spring:message code='sample.email'/> "/>
                            <label>
                                    <span class="alert alert-danger"
                                          ng-show="displayValidationError && newCandidateForm.email.$error.required">
                                        <spring:message code="required"/>
                                    </span>
                            </label>
                        </div>
                        <div class="form-group">
                            <label>* <spring:message code="candidates.phone"/>:</label>
                            <input type="text"
                                   class="form-control"
                                   required
                                   ng-model="candidate.phoneNumber"
                                   name="phoneNumber"
                                   placeholder="<spring:message code='sample.phone'/> "/>
                            <label>
                                <span class="alert alert-danger"
                                      ng-show="displayValidationError && newCandidateForm.phoneNumber.$error.required">
                                    <spring:message code="required"/>
                                </span>
                            </label>
                        </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default"
                        type="button"
                        data-dismiss="modal"
                        ng-click="exit('#addCandidatesModal');"
                        aria-hidden="true">
                    <spring:message code="cancel"/>
                </button>
                <input type="submit"
                       class="btn btn-primary"
                       value='<spring:message code="create"/>'/>
            <span class="alert alert-danger dialogErrorMessage"
                  ng-show="errorOnSubmit">
                <spring:message code="request.error"/>
            </span>
            </div>
            </form>
        </div>

    </div>
</div>

<div id="updateCandidatesModal"
     class="modal fade"
     aria-labelledby="updateCandidatesModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal">
                    <span aria-hidden="true">&times;</span>
                    <span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title" id="updateCandidatesModalLabel">
                    <spring:message code="update"/>&nbsp;<spring:message code="candidate"/>
                </h4>
            </div>
            <form name="updateCandidateForm" novalidate ng-submit="updateCandidate(updateCandidateForm);">
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
                               ng-model="candidate.name"
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
                        <label>* <spring:message code="candidates.email"/>:</label>
                        <input type="email"
                               class="form-control"
                               required
                               ng-model="candidate.email"
                               name="email"
                               placeholder="<spring:message code='sample.email'/> "/>
                        <label>
                            <span class="alert alert-danger"
                                  ng-show="displayValidationError && updateCandidateForm.email.$error.required">
                                <spring:message code="required"/>
                            </span>
                        </label>
                    </div>
                    <div class="form-group">
                        <label>* <spring:message code="candidates.phone"/>:</label>
                        <input type="text"
                               class="form-control"
                               required
                               ng-model="candidate.phoneNumber"
                               name="phoneNumber"
                               placeholder="<spring:message code='sample.phone'/> "/>
                        <label>
                            <span class="alert alert-danger"
                                  ng-show="displayValidationError && updateCandidateForm.phoneNumber.$error.required">
                                <spring:message code="required"/>
                            </span>
                        </label>
                    </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default"
                        type="button"
                        data-dismiss="modal"
                        ng-click="exit('#updateCandidatesModal');"
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
        </div>
    </div>
</div>

<div id="deleteCandidatesModal"
     class="modal fade"
     aria-labelledby="deleteCandidatesModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal">
                    <span aria-hidden="true">&times;</span>
                    <span class="sr-only">Close</span>
                </button>
                <h4 id="deleteCandidatesModalLabel" class="modal-title">
                    <spring:message code="delete"/>&nbsp;<spring:message code="candidate"/>
                </h4>
            </div>
            <div class="modal-body">
                <form name="deleteCandidateForm" novalidate ng-submit="deleteCandidateForm.$valid && deleteCandidate();">
                    <p><spring:message code="delete.confirm"/>:&nbsp;{{candidate.name}}?</p>
                    <button class="btn btn-default"
                            type="button"
                            data-dismiss="modal"
                            ng-click="exit('#deleteCandidatesModal');"
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
        </div>
    </div>
</div>

<div id="searchCandidatesModal"
     class="modal fade"
     aria-labelledby="searchCandidatesModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal">
                    <span aria-hidden="true">&times;</span>
                    <span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title" id="searchCandidatesModalLabel">
                    <spring:message code="search"/>
                </h4>
            </div>
            <form name="searchCandidateForm" novalidate ng-submit="searchCandidate(searchCandidateForm, false);">
            <div class="modal-body">
                    <label><spring:message code="search.for"/></label>
                    <div class="form-group">
                            <input type="text"
                                   class="form-control"
                                   autofocus
                                   required
                                   ng-model="searchFor"
                                   name="searchFor"
                                   placeholder="<spring:message code='candidate'/>&nbsp;<spring:message code='candidates.name'/> "/>
                        <div class="displayInLine">
                            <label class="displayInLine">
                                <span class="alert alert-danger"
                                      ng-show="displayValidationError && searchCandidateForm.searchFor.$error.required">
                                    <spring:message code="required"/>
                                </span>
                            </label>
                        </div>
                    </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default"
                        type="button"
                        data-dismiss="modal"
                        ng-click="exit('#searchCandidatesModal');"
                        aria-hidden="true">
                    <spring:message code="cancel"/>
                </button>
                <input type="submit"
                       class="btn btn-primary"
                       value='<spring:message code="search"/>'
                        />
                <span class="alert alert-danger dialogErrorMessage"
                      ng-show="errorOnSubmit">
                    <spring:message code="request.error"/>
                </span>
            </div>
            </form>

        </div>
    </div>
</div>