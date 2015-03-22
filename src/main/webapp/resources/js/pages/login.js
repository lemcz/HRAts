function loginController($scope, $location) {
    var url = "" + $location.$$absUrl;
    $scope.displayLoginError = (url.indexOf("error") >= 0);
}
//loginController.$inject = ['$scope'];
//angular.module('app', []).controller('loginController', loginController)
