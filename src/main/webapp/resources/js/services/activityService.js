(function(angular) {
    var hratsApp = angular.module('HRAts');

    hratsApp.service('ActivityService', function ($http){

        var baseUrl = 'http://localhost:8080/HRAts/protected/activities/';

        this.paginationOptions = function(){
            return [10, 15, 25, 50, 100];
        };

        this.fetchAll = function () {
            return $http.get(baseUrl);
        };

        this.fetchById = function(activityId) {
            return $http.get(baseUrl + activityId)
        };

        this.fetchByCandidateId = function(candidateId) {
            return $http.get(baseUrl + candidateId);
        };

        this.createRow = function(activityData){
            return $http.post(baseUrl, activityData);
        };

        this.updateRow = function(activityData) {
            return $http.put(baseUrl+activityData.id, activityData);
        };

        this.removeRow = function(activityId){
            return $http.delete(activityId);
        };

    });
})(angular);