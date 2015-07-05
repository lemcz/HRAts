(function(angular) {

    var hratsApp = angular.module('HRAts', ['ui.bootstrap', 'smart-table', 'ngTagsInput', 'ui.select', 'ngSanitize']);

    hratsApp.controller('LocationController', function($scope, $location){
        if($location.$$absUrl.lastIndexOf('/candidates') > 0){
            $scope.activeURL = 'candidates';
        } else if($location.$$absUrl.lastIndexOf('/vacancies') > 0){
            $scope.activeURL = 'vacancies';
        } else if($location.$$absUrl.lastIndexOf('/companies') > 0){
            $scope.activeURL = 'companies';
        } else if($location.$$absUrl.lastIndexOf('/contacts') > 0){
            $scope.activeURL = 'contacts';
        } else if($location.$$absUrl.lastIndexOf('/candidates') > 0){
            $scope.activeURL = 'candidates';
        } else if($location.$$absUrl.lastIndexOf('/activities') > 0){
            $scope.activeURL = 'activities';
        }
        else{
            $scope.activeURL = 'home';
        }
    })
})(angular);