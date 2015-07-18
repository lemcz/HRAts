(function(angular) {
    var hratsApp = angular.module('HRAts');

    hratsApp.service('SectorService', function ($http){

    var baseUrl = 'http://localhost:8080/HRAts/protected/sectors';
    this.fetchAll = function() {
        return $http.get(baseUrl);
    };

    this.fetchAllByName = function() {
        return $http.get(baseUrl+'/byName');
    };
});})(angular);