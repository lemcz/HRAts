(function(angular){

    var hratsApp = angular.module('HRAts');

    hratsApp.controller('CompanyController', function($scope, $modal, CompanyService){

        $scope.pageConfiguration = { dataCollectionName: 'companiesCollection' };
        $scope.pageConfiguration.columnDefs = CompanyService.getColumnDefs();

        $scope.companiesCollection = [];

        CompanyService.fetchAll()
            .success(function (data) {
                $scope.companiesCollection = data;
            })
            .error(function () {
                alert("Unable to fetch data ("+status+").");
            });
    });

    hratsApp.controller('ModalInstanceController', function($scope, $modalInstance, CompanyService, row, SectorService){

        //Add company variables
        $scope.createCompanySuccess = true;
        $scope.newCompany = angular.copy(row.data) || {};
        $scope.newCompany.owner = {};
        $scope.newCompany.owner.id = $('#userId').attr('value');

        $scope.loadSectors = function(query) {
            return SectorService.fetchAllByName();
        };

        //Edit/delete company variables
        $scope.company = row.data;

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