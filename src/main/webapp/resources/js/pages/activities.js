(function(angular){

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
        }

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

    hratsApp.controller('ActivityController', function($scope, $modal, ActivityService){

        $scope.activitiesCollection = [];

        $scope.paginationOptions = ActivityService.paginationOptions();

        ActivityService.fetchAll()
            .success(function (data) {
                $scope.activitiesCollection = data;
                $scope.displayedCollection = [].concat($scope.activitiesCollection);
            })
            .error(function () {
                alert("Unable to fetch data ("+status+").");
            });

        $scope.openModal = function(modalTemplate, activity){

            var modalInstance = $modal.open({
                animation: false,
                templateUrl: modalTemplate,
                controller: 'ModalInstanceController',
                size: 'lg',
                backdrop: true,
                scope: $scope,
                resolve: {
                    activity: function(){
                        return activity;
                    }
                }
            });
        };
    });

    hratsApp.controller('ModalInstanceController', function($scope, $modalInstance, ActivityService, activity){

        //Add activity variables
        $scope.createActivitySuccess = true;
        $scope.newActivity = angular.copy(activity) || {};
        if (angular.equals({}, $scope.newActivity)) {
            $scope.newActivity.numberOfActivities = 1;
        };

        //Edit/delete activity variables
        $scope.activity = activity;

        $scope.createActivity = function(){
            ActivityService.createRow($scope.newActivity)
                .success(function(data){
                    $scope.activitiesCollection.push(data);
                })
                .error(function(data,status){
                    $scope.createActivitySuccess = false;
                    alert("Unable to create record ("+status+").");
                });

            $modalInstance.close();
        };

        $scope.updateActivity = function(activity) {
            ActivityService.updateRow(activity)
                .success(function(data){
                    angular.copy(data, $scope.activity);
                    $modalInstance.close();
                })
                .error(function(data,status){
                    alert("Unable to update record ("+status+").");
                })
        };

        $scope.removeRow = function(activity) {
            ActivityService.removeRow(activity.id)
                .success(function(){
                    var index = $scope.activitiesCollection.indexOf(activity);
                    if(index !== -1){
                        $scope.activitiesCollection.splice(index, 1);
                    }
                    $modalInstance.close();
                })
                .error(function(data,status){
                    alert("Unable to remove record ("+status+").");
                })
        };

        $scope.cancel = function(){
            $modalInstance.dismiss('cancel');
        };
    });
})(angular);