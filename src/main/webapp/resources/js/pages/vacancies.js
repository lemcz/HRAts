(function(angular){

    var hratsApp = angular.module('HRAts');

    hratsApp.controller('VacancyController', function($scope, $modal, VacancyService){

        $scope.owner = { id: $('#userId').val() };

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

    hratsApp.controller('VacancyDetailsController', function($scope, $modal, VacancyService, GridService, ContactService) {
        var vacancyId = $('#pathId').val();

        $scope.vacancyData = {};

        VacancyService.fetchById(vacancyId)
            .success(function(data) {
                $scope.vacancyData = data || {};
            })
            .error(function(data) {
                alert('Unable to fetch data\r\n' + data);
            });

        ContactService.fetchByVacancyId(vacancyId)
            .success(function(data){
                $scope.vacancyData.manager = data || {};
            })
            .error(function(data){
                alert("Unable to fetch related manager\r\n" + data);
            });

        $scope.updateUsersInfo = function(entityData) {
            VacancyService.updateRow(entityData)
                .success(function(data){
                    angular.copy(data, $scope.vacancyData);
                    alert("User's info updated successfully");
                })
                .error(function(data) {
                    alert("Could not update vacancy's info\r\nStatus: "+data.status+'\r\nReason: '+data.data);
                })
        };

        $scope.redirect = function (row, endpoint) {
            GridService.redirect(row, endpoint);
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

    hratsApp.controller('DetailsGridsController', function($scope, CandidateService, GridService){
        var vacancyId = $('#pathId').val();

        $scope.candidatesGridOptions = GridService.getGridConfiguration() || {};
        $scope.candidatesGridOptions.data = [];
        $scope.candidatesGridOptions.columnDefs = CandidateService.getColumnDefs();

        CandidateService.fetchAllByVacancyId(vacancyId)
            .success(function (data) {
                $scope.candidatesGridOptions.data = data || [];
            })
            .error(function (status, data) {
                alert("Unable to fetch data ("+status+").");
            });
    });

    hratsApp.controller('DetailsModalController', function($scope, $modalInstance, $location, row,
                                                           VacancyService){
        $scope.vacancy = row.data;
        console.log(row);

        $scope.removeRow = function(vacancy) {
            VacancyService.removeRow(vacancy.id)
                .success(function(){
                    var lookupString = '/protected/vacancies/';
                    var baseUrl = $location.absUrl();
                    var trimIndex = baseUrl.indexOf(lookupString);
                    baseUrl = baseUrl.substr(0,trimIndex+lookupString.length);
                    window.location = baseUrl;
                })
                .error(function(data,status){
                    alert("Unable to remove record ("+status+").");
                })
        };

        $scope.cancel = function(){
            $modalInstance.dismiss('cancel');
        };
    });

    hratsApp.controller('ModalInstanceController', function($log, $scope, $modalInstance, row,
                                                            VacancyService, VacancyUserService, CandidateService,
                                                            CompanyService, DepartmentService){

        //Add vacancy variables
        $scope.newVacancy = row.data || {};

        $scope.newVacancy.startDate = new Date();
        $scope.newVacancy.numberOfVacancies = 1;

        var modalType = row.modalName;

        switch(modalType) {
            case 'addToVacancyModal':
                $scope.candidatesCollection = [];
                CandidateService.fetchAllNotAssignedToVacancy($scope.newVacancy.id)
                    .success(function(data) {
                        $scope.candidatesCollection = data || [];
                    })
                    .error(function(data){
                        alert("Unable to fetch vacancies ("+data+").");
                    });
                break;
            default: break;
        }

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

        $scope.addToVacancy = function(row, vacancy) {

            var vacanciesToAddList =  {
                vacancy: {id: row.id },
                candidates: $scope.querySelection.selectedCandidates[0],
                owner: {id: $('#userId').val()}
            };

            VacancyUserService.createRow(vacanciesToAddList)
                .success(function(data){
                    alert("Candidate successfully added to vacancy");
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