(function(angular){

    var hratsApp = angular.module('HRAts');

    hratsApp.controller('VacancyController', function($scope, $modal, VacancyService){

        $scope.pageConfiguration = { dataCollectionName: 'vacanciesCollection' };
        $scope.pageConfiguration.columnDefs = VacancyService.getColumnDefs();

        $scope.vacanciesCollection = [];

        VacancyService.fetchAll()
            .success(function (data) {
                $scope.vacanciesCollection = data;
            })
            .error(function (status, data) {
                alert("Unable to fetch data ("+status+").");
            });
    });

    hratsApp.controller('ModalInstanceController', function($log, $scope, $modalInstance, row, VacancyService, CompanyService, DepartmentService){

        //TODO organize vacancies modals - make them function properly
        //Add vacancy variables
        $scope.newVacancy = angular.copy(row.data) || {};

        $scope.newVacancy.startDate = new Date();
        $scope.newVacancy.numberOfVacancies = 1;

        CompanyService.fetchAll()
            .success(function (data){
                $scope.companiesCollection = data || [];
            })
            .error(function (status, data){
                alert("Unable to fetch data ("+status+").");
            });

        //Edit/delete vacancy variables
        $scope.vacancy = row.data;

        $scope.fetchRelatedDepartments = function(company) {
            DepartmentService.fetchRelatedDepartments(company)
                .success(function(data){
                    $scope.departmentList = data;
                    $scope.newVacancy.department = null;
                })
                .error(function(data, status){
                    alert("Unable to fetch departments ("+status+").");
                });
            console.log(company);
            return company.departmentList;
        };

        $scope.createVacancy = function(){
            $log.info($scope.newVacancy);
            VacancyService.createRow($scope.newVacancy)
                .success(function(data){
                    $scope.vacanciesCollection.push(data);
                })
                .error(function(data,status){
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