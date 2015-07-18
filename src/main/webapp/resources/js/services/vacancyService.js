
(function(angular) {
    var hratsApp = angular.module('HRAts');


    hratsApp.service('VacancyService', function ($http) {

        var baseUrl = 'http://localhost:8080/HRAts/protected/vacancies/';

        this.paginationOptions = function () {
            return [10, 15, 25, 50, 100];
        };

        this.fetchAll = function () {
            return $http.get(baseUrl);
        };

        this.fetchById = function (vacancyId) {
            return $http.get(baseUrl + vacancyId)
        };

        this.createRow = function (vacancyData) {
            return $http.post(baseUrl, vacancyData);
        };

        this.updateRow = function (vacancyData) {
            return $http.put(baseUrl + vacancyData.id, vacancyData);
        };

        this.removeRow = function (vacancyId) {
            return $http.delete(vacancyId);
        };

    });
})(angular);