<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="addContactsModal"
     class="modal fade"
     role="dialog"
     aria-labelledby="addContactsModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal">
                    <span aria-hidden="true">&times;</span>
                    <span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title" id="addContactsModalLabel">
                    <spring:message code="create"/>&nbsp;<spring:message code="contact"/>
                </h4>
            </div>
            <div class="modal-body">
                <form name="newContactForm" role="form" novalidate>
                        <div class="form-group">
                            <label>* <spring:message code="contacts.name"/>:</label>
                            <input type="text"
                                   class="form-control"
                                   required
                                   autofocus
                                   ng-model="contact.name"
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
                            <label>* <spring:message code="contacts.email"/>:</label>
                            <input type="text"
                                   class="form-control"
                                   required
                                   ng-model="contact.email"
                                   name="email"
                                   placeholder="<spring:message code='sample.email'/> "/>
                            <label>
                                    <span class="alert alert-danger"
                                          ng-show="displayValidationError && newContactForm.email.$error.required">
                                        <spring:message code="required"/>
                                    </span>
                            </label>
                        </div>
                        <div class="form-group">
                            <label>* <spring:message code="contacts.phone"/>:</label>
                            <input type="text"
                                   class="form-control"
                                   required
                                   ng-model="contact.phoneNumber"
                                   name="phoneNumber"
                                   placeholder="<spring:message code='sample.phone'/> "/>
                            <label>
                                <span class="alert alert-danger"
                                      ng-show="displayValidationError && newContactForm.phoneNumber.$error.required">
                                    <spring:message code="required"/>
                                </span>
                            </label>
                        </div>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default"
                        data-dismiss="modal"
                        ng-click="exit('#addContactsModal');"
                        aria-hidden="true">
                    <spring:message code="cancel"/>
                </button>
                <input type="submit"
                       class="btn btn-primary"
                       ng-click="createContact(newContactForm);"
                       value='<spring:message code="create"/>'/>
            <span class="alert alert-danger dialogErrorMessage"
                  ng-show="errorOnSubmit">
                <spring:message code="request.error"/>
            </span>
            </div>
        </div>
    </div>
</div>

<div id="updateContactsModal"
     class="modal fade"
     aria-labelledby="updateContactsModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal">
                    <span aria-hidden="true">&times;</span>
                    <span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title" id="updateContactsModalLabel">
                    <spring:message code="update"/>&nbsp;<spring:message code="contact"/>
                </h4>
            </div>
            <div class="modal-body">
                <form name="updateContactForm" novalidate>
                    <div class="form-group">
                        <input type="hidden"
                               required
                               ng-model="contact.id"
                               name="id"
                               value="{{contact.id}}"/>
                        <label>* <spring:message code="contacts.name"/>:</label>
                        <input type="text"
                               class="form-control"
                               autofocus
                               required
                               ng-model="contact.name"
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
                        <label>* <spring:message code="contacts.email"/>:</label>
                        <input type="email"
                               class="form-control"
                               required
                               ng-model="contact.email"
                               name="email"
                               placeholder="<spring:message code='sample.email'/> "/>
                        <label>
                            <span class="alert alert-danger"
                                  ng-show="displayValidationError && updateContactForm.email.$error.required">
                                <spring:message code="required"/>
                            </span>
                        </label>
                    </div>
                    <div class="form-group">
                        <label>* <spring:message code="contacts.phone"/>:</label>
                        <input type="text"
                               class="form-control"
                               required
                               ng-model="contact.phoneNumber"
                               name="phoneNumber"
                               placeholder="<spring:message code='sample.phone'/> "/>
                        <label>
                            <span class="alert alert-danger"
                                  ng-show="displayValidationError && updateContactForm.phoneNumber.$error.required">
                                <spring:message code="required"/>
                            </span>
                        </label>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default"
                        data-dismiss="modal"
                        ng-click="exit('#updateContactsModal');"
                        aria-hidden="true">
                    <spring:message code="cancel"/></button>
                <input type="submit"
                   class="btn btn-primary"
                   ng-click="updateContact(updateContactForm);"
                   value='<spring:message code="update"/>'/>
                <span class="alert alert-danger dialogErrorMessage"
                      ng-show="errorOnSubmit">
                    <spring:message code="request.error"/>
                </span>
            </div>
        </div>
    </div>
</div>

<div id="deleteContactsModal"
     class="modal fade"
     aria-labelledby="searchContactsModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal">
                    <span aria-hidden="true">&times;</span>
                    <span class="sr-only">Close</span>
                </button>
                <h4 id="deleteContactsModalLabel" class="modal-title">
                    <spring:message code="delete"/>&nbsp;<spring:message code="contact"/>
                </h4>
            </div>
            <div class="modal-body">
                <form name="deleteContactForm" novalidate>
                    <p><spring:message code="delete.confirm"/>:&nbsp;{{contact.name}}?</p>
                    <button class="btn btn-default"
                            data-dismiss="modal"
                            ng-click="exit('#deleteContactsModal');"
                            aria-hidden="true">
                        <spring:message code="cancel"/>
                    </button>
                    <input type="submit"
                           class="btn btn-danger"
                           ng-click="deleteContact();"
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

<div id="searchContactsModal"
     class="modal fade"
     aria-labelledby="searchContactsModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal">
                    <span aria-hidden="true">&times;</span>
                    <span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title" id="searchContactsModalLabel">
                    <spring:message code="search"/>
                </h4>
            </div>
            <div class="modal-body">
                <form name="searchContactForm" novalidate>
                    <label><spring:message code="search.for"/></label>
                    <div class="form-group">
                            <input type="text"
                                   class="form-control"
                                   autofocus
                                   required
                                   ng-model="searchFor"
                                   name="searchFor"
                                   placeholder="<spring:message code='contact'/>&nbsp;<spring:message code='contacts.name'/> "/>
                        <div class="displayInLine">
                            <label class="displayInLine">
                                <span class="alert alert-danger"
                                      ng-show="displayValidationError && searchContactForm.searchFor.$error.required">
                                    <spring:message code="required"/>
                                </span>
                            </label>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default"
                        data-dismiss="modal"
                        ng-click="exit('#searchContactsModal');"
                        aria-hidden="true">
                    <spring:message code="cancel"/>
                </button>
                <input type="submit"
                       class="btn btn-primary"
                       ng-click="searchContact(searchContactForm, false);"
                       value='<spring:message code="search"/>'
                        />
                <span class="alert alert-danger dialogErrorMessage"
                      ng-show="errorOnSubmit">
                    <spring:message code="request.error"/>
                </span>
            </div>
        </div>
    </div>
</div>