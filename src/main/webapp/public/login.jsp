<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="row-fluid">
    <div class="jumbotron">
        <h1><spring:message code='project.name'/></h1>
    </div>
</div>
<div class="row">
    <div class="col-md-4 col-md-offset-4 well" ng-controller="loginController">
        <legend><spring:message code="login.header" /></legend>
        <div class="alert alert-danger" ng-class="{'': displayLoginError == true, 'none': displayLoginError == false}">
            <spring:message code="login.error" />
        </div>
        <form method="post" action="j_spring_security_check">
            <div>
                <input name="j_username" id="j_username" type="text" class="col-md-12 form-control" placeholder="<spring:message code='sample.email' /> " required autofocus><br/>
                <input name="j_password" id="j_password" type="password" class="col-md-12 form-control" placeholder="Password" required><br/>
                <div class="checkbox">
                    <label>
                        <input type="checkbox" value="remember-me"> Remember me
                    </label>
                </div>
                <button type="submit" name="submit" class="btn btn-lg btn-primary btn-block"><spring:message code="login.signIn" /></button>
            </div>
        </form>
    </div>
</div>
<script src="<c:url value='/resources/js/pages/login.js' />"></script>