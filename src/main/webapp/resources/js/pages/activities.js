(function(angular){

    var hratsApp = angular.module('HRAts');

    hratsApp.controller('ActivityController', function($scope, $modal, ActivityService){

        $scope.pageConfiguration = { dataCollectionName: 'activitiesCollection' };
        $scope.pageConfiguration.columnDefs = ActivityService.getColumnDefs();

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
    });

    hratsApp.controller('ModalInstanceController', function($scope, $modalInstance, ActivityService, row){

        //Add activity variables
        $scope.createActivitySuccess = true;
        $scope.newActivity = angular.copy(row.data) || {};
        if (angular.equals({}, $scope.newActivity)) {
            $scope.newActivity.numberOfActivities = 1;
        }
        //Edit/delete activity variables
        $scope.activity = row.data;

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