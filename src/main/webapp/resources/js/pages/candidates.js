(function(angular){

    var hratsApp = angular.module('HRAts');

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

    hratsApp.controller('CandidateDetailsController', function($scope, $modal, ContactService, DepartmentService, ContractTypeService) {
        var candidateId = $('#pathId').val();

        ContactService.fetchById(candidateId)
            .success(function(data) {
                $scope.candidateData = data || {};
            })
            .error(function(data) {
                alert('Unable to fetch data\r\n' + data);
            });

        DepartmentService.fetchAllByManager(candidateId)
            .success(function(data){
                $scope.departmentsCollection = data || [];
            })
            .error(function(data){
                alert('Unable to fetch departments\r\n'+data);
            });

        ContractTypeService.fetchAll()
            .success(function(data) {
                $scope.contractsCollection = data || [];
            })
            .error(function(status, data) {
                alert("Unable to fetch data (" + status + ").");
            });

        $scope.updateUsersInfo = function(user) {
            ContactService.updateRow(user)
                .success(function(data){
                    angular.copy(data, $scope.candidateData);
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
                controller: 'ModalInstanceController',
                size: 'lg',
                backdrop: true,
                scope: $scope,
                resolve: {
                    row: function(){
                        return {
                            modalName: modalTemplate,
                            data: $scope.candidateData || {}
                        };
                    }
                }
            });
        };
    });

    hratsApp.controller('DetailsGridsController', function($scope, VacancyService, ActivityService, GridService){
        var candidateId = $('#pathId').val();

        $scope.vacanciesGridOptions = {};
        angular.copy(GridService.getGridConfiguration(), $scope.vacanciesGridOptions);
        $scope.vacanciesGridOptions.data = [];
        $scope.vacanciesGridOptions.columnDefs = VacancyService.getColumnDefs();

        VacancyService.fetchByCandidateId(candidateId)
            .success(function (data) {
                console.log(data);
                $scope.vacanciesGridOptions.data = data || [];
            })
            .error(function (status, data) {
                alert("Unable to fetch data ("+status+").");
            });

        $scope.activitiesGridOptions = {};
        angular.copy(GridService.getGridConfiguration(), $scope.activitiesGridOptions);
        $scope.activitiesGridOptions.columnDefs = ActivityService.getColumnDefs();
        $scope.activitiesGridOptions.data = [];

        ActivityService.fetchByCandidateId(candidateId)
            .success(function (data) {
                $scope.activitiesGridOptions.data = data || [];
            })
            .error(function (status, data) {
                alert("Unable to fetch data ("+status+").");
            });

        $scope.redirect = function (row, endpoint) {
            GridService.redirect(row, endpoint);
        };
    });

    hratsApp.controller('ModalInstanceController', function($scope, $modalInstance, CandidateService, row,
                                                            VacancyService, ActivityTypeService, ActivityService,
                                                            CompanyService, DepartmentService, ContractTypeService,
                                                            VacancyUserService, StatusService, StatusTypeService) {
        //Add candidate variables
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

        $scope.removeFromArray = function(array, value) {
            var index = array.indexOf(value);
            if(index !== -1){
                array.splice(index, 1);
            }
            return array;
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

            DepartmentService.fetchRelatedDepartments(selectedCompanies).then(function(result) {
                $scope.departmentsCollection = result.data;
            });
        };

        $scope.fetchRelatedVacancies = function(selectedDepartments) {

            $scope.querySelection.selectedVacancies = [];
            if (selectedDepartments.length <= 0 ) {
                return;
            }

            VacancyService.fetchRelatedVacancies(selectedDepartments).then(function(result) {
                $scope.vacancyCollection = result.data;
            });
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

                break;
            case 'logActivityModal':
                $scope.activity = {};
                $scope.status = {};

                StatusTypeService.fetchAll()
                    .success(function(data){
                        $scope.statusTypeCollection = data || {};
                    })
                    .error(function(data){
                        alert("Unable to fetch vacancies ("+data.status+").");
                    });

                ActivityTypeService.fetchAll()
                    .success(function(data){
                        $scope.activityTypeCollection = data || {};
                    })
                    .error(function(data) {
                        alert("Unable to fetch activity types ("+data+").");
                    });

                VacancyService.fetchByCandidateId($scope.candidate.id)
                    .success(function(data) {
                        $scope.vacancyCollection = data || {};
                    })
                    .error(function(data) {
                        alert("Unable to fetch vacancies ("+data+").");
                    });

                break;
            default:
                $modalInstance.close();
                break;
        }

        $scope.createCandidate = function(files){

            $scope.newCandidate.candidateInformation.contractType = $scope.querySelection.selectedContractType || {};
            var selectedVacancies = [];
            for( var i = 0; i < $scope.querySelection.selectedVacancies.length; i++) {
                var vacancyToAdd = { vacancy: {id: $scope.querySelection.selectedVacancies[i].id},
                                     owner: {id: $scope.newCandidate.owner.id}
                };
                selectedVacancies.push(vacancyToAdd);
            }
            $scope.newCandidate.vacancyUserCandidateList = selectedVacancies;

            var req = CandidateService.setupReqData($scope.newCandidate, files);

            CandidateService.sendCustomRequest('', req)
                .success(function(data){
                    $scope.candidatesCollection.push(data.data);
                    $modalInstance.close();
                })
                .error(function(data,status){
                    alert("Unable to create record ("+status+").");
                });
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

            var vacanciesToAddList =  {
                candidate: {id: row.id },
                vacancy: $scope.querySelection.selectedVacancies[0],
                owner: {id: $scope.candidate.owner.id}
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

        $scope.logActivity = function(user) {

            $scope.activity.candidate = {id: $scope.candidate.id};
            $scope.activity.owner = {id: $scope.candidate.owner.id};

            if ($scope.statusSwitch) {
                $scope.status.owner = { id: user.owner.id };
                $scope.status.statusType = $scope.querySelection.selectedStatusType;
            }

            var activityStatusContext = { activity: $scope.activity, status: $scope.status || null};
            console.log(activityStatusContext);
            ActivityService.createRow(activityStatusContext)
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
                    $scope.candidatesCollection = removeFromArray($scope.candidatesCollection, row);
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