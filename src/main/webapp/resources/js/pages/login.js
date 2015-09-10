(function(angular) {

    var hratsApp = angular.module('HRAts', []);

    hratsApp.controller('LoginController', function($scope, $location){
        var url = "" + $location.$$absUrl;
        $scope.displayLoginError = (url.indexOf("error") >= 0);
    });

    hratsApp.controller('ResetController', function($scope, $http, $location){

        function get(name){
            if(name=(new RegExp('[?&]'+encodeURIComponent(name)+'=([^&]*)')).exec(location.search))
                return decodeURIComponent(name[1]);
        }

        var id = get("id");
        var token = get("token");

        if (!!id && !!token) {
            $scope.tokenIdAvailable = true;
        }

        var baseUrl = $location.absUrl();
        var usersBaseUrl = baseUrl.substr(0, baseUrl.search('passwordReset'))+'protected/users';

        $scope.user = {};

        $scope.sendResetToken = function (user) {
            if (user.email == null) {return;}

            $http.post(usersBaseUrl+"/resetPassword?email="+user.email, user)
                .success(function(data, status) {
                    alert(data.data);
                })
                .error(function(data) {
                    alert(data.status +"\r\n"+ data.data);
                })
        };

        $scope.changePassword = function(user) {



            if (user.password !== user.repeatPassword) {
                alert ("Passwords do not match");
                return;
            }

            $http.post(usersBaseUrl+'/passwordReset'+'?id='+id+'&token='+token, user)
                .success(function(data) {
                    alert("User's password changed successfully");
                }
            )
                .error( function(data, status) {
                    alert("Error during password change\r\n"+data.data + "\r\n with status: "+ data.status);
                }
            );
        }
    });
})(angular);