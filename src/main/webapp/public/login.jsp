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
            <div class="col-md-4 col-md-offset-4 well" ng-controller="LoginController" id="login">
                <legend><spring:message code="login.header" /></legend>
                <div class="alert alert-danger" ng-show="displayLoginError">
                    <spring:message code="login.error" />
                </div>
                <form name="loginForm"
                      role="form"
                      method="POST"
                      action="j_spring_security_check"
                      novalidate
                      autocomplete="off">
                    <div class="form-group">
                        <label for="j_username" class="sr-only">Username</label>
                        <input name="j_username"
                               id="j_username"
                               type="text"
                               class="form-control" placeholder="<spring:message code='sample.email'/>"
                               ng-model="user.username"
                               required
                               autofocus/>
                    </div>
                    <div class="form-group">
                        <label for="j_password" class="sr-only">Password</label>
                            <input name="j_password"
                                   id="j_password"
                                   type="password"
                                   class="form-control"
                                   ng-model="user.password"
                                   placeholder="Password"
                                   required/>
                    </div>
                    <button type="submit" name="submit" class="btn btn-lg btn-primary btn-block" ng-disabled="loginForm.$invalid"><spring:message code="login.signIn" /></button>
                </form>
                <a href="<c:url value='/register'/>">Sign up</a> or
                <a href="<c:url value='/passwordReset'/>">reset your password</a>
            </div>
        </div>
    </div>
        <script src="<c:url value='/resources/js/pages/login.js' />"></script>
    </body>
</html>