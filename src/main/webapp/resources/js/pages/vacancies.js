(function(angular){

    var hratsApp = angular.module('HRAts');

    hratsApp.service('VacancyService', function ($http){

        var baseUrl = 'http://localhost:8080/HRAts/protected/vacancies/';

        this.paginationOptions = function(){
            return [10, 15, 25, 50, 100];
        };

        this.fetchAll = function () {
            return $http.get(baseUrl);
        };

        this.fetchById = function(vacancyId) {
            return $http.get(baseUrl + vacancyId)
        }

        this.createRow = function(vacancyData){
            return $http.post(baseUrl, vacancyData);
        };

        this.updateRow = function(vacancyData) {
            return $http.put(baseUrl+vacancyData.id, vacancyData);
        };

        this.removeRow = function(vacancyId){
            return $http.delete(vacancyId);
        };

    });

    hratsApp.controller('VacancyController', function($scope, $modal, VacancyService){

        $scope.vacanciesCollection = [];

        $scope.paginationOptions = VacancyService.paginationOptions();

        VacancyService.fetchAll()
            .success(function (data) {
                $scope.vacanciesCollection = data;
                $scope.displayedCollection = [].concat($scope.vacanciesCollection);
            })
            .error(function () {
                alert("Unable to fetch data ("+status+").");
            });

        $scope.openModal = function(modalTemplate, vacancy){

            var modalInstance = $modal.open({
                animation: false,
                templateUrl: modalTemplate,
                controller: 'ModalInstanceController',
                size: 'lg',
                backdrop: true,
                scope: $scope,
                resolve: {
                    vacancy: function(){
                        return vacancy;
                    }
                }
            });
        };
    });

    hratsApp.controller('ModalInstanceController', function($scope, $modalInstance, VacancyService, vacancy, CompanyService, DepartmentService){

        //Add vacancy variables
        $scope.createVacancySuccess = true;
        $scope.newVacancy = angular.copy(vacancy) || {};
        $scope.newVacancy.department = $scope.newVacancy.department || {};
        if (angular.equals({}, $scope.newVacancy)) {
            $scope.newVacancy.numberOfVacancies = 1;
        };

        CompanyService.fetchAll()
            .success(function (data){
                $scope.companiesCollection = data || [];
            })
            .error(function (status, data){
                alert("Unable to fetch data ("+status+").");
            });

        //Edit/delete vacancy variables
        $scope.vacancy = vacancy;

        $scope.departmentTransform = function(newDepartment) {
            var department = {
                name: newDepartment,
                company: {id: $scope.newVacancy.company.id}
            };

            return department;
        };

        $scope.fetchRelatedDepartments = function(company) {
            $scope.newVacancy.departmentList = [];
            DepartmentService.fetchAllByCompany(company.id)
                .success(function(data){
                    company.departmentList = data;
                })
                .error(function(data, status){
                    alert("Unable to fetch departments ("+status+").");
                });
            console.log(company);
            return company.departmentList;
        };

        $scope.createVacancy = function(){
            VacancyService.createRow($scope.newVacancy)
                .success(function(data){
                    $scope.vacanciesCollection.push(data);
                })
                .error(function(data,status){
                    $scope.createVacancySuccess = false;
                    alert("Unable to create record ("+status+").");
                });

            $modalInstance.close();
        };

        $scope.updateVacancy = function(vacancy) {
            VacancyService.updateRow(vacancy)
                .success(function(data){
                    angular.copy(data, $scope.vacancy);
                    $modalInstance.close();
                })
                .error(function(data,status){
                    alert("Unable to update record ("+status+").");
                })
        };

        $scope.removeRow = function(vacancy) {
            VacancyService.removeRow(vacancy.id)
                .success(function(){
                    var index = $scope.vacanciesCollection.indexOf(vacancy);
                    if(index !== -1){
                        $scope.vacanciesCollection.splice(index, 1);
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