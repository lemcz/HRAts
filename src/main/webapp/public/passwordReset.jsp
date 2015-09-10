<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<!DOCTYPE html>
<html lang="en-EN">
    <head>
        <tiles:insertAttribute name="scriptBundle"/>
    </head>
    <body ng-app="HRAts">
    <div class="container">
        <div class="row-fluid">
            <div class="jumbotron">
                <h1><spring:message code='project.name'/></h1>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4 col-md-offset-4 well" ng-controller="ResetController" id="reset">
                <legend><spring:message code="reset.header"/>&nbsp;<spring:message code="user.password"/></legend>
                <form name="resetPasswordForm"
                      ng-hide="tokenIdAvailable"
                      role="form"
                      novalidate
                      autocomplete="off"
                      ng-submit="sendResetToken(user);">
                    <fieldset class="form">
                        <div class="form-group">
                            <label class="control-label"><spring:message code="email"/></label>
                            <input type="email"
                                   class="form-control"
                                   required
                                   ng-model="user.email"
                                   name="email"
                                   placeholder="<spring:message code='sample.email'/>"/>
                        </div>
                        <button type="submit" name="submit" class="btn btn-lg btn-primary btn-block" ng-disabled="resetPasswordForm.$invalid"><spring:message code="message.send.mail" /></button>
                    </fieldset>
                </form>
                <form name="changePasswordForm"
                      role="form"
                      novalidate
                      autocomplete="off"
                      ng-show="tokenIdAvailable"
                      ng-submit="changePassword(user);">
                    <div class="form-group">
                        <label class="control-label"><spring:message code="user.password"/></label>
                        <input type="password"
                               class="form-control"
                               required
                               ng-model="user.password"
                               name="password"
                               placeholder="<spring:message code='user.password'/>"/>
                        <label class="control-label"><spring:message code="user.repeatPassword"/></label>
                        <input type="password"
                               class="form-control"
                               required
                               ng-model="user.repeatPassword"
                               name="password"
                               placeholder="<spring:message code='user.repeatPassword'/>"/>
                    </div>
                    <button type="submit" name="submit" class="btn btn-lg btn-primary btn-block" ng-disabled="changePasswordForm.$invalid"><spring:message code="user.changePassword" /></button>
                </form>
                <a href="<c:url value='/login'/>">Sign in</a>
            </div>
        </div>
    </div>
    <script src="<c:url value='/resources/js/pages/login.js' />"></script>
    </body>
</html>