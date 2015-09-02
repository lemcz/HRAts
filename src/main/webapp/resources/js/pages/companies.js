(function(angular){

    var hratsApp = angular.module('HRAts');

    hratsApp.controller('CompanyController', function($scope, $modal, CompanyService){

        $scope.pageConfiguration = { dataCollectionName: 'companiesCollection' };
        $scope.pageConfiguration.columnDefs = CompanyService.getColumnDefs();

        $scope.companiesCollection = [];
        $scope.owner = $('#userId').attr('value');

        CompanyService.fetchAll()
            .success(function (data) {
                $scope.companiesCollection = data;
            })
            .error(function () {
                alert("Unable to fetch data ("+status+").");
            });
    });

    hratsApp.controller('ModalInstanceController', function($scope, $modalInstance, CompanyService, row, SectorService, DepartmentService){

        //Add company variables
        $scope.newCompany = angular.copy(row.data) || {};
        $scope.newCompany.owner = {};
        $scope.newCompany.owner.id = $scope.owner;

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


        //TODO poprawic te funkcje (zunifikowac ownera, tak zeby zawsze byl przechowywany jako jedna zmienna i sprawdzic czy rzeczywiscie dodaje depratment do company)
        //pomyslec nad opcja manager per department przy okazji dodawania nowych departamentow. trzeba by zmienic sposob dodawania
        //(nie po tagach, tylko oddzielne input fieldy, automatycznie generowane per department?)

        $scope.addDepartment = function(company, departmentList) {

            for (var i = 0; i < $scope.departmentList.length; i++) {
                $scope.departmentList[i].owner = {id: $scope.owner};
                $scope.departmentList[i].company = company;
            }

            DepartmentService.createMultiple(departmentList)
                .success(function(){
                    //TODO dorobic update company do ktorej zostaly dodane departamenty
                    $modalInstance.close();
                })
                .error(function(data,status){
                    alert("Unable to create records ("+status+").");
                })
        };

        $scope.cancel = function(){
            $modalInstance.dismiss('cancel');
        };
    });
})(angular);