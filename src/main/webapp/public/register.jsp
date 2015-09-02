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
            <div class="col-md-4 col-md-offset-4 well" ng-controller="RegisterController" id="register" ng-submit="createUser(newUser);">
                <legend><spring:message code="register.header" /></legend>
                <form name="registerForm"
                      role="form"
                      novalidate
                      autocomplete="off">
                    <fieldset class="form">
                        <div class="form-group">
                            <label><spring:message code="firstName"/></label>
                            <input type="text"
                                   class="form-control"
                                   required
                                   autofocus
                                   ng-model="newUser.name"
                                   name="firstName"
                                   placeholder="<spring:message code='firstName'/>"/>
                            <label><spring:message code="lastName"/></label>
                            <input type="text"
                                   name="lastName"
                                   class="form-control"
                                   ng-model="newUser.lastName"
                                   placeholder="<spring:message code='lastName'/>"
                                   required />
                            <label class="control-label"><spring:message code="email"/></label>
                            <input type="email"
                                   class="form-control"
                                   required
                                   ng-model="newUser.email"
                                   name="email"
                                   placeholder="<spring:message code='sample.email'/>"/>
                            <label class="control-label"><spring:message code="password"/></label>
                            <input type="password"
                                   class="form-control"
                                   required
                                   ng-model="newUser.password"
                                   name="password"
                                   placeholder="<spring:message code='password'/>"/>
                            <label class="control-label"><spring:message code="repeatPassword"/></label>
                            <input type="password"
                                   class="form-control"
                                   required
                                   ng-model="newUser.repeatPassword"
                                   name="password"
                                   placeholder="<spring:message code='repeatPassword'/>"/>
                        </div>
                        <button type="submit" name="submit" class="btn btn-lg btn-primary btn-block" ng-disabled="registerForm.$invalid"><spring:message code="register" /></button>
                    </fieldset>
                </form>
                <a href="<c:url value='/login'/>">Sign in</a>
            </div>
        </div>
    </div>
    <script src="<c:url value='/resources/js/pages/register.js' />"></script>
    </body>
</html>