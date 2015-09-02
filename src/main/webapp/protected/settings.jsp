<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
    <div ng-controller="SettingsController" ng-cloak>
        <h1 class="page-header">
            <p class="text-center">
                <spring:message code='settings.header'/>
            </p>
        </h1>
        <h2 class="page-header">
            <p>
                <spring:message code="generalInfo"/>
            </p>
        </h2>
        <div class="col-md-10 col-md-offset-1">
            <form name="updateUsersInfoForm"
                  role="form"
                  novalidate
                  autocomplete="off"
                  ng-submit="updateUsersInfo(userData);">
            <div class="col-md-6">
                <div class="field"><strong>Name</strong>
                    <div click-to-edit="userData.name"></div>
                </div>

                <div class="field"><strong>Middle Name</strong>
                    <div click-to-edit="userData.middleName"></div>
                </div>

                <div class="field"><strong>Last Name</strong>
                    <div click-to-edit="userData.lastName"></div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="field"><strong>Email</strong>
                    <div click-to-edit="userData.email"></div>
                </div>
                <div class="field"><strong>Phone</strong>
                    <div click-to-edit="userData.phoneNumber"></div>
                </div>
            </div>
                <button type="submit" class="col-md-2 col-md-offset-8 btn btn-primary">Update</button>
            </form>
        </div>
        <h2 class="page-header">
            <p>
                <spring:message code="password.change"/>
            </p>
        </h2>
        <div class="col-md-4 col-md-offset-1">
            <form name="changePasswordForm"
                  role="form"
                  novalidate
                  autocomplete="off"
                  ng-submit="changePassword(user);">
                <div class="form-group">
                    <label class="control-label"><spring:message code="currentPassword"/></label>
                    <input type="password"
                           class="form-control"
                           required
                           ng-model="user.oldPassword"
                           name="password"
                           placeholder="<spring:message code='password'/>"/>
                    <label class="control-label"><spring:message code="password"/></label>
                    <input type="password"
                           class="form-control"
                           required
                           ng-model="user.password"
                           name="password"
                           placeholder="<spring:message code='password'/>"/>
                    <label class="control-label"><spring:message code="repeatPassword"/></label>
                    <input type="password"
                           class="form-control"
                           required
                           ng-model="user.repeatPassword"
                           name="password"
                           placeholder="<spring:message code='repeatPassword'/>"/>
                </div>
                <button type="submit" name="submit" class="btn btn-lg btn-primary btn-block" ng-disabled="changePasswordForm.$invalid"><spring:message code="changePassword" /></button>
            </form>
        </div>
    </div>
</div>

<script src="<c:url value="/resources/js/pages/settings.js"/>"></script>