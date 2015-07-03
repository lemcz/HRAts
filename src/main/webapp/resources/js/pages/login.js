(function(angular) {

    var hratsApp = angular.module('HRAts', []);

    hratsApp.controller('LoginController', function($scope, $location){
        var url = "" + $location.$$absUrl;
        $scope.displayLoginError = (url.indexOf("error") >= 0);
    });

    angular.module('login', ['ngMessages']);
})(angular);
