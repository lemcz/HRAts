<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
    <div ng-controller="ActivityController" ng-cloak>
        <h1 class="page-header">
            <p class="text-center">
                <spring:message code='activities.header'/>
            </p>
        </h1>
        <div ng-controller="UiGridController" ng-cloak>
            <div class="grid" id="grid1" ui-grid="gridOptions" ui-grid-auto-resize ui-grid-resize-columns ui-grid-pinning ui-grid-selection ui-grid-move-columns ui-grid-exporter ui-grid-grouping></div>
        </div>
    </div>
</div>

<script src="<c:url value="/resources/js/pages/gridConf.js"/>"></script>
<script src="<c:url value="/resources/js/services/activityService.js"/>"></script>
<script src="<c:url value="/resources/js/pages/activities.js"/>"></script>