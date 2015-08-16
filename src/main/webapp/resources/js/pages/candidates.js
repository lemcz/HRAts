(function(angular){

    var hratsApp = angular.module('HRAts');

    hratsApp.service('CandidateService', function ($http){

        var baseUrl = 'http://localhost:8080/HRAts/protected/candidates/';

        return {

            fetchAll: function () {
                return $http.get(baseUrl);
            },

            fetchById: function(candidateId) {
                return $http.get(baseUrl + candidateId)
            },

            createRow: function(candidateData){
                return $http.post(baseUrl, candidateData);
            },

            updateRow: function(candidateData) {
                console.log(candidateData);
                return $http.put(baseUrl+'/'+candidateData.id, candidateData);
            },

            removeRow: function(candidateId){
                return $http.delete(candidateId);
            },

            getColumnDefs: function() {
                return [
                    { name:'id', width:50 },
                    { name:'name', cellTemplate: '<div class="ui-grid-cell-contents"><a data-toggle="modal" ng-click="grid.appScope.openModal(\'addToVacancyModal\', row.entity)" role="button" >{{COL_FIELD}}</a></div>', width:100 },
                    { name:'middleName', width:100 },
                    { name:'lastName', width:100 },
                    { name:'candidateInformation.address', displayName: "Address", width:150},
                    { name:'candidateInformation.city', displayName: "City", width:150},
                    { name:'candidateInformation.state', displayName: "State", width:150},
                    { name:'candidateInformation.zipCode', displayName: "Zip Code", width:150},
                    { name:'phoneNumber', width:150},
                    { name:'email', width:100, cellTemplate: '<div class="ui-grid-cell-contents text-center"><a href="mailto:{{ COL_FIELD }}">{{ COL_FIELD }}</a></div>'},
                    { name:'phone', width:200},
                    { name:'about', width:300 },
                    { name:'dateEntered', cellFilter:'date', width:150 },
                    { name:'dateModified', cellFilter:'date', width:150 },
                    { name:'owner.email', displayName:'Owner', width:150 }
                ];
            },

            setupReqData: function (data, files) {
                var formData = new FormData();
                formData.append('data', new Blob([JSON.stringify(data)], {type: "application/json"}));

                if (files && files.length) {
                    for (var i = 0; i < files.length; i++) {
                        var file = files[i];
                        formData.append("file", file);
                    }
                }

                var req = {
                    method: 'POST',
                    headers: {
                        'Content-Type': undefined
                    },
                    data: formData
                };

                return req;
            }
        }
    });

    hratsApp.controller('CandidateController', function($scope, $modal, CandidateService){

        $scope.pageConfiguration = { dataCollectionName: 'candidatesCollection' };
        $scope.pageConfiguration.columnDefs = CandidateService.getColumnDefs();

        $scope.candidatesCollection = [];

        CandidateService.fetchAll()
            .success(function (data) {
                $scope.candidatesCollection = data;
            })
            .error(function () {
                alert("Unable to fetch data ("+status+").");
            });
    });

    hratsApp.controller('ModalInstanceController', function($scope, $modalInstance, CandidateService, row,
                                                            VacancyService, ActivityTypeService, ActivityService,
                                                            CompanyService, DepartmentService, ContractTypeService, VacancyUserService) {

        //TODO reorganize controller, setup owner
        //Add candidate variables
        console.log(row);
        var modalType = row.modalName;
        $scope.candidate = row.data;
        $scope.newCandidate = angular.copy(row.data) || {};
        $scope.querySelection = {
            selectedCompanies: [],
            selectedDepartments: [],
            selectedVacancies: []
        };
        $scope.companiesCollection = [];
        $scope.departmentsCollection = [];
        $scope.contractsCollection = [];
        $scope.vacancyCollection = [];


        $scope.showMe = function() {
            alert('KUPA');
        };

        CompanyService.fetchAll()
            .success(function (data) {
                $scope.companiesCollection = data || [];
            })
            .error(function (status, data) {
                alert("Unable to fetch data (" + status + ").");
            });

        ContractTypeService.fetchAll()
            .success(function(data) {
                $scope.contractsCollection = data || [];
            })
            .error(function(status, data) {
                alert("Unable to fetch data (" + status + ").");
            });

        $scope.fetchRelatedDepartments = function(selectedCompanies) {

            $scope.querySelection.selectedDepartments = [];
            if (selectedCompanies.length <= 0 ) {
                return;
            }

            var departmentsCollectionPromise = DepartmentService.fetchRelatedDepartments(selectedCompanies);
            departmentsCollectionPromise.then(function(result) {
                $scope.departmentsCollection = result.data;
            })
        };

        $scope.fetchRelatedVacancies = function(selectedDepartments) {

            $scope.querySelection.selectedVacancies = [];
            if (selectedDepartments.length <= 0 ) {
                return;
            }

            var vacanciesCollectionPromise = VacancyService.fetchRelatedVacancies(selectedDepartments);
            vacanciesCollectionPromise.then(function(result) {
                $scope.vacancyCollection = result.data;
            })
        };

        switch(modalType) {
            case 'addCandidateModal':
                $scope.newCandidate.owner = { id: $('#userId').attr('value')};
                $scope.newCandidate.candidateInformation = {startDate: new Date()};
                //
                break;
            case 'editCandidateModal':
                //
                break;
            case 'deleteCandidateModal':
                // do nothing
                break;
            case 'addToVacancyModal':

                VacancyService.fetchAll()
                    .success(function(data) {
                        $scope.vacancyCollection = data || {};
                    })
                    .error(function(data,status){
                        alert("Unable to fetch vacancies ("+status+").");
                    });
                break;
            case 'logActivityModal':
                $scope.activity = {};

                ActivityTypeService.fetchAll()
                    .success(function(data){
                        $scope.activityTypeCollection = data || {};
                    })
                    .error(function(data, status) {
                        alert("Unable to fetch activity types ("+status+").");
                    });

                VacancyService.fetchAll()
                    .success(function(data) {
                        $scope.vacancyCollection = data || {};
                    })
                    .error(function(data, status) {
                        alert("Unable to fetch vacancies ("+status+").");
                    });

                break;
            default:
                $modalInstance.close();
                break;
        }

        $scope.createCandidate = function(){

            $scope.newCandidate.candidateInformation.contractType = $scope.querySelection.selectedContractType || {};
            CandidateService.createRow($scope.newCandidate)
                .success(function(data){
                    console.log(data);
                    $scope.candidatesCollection.push(data);
                })
                .error(function(data,status){
                    alert("Unable to create record ("+status+").");
                });

            $modalInstance.close();
        };

        $scope.updateCandidate = function(row) {
            CandidateService.updateRow(row)
                .success(function(data){
                    angular.copy(data, $scope.candidate);
                    $modalInstance.close();
                })
                .error(function(data,status){
                    alert("Unable to update record ("+status+").");
                })
        };

        $scope.addToVacancy = function(row, vacancy) {

            var requestData = {};

            var vacanciesToAddList =  {
                candidate: {id: row.id },
                vacancy: $scope.querySelection.selectedVacancies[0],
                owner: {id: $scope.candidate.owner.id}
            };

            VacancyUserService.createRow(vacanciesToAddList)
                .success(function(data){
                    //TODO Add success behavior (update row)
                    //angular.copy(data, $scope.row);
                    $modalInstance.close();
                })
                .error(function(data,status){
                    alert("Unable to update record ("+status+").");
                })
        };

        $scope.logActivity = function() {

            $scope.activity.candidate = {id: $scope.candidate.id};
            $scope.activity.owner = {id: $scope.candidate.owner.id};

            ActivityService.createRow($scope.activity)
                .success(function(data){
                    angular.copy(data, $scope.newActivity);
                    $modalInstance.close();
                })
                .error(function(data,status){
                    alert("Unable to update record ("+status+").");
                })
        };
        $scope.removeRow = function(row) {
            CandidateService.removeRow(row.id)
                .success(function(){
                    var index = $scope.candidatesCollection.indexOf(row);
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