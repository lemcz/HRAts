(function(angular) {

    var hratsApp = angular.module('HRAts');

    hratsApp.service('UserService', function ($http){

        var baseUrl = 'http://localhost:8080/HRAts/protected/users/';

        return {

            fetchAll: function () {
                return $http.get(baseUrl);
            },

            fetchById: function(userId) {
                return $http.get(baseUrl + userId)
            },

            updateRow: function(userData) {
                console.log(userData);
                return $http.put(baseUrl+'/'+userData.id, userData);
            },

            changePassword: function(userData, oldPassword, newPassword) {
                console.log(userData);
                console.log(baseUrl+'/changePassword'+'?oldPassword='+oldPassword+'&newPassword='+newPassword);
                return $http.post(baseUrl+'/changePassword'+'?oldPassword='+oldPassword+'&newPassword='+newPassword, userData);
            }
        }
    });


    hratsApp.controller('SettingsController', function($scope, $http, UserService){

        //todo fill this in
        var userId = $('#userId').val();

        console.log ('asd');
        UserService.fetchById(userId)
            .success(function(data) {
                $scope.userData = data;
            })
            .error(function(data) {
                alert('Ooops, something went wrong...\r\n'+data.data);
            });

        $scope.updateUsersInfo = function(user) {
            UserService.updateRow(user)
                .success(function(data){
                    angular.copy(data, $scope.userData);
                    alert("User's info updated successfully");
                })
                .error(function(data) {
                    alert("Could not update user's info\r\nStatus: "+data.status+'\r\nReason: '+data.data);
                })
        };

        $scope.changePassword = function(user) {
            if (user.password !== user.repeatPassword) {
                alert("Passwords do not match");
                return;
            }

            UserService.changePassword($scope.userData, user.oldPassword, user.password)
                .success(function(data){
                    alert("Password updated successfully");
                })
                .error(function(data) {
                    alert("Could not update user's password\r\nStatus: "+data.status+'\r\nReason: '+data.data);
                })
        }
    });

})(angular);
