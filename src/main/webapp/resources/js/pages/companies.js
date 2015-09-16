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

    hratsApp.controller('CompanyDetailsController', function($scope, $modal, CompanyService, ContactService, DepartmentService) {
        var companyId = $('#pathId').val();

        CompanyService.fetchById(companyId)
            .success(function(data) {
                $scope.companyData = data || {};
            })
            .error(function(data) {
                alert('Unable to fetch data\r\n' + data);
            });

        DepartmentService.fetchAllByCompany(companyId)
            .success(function(data){
                $scope.departmentsCollection = data || [];
            })
            .error(function(data){
                alert('Unable to fetch contacts\r\n'+data);
            });

        ContactService.fetchAllByCompanyId(companyId)
            .success(function(data){
                $scope.contactsCollection = data || [];
            })
            .error(function(data){
                alert('Unable to fetch contacts\r\n'+data);
            });

        $scope.updateUsersInfo = function(entityData) {
            CompanyService.updateRow(entityData)
                .success(function(data){
                    angular.copy(data, $scope.companyData);
                    alert("User's info updated successfully");
                })
                .error(function(data) {
                    alert("Could not update user's info\r\nStatus: "+data.status+'\r\nReason: '+data.data);
                })
        };

        $scope.openModal = function(modalTemplate, row){
            var modalInstance = $modal.open({
                animation: false,
                templateUrl: modalTemplate,
                controller: 'DetailsModalController',
                size: 'lg',
                backdrop: true,
                scope: $scope,
                resolve: {
                    row: function(){
                        return {
                            modalName: modalTemplate,
                            data: row || {}
                        };
                    }
                }
            });
        };
    });

    hratsApp.controller('DetailsGridsController', function($scope, VacancyService, ContactService, GridService){
        var companyId = $('#pathId').val();

        $scope.vacanciesGridOptions = {};
        angular.copy(GridService.getGridConfiguration(), $scope.vacanciesGridOptions);
        $scope.vacanciesGridOptions.data = [];
        $scope.vacanciesGridOptions.columnDefs = VacancyService.getColumnDefs();

        VacancyService.fetchAllByManagerId(companyId)
            .success(function (data) {
                console.log(data);
                $scope.vacanciesGridOptions.data = data || [];
            })
            .error(function (status, data) {
                alert("Unable to fetch data ("+status+").");
            });

        $scope.contactsGridOptions = {};
        angular.copy(GridService.getGridConfiguration(), $scope.contactsGridOptions);
        $scope.contactsGridOptions.columnDefs = ContactService.getColumnDefs();
        $scope.contactsGridOptions.data = [];

        ContactService.fetchAllByCompanyId(companyId)
            .success(function (data) {
                $scope.contactsGridOptions.data = data || [];
            })
            .error(function (status, data) {
                alert("Unable to fetch data ("+status+").");
            });

        $scope.redirect = function (row, endpoint) {
            GridService.redirect(row, endpoint);
        };
    });

    hratsApp.controller('DetailsModalController', function($scope, $modalInstance, $location, row,
                                                           CompanyService, DepartmentService){
        $scope.company = row.data;

        $scope.removeRow = function(company) {
            CompanyService.removeRow(company.id)
                .success(function(){
                    var lookupString = '/protected/companies/';
                    var baseUrl = $location.absUrl();
                    var trimIndex = baseUrl.indexOf(lookupString);
                    baseUrl = baseUrl.substr(0,trimIndex+lookupString.length);
                    window.location = baseUrl;
                })
                .error(function(data,status){
                    alert("Unable to remove record ("+status+").");
                })
        };

        $scope.addDepartment = function(company, departmentList) {
            $scope.owner = $('#userId').val();

            for (var i = 0; i < $scope.departmentList.length; i++) {
                $scope.departmentList[i].owner = {id: $scope.owner};
                $scope.departmentList[i].company = {id: $('#pathId').val()};
            }

            DepartmentService.createMultiple(departmentList)
                .success(function(){
                    alert("Departments added successfully");
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

    hratsApp.controller('ModalInstanceController', function($scope, $modalInstance, CompanyService, row, SectorService, DepartmentService){

        //Add company variables
        $scope.newCompany = angular.copy(row.data) || {};
        $scope.newCompany.owner = { id: $scope.owner };

        $scope.loadSectors = function() {
            return SectorService.fetchAllByName();
        };

        //Edit/delete company variables
        $scope.company = row.data;

        $scope.createCompany = function(files){
            for (var i = 0; i < $scope.newCompany.departmentList.length; i++) {
                $scope.newCompany.departmentList[i].owner = {id: $scope.newCompany.owner.id};
            }

            var req = CompanyService.setupReqData($scope.newCompany, files);

            CompanyService.sendCustomRequest('', req)
                .success(function(data){
                    $scope.companiesCollection.push(data.data);
                    $modalInstance.close();
                })
                .error(function(data,status){
                    alert("Unable to create record ("+status+").");
                });
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

        $scope.addDepartment = function(company, departmentList) {

            for (var i = 0; i < $scope.departmentList.length; i++) {
                $scope.departmentList[i].owner = {id: $scope.owner};
                $scope.departmentList[i].company = company;
            }

            DepartmentService.createMultiple(departmentList)
                .success(function(){
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