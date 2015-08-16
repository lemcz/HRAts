(function(angular) {
    var hratsApp = angular.module('HRAts');

    var baseUrl = 'http://localhost:8080/HRAts';

    hratsApp.service('VacancyUserService', function ($http) {

        var urlExtension = '/protected/vacancyUser/';

        this.fetchAll = function () {
            return $http.get(baseUrl + urlExtension);
        };

        this.fetchById = function (activityId) {
            return $http.get(baseUrl + urlExtension + activityId)
        };

        this.createRow = function (activityData) {
            return $http.post(baseUrl + urlExtension, activityData);
        };

        this.removeRow = function (activityId) {
            return $http.delete(activityId);
        };
    });
})(angular);