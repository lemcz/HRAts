(function(angular) {
    var hratsApp = angular.module('HRAts');


    var baseUrl = 'http://localhost:8080/HRAts';

    hratsApp.service('ActivityTypeService', function ($http) {

        var urlExtension = '/protected/activityTypeLkp/';

        this.fetchAll = function () {
            return $http.get(baseUrl + urlExtension);
        };

        this.fetchById = function (activityId) {
            return $http.get(baseUrl + urlExtension + activityId)
        };

        this.createRow = function (activityData) {
            return $http.post(baseUrl + urlExtension, activityData);
        };

        this.updateRow = function (activityData) {
            return $http.put(baseUrl + urlExtension + activityData.id, activityData);
        };

        this.removeRow = function (activityId) {
            return $http.delete(activityId);
        };
    });

    hratsApp.service('ContractTypeService', function($http) {

        var urlExtension = '/protected/contractTypeLkp';

        this.fetchAll = function () {
            return $http.get(baseUrl + urlExtension);
        };

        this.fetchById = function (contractId) {
            return $http.get(baseUrl + urlExtension + contractId)
        };

        this.createRow = function (contractData) {
            return $http.post(baseUrl + urlExtension, contractData);
        };

        this.updateRow = function (contractData) {
            return $http.put(baseUrl + urlExtension + contractData.id, contractData);
        };

        this.removeRow = function (contractId) {
            return $http.delete(contractId);
        };
    });

    hratsApp.service('StatusTypeService', function($http) {

        var urlExtension = '/protected/statusTypeLkp';

        this.fetchAll = function () {
            return $http.get(baseUrl + urlExtension);
        };

        this.fetchById = function (statusId) {
            return $http.get(baseUrl + urlExtension + statusId)
        };

        this.createRow = function (statusData) {
            return $http.post(baseUrl + urlExtension, statusData);
        };

        this.updateRow = function (statusData) {
            return $http.put(baseUrl + urlExtension + statusData.id, statusData);
        };

        this.removeRow = function (statusId) {
            return $http.delete(statusId);
        };
    });

    hratsApp.service('StatusService', function($http) {

        var urlExtension = '/protected/statusType';

        this.fetchAll = function () {

            return $http.get(baseUrl + urlExtension);
        };

        this.fetchById = function (statusId) {
            return $http.get(baseUrl + urlExtension + statusId)
        };

        this.createRow = function (statusData) {
            return $http.post(baseUrl + urlExtension, statusData);
        };

        this.updateRow = function (statusData) {
            return $http.put(baseUrl + urlExtension + statusData.id, statusData);
        };

        this.removeRow = function (statusId) {
            return $http.delete(statusId);
        };
    });
})(angular);