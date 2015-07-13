(function(angular){

    var hratsApp = angular.module('HRAts');

    hratsApp.service('CompanyService', function ($http){

        var baseUrl = 'http://localhost:8080/HRAts/protected/companies/';

        this.paginationOptions = function(){
            return [10, 15, 25, 50, 100];
        };

        this.fetchAll = function () {
            return $http.get(baseUrl);
        };

        this.fetchById = function(companyId) {
            return $http.get(baseUrl + companyId)
        };

        this.createRow = function(companyData){
            return $http.post(baseUrl, companyData);
        };

        this.updateRow = function(companyData) {
            return $http.put(baseUrl+companyData.id, companyData);
        };

        this.removeRow = function(companyId){
            return $http.delete(companyId);
        };
    });

    //todo find a place for this service
    hratsApp.service('SectorService', function ($http){

        var baseUrl = 'http://localhost:8080/HRAts/protected/sectors';
        this.fetchAll = function() {
            return $http.get(baseUrl);
        };

        this.fetchAllByName = function() {
            return $http.get(baseUrl+'/byName');
        };
    });


    hratsApp.controller('CompanyController', function($scope, $modal, CompanyService){

        $scope.companiesCollection = [];

        $scope.paginationOptions = CompanyService.paginationOptions();

        CompanyService.fetchAll()
            .success(function (data) {
                $scope.companiesCollection = data;
                $scope.displayedCollection = [].concat($scope.companiesCollection);
            })
            .error(function () {
                alert("Unable to fetch data ("+status+").");
            });

        $scope.openModal = function(modalTemplate, company){

            var modalInstance = $modal.open({
                animation: false,
                templateUrl: modalTemplate,
                controller: 'ModalInstanceController',
                size: 'lg',
                backdrop: true,
                scope: $scope,
                resolve: {
                    company: function(){
                        return company;
                    }
                }
            });
        };
    });

    hratsApp.controller('ModalInstanceController', function($scope, $modalInstance, CompanyService, company, SectorService){

        //Add company variables
        $scope.createCompanySuccess = true;
        $scope.newCompany = angular.copy(company) || {};
        $scope.newCompany.owner = {};
        $scope.newCompany.owner.id = $('#userId').attr('value');

        $scope.loadSectors = function(query) {
            return SectorService.fetchAllByName();
        };

        //Edit/delete company variables
        $scope.company = company;

        $scope.createCompany = function(){
            for (var i = 0; i < $scope.newCompany.departmentList.length; i++) {
                $scope.newCompany.departmentList[i].owner = {id: $scope.newCompany.owner.id};
            }
            console.log($scope.newCompany);
            CompanyService.createRow($scope.newCompany)
            .success(function(data){
                    console.log(data);
                    $scope.companiesCollection.push(data);
                })
                .error(function(data,status){
                    $scope.createCompanySuccess = false;
                    alert("Unable to create record ("+status+").");
                });

            $modalInstance.close();
        };

        $scope.updateCompany = function(company) {
            CompanyService.updateRow(company)
                .success(function(data){
                    angular.copy(data, $scope.company);
                    $modalInstance.close();
                })
                .error(function(data,status){
                    alert("Unable to update record ("+status+").");
                })
        };

        $scope.removeRow = function(company) {
            CompanyService.removeRow(company.id)
                .success(function(){
                    var index = $scope.companiesCollection.indexOf(company);
                    if(index !== -1){
                        $scope.companiesCollection.splice(index, 1);
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