(function(angular) {

    var hratsApp = angular.module('HRAts', []);

    hratsApp.controller('LoginController', function($scope, $location){
        var url = "" + $location.$$absUrl;
        $scope.displayLoginError = (url.indexOf("error") >= 0);
    });

    hratsApp.controller('ResetController', function($scope, $http, $location){

        var baseUrl = 'http://localhost:8080/HRAts/protected/users';
        $scope.user = {};

        function get(name){
            if(name=(new RegExp('[?&]'+encodeURIComponent(name)+'=([^&]*)')).exec(location.search))
                return decodeURIComponent(name[1]);
        }

        $scope.sendResetToken = function (user) {
            if (user.email == null) {return;}

            $http.post(baseUrl+"/resetPassword?email="+user.email, user)
                .success(function(data, status) {
                    alert(data.data);
                })
                .error(function(data) {
                    alert(data.status +"\r\n"+ data.data);
                })
        };

        $scope.changePassword = function(user) {

            var id = get("id");
            var token = get("token");

            if (user.password !== user.repeatPassword) {
                alert ("Passwords do not match");
                return;
            }

            $http.post(baseUrl+'/passwordReset'+'?id='+id+'&token='+token, user)
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

    angular.module('login', ['ngMessages']);
})(angular);
