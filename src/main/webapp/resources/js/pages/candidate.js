(function(angular){

    var hratsApp = angular.module('HRAts');

    hratsApp.service('CandidateService', function ($http){

        var baseUrl = 'http://localhost:8080/HRAts/protected/candidate/';

        this.paginationOptions = function(){
            return [10, 15, 25, 50, 100];
        };

        this.fetchAll = function () {
            return $http.get(baseUrl);
        };

        this.fetchById = function(candidateId) {
            return $http.get(baseUrl + candidateId)
        };

        this.createRow = function(candidateData){
            return $http.post(baseUrl, candidateData);
        };

        this.updateRow = function(candidateData) {
            console.log(candidateData);
            return $http.put(baseUrl+candidateData.id, candidateData);
        };

        this.removeRow = function(candidateId){
            return $http.delete(candidateId);
        };
    });

    hratsApp.controller('CandidateController', function($scope, $modal, CandidateService){

        $scope.candidatesCollection = [];

        $scope.paginationOptions = CandidateService.paginationOptions();

        CandidateService.fetchAll()
            .success(function (data) {
                $scope.candidatesCollection = data;
                $scope.displayedCollection = [].concat($scope.candidatesCollection);
            })
            .error(function () {
                alert("Unable to fetch data ("+status+").");
            });

        $scope.openModal = function(modalTemplate, candidate){

            var modalInstance = $modal.open({
                animation: false,
                templateUrl: modalTemplate,
                controller: modalTemplate + 'Controller',
                size: 'lg',
                backdrop: true,
                scope: $scope,
                resolve: {
                    candidate: function(){
                        return candidate;
                    }
                }
            });
        };
    });

    hratsApp.controller('addCandidateModalController', function($scope, $modalInstance, CandidateService, candidate){

        //Add candidate variables
        $scope.createCandidateSuccess = true;
        $scope.newCandidate = angular.copy(candidate) || {};
        $scope.newCandidate.owner = {};
        $scope.newCandidate.owner.id = $('#userId').attr('value');


        $scope.createCandidate = function(){
            console.log($scope.newCandidate);
            CandidateService.createRow($scope.newCandidate)
            .success(function(data){
                    console.log(data);
                    $scope.candidatesCollection.push(data);
                })
                .error(function(data,status){
                    $scope.createCandidateSuccess = false;
                    alert("Unable to create record ("+status+").");
                });

            $modalInstance.close();
        };

        $scope.cancel = function(){
            $modalInstance.dismiss('cancel');
        };
    });

    hratsApp.controller('updateCandidateModalController', function($scope, $modalInstance, CandidateService, candidate){

        //Edit/delete candidate variables
        $scope.candidate = candidate;

        $scope.updateCandidate = function(candidate) {
            CandidateService.updateRow(candidate)
                .success(function(data){
                    angular.copy(data, $scope.candidate);
                    $modalInstance.close();
                })
                .error(function(data,status){
                    alert("Unable to update record ("+status+").");
                })
        };

        $scope.cancel = function(){
            $modalInstance.dismiss('cancel');
        };
    });

    hratsApp.controller('addToVacancyModalController', function($scope, $modalInstance, CandidateService, candidate, ActivityTypeService) {

        $scope.candidate = candidate;

        $scope.candidate.vacancyUserCandidateList[0] = { owner: {id: $scope.candidate.owner.id} };
        $scope.createCandidateSuccess = true;

        $scope.addToVacancy = function(candidate) {
            CandidateService.updateRow(candidate)
                .success(function(data){
                    angular.copy(data, $scope.candidate);
                    $modalInstance.close();
                })
                .error(function(data,status){
                    alert("Unable to update record ("+status+").");
                })
        };

        $scope.cancel = function(){
            $modalInstance.dismiss('cancel');
        };
    });

    hratsApp.controller('logActivityModalController', function($scope, $modalInstance, ActivityService, ActivityTypeService, candidate) {

        $scope.createCandidateSuccess = true;

        $scope.candidate = candidate;
        $scope.activity = {};

        ActivityTypeService.fetchAll()
            .success(function(data){
                $scope.activityTypeCollection = data || {};
            })
            .error(function(data, status) {
                alert("Unable to fetch activity types ("+status+").");
            });

        $scope.logActivity = function(candidate) {

            $scope.activity.candidate = {id: $scope.candidate.id};
            $scope.activity.owner = {id: $scope.candidate.owner.id};

            console.log($scope.activity);

            ActivityService.createRow($scope.activity)
                .success(function(data){
                    angular.copy(data, $scope.newActivity);
                    $modalInstance.close();
                })
                .error(function(data,status){
                    alert("Unable to update record ("+status+").");
                })
        };

        $scope.cancel = function(){
            $modalInstance.dismiss('cancel');
        };
    });

    hratsApp.controller('deleteCandidateModalController', function($scope, $modalInstance, CandidateService, candidate){

        //Edit/delete candidate variables
        $scope.candidate = candidate;
        $scope.candidate.activityTypeCollection[0] = { owner: {id: $scope.candidate.owner.id} };

        $scope.removeRow = function(candidate) {
            CandidateService.removeRow(candidate.id)
                .success(function(){
                    var index = $scope.candidatesCollection.indexOf(candidate);
                    if(index !== -1){
                        $scope.candidatesCollection.splice(index, 1);
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