(function(angular) {
    var hratsApp = angular.module('HRAts');


    hratsApp.service('VacancyUserService', function ($http) {

        var baseUrl = 'http://localhost:8080/HRAts';

        var urlExtension = '/protected/vacancyUser/';

        return {

            fetchAll: function () {
                return $http.get(baseUrl + urlExtension);
            },

            fetchById: function (vacancyUserId) {
                return $http.get(baseUrl + urlExtension + vacancyUserId)
            },

            createRow: function (vacancyUserData) {
                return $http.post(baseUrl + urlExtension, vacancyUserData);
            },

            createMultiple: function (vacancyUserList) {
                return $http.post(baseUrl + urlExtension + 'list', vacancyUserList);
            },

            removeRow: function (vacancyUserId) {
                return $http.delete(vacancyUserId);
            }
        }

    });
})(angular);