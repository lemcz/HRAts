(function(angular) {
    var hratsApp = angular.module('HRAts');

    hratsApp.service('DepartmentService', function ($http){

        var baseUrl = 'http://localhost:8080/HRAts/protected/departments';
        this.fetchAll = function() {
            return $http.get(baseUrl);
        };

        this.fetchAllByName = function() {
            return $http.get(baseUrl+'/byName');
        };

        this.fetchAllByCompany = function(companyId) {
            return $http.get(baseUrl+'/?companyId='+companyId);
        }
    });
})(angular);