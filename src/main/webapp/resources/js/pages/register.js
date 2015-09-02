(function(angular) {

    var hratsApp = angular.module('HRAts', []);

    hratsApp.controller('RegisterController', function($scope, $http){

        $scope.newUser = {};

        $scope.newUser.enabled = false;
        $scope.newUser.userRole = "ROLE_ADMIN"; //TODO add ROLE_USER functionality

        $scope.createUser = function (newUser) {

            if (newUser.password !== newUser.repeatPassword) {
                alert ("Passwords do not match");
                return;
            }

            var baseUrl = 'http://localhost:8080/HRAts/protected/users';
            $http.post(baseUrl+'/createUser', newUser)
                .success(function(data) {
                    alert("User registered");
                }
            )
            .error( function(data, status) {
                    alert("Error during registration \r\n"+data.data + "\r\n with status: "+ data.status);
                }
            );
        }
    });

})(angular);
