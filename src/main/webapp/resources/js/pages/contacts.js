(function(angular){

    var hratsApp = angular.module('HRAts');

    hratsApp.controller('ContactController', function($scope, $modal, ContactService){

        $scope.pageConfiguration = { dataCollectionName: 'contactsCollection' };
        $scope.pageConfiguration.columnDefs = ContactService.getColumnDefs();

        ContactService.fetchAllContacts()
            .success(function (data) {
                $scope.contactsCollection = data || [];
            })
            .error(function (status, data) {
                alert("Unable to fetch data ("+status+").");
            });
    });

    hratsApp.controller('ContactDetailsController', function($scope, $modal, ContactService, DepartmentService) {
        var contactId = $('#pathId').val();

        ContactService.fetchById(contactId)
            .success(function(data) {
                $scope.contactData = data || {};
            })
            .error(function(data) {
                alert('Unable to fetch data\r\n' + data);
            });

        DepartmentService.fetchAllByManager(contactId)
            .success(function(data){
                $scope.departmentsCollection = data || [];
            })
            .error(function(data){
                alert('Unable to fetch departments\r\n'+data);
            });

        $scope.updateUsersInfo = function(user) {
            ContactService.updateRow(user)
                .success(function(data){
                    angular.copy(data, $scope.contactData);
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

    hratsApp.controller('DetailsGridsController', function($scope, VacancyService, ActivityService, GridService){
        var contactId = $('#pathId').val();

        $scope.vacanciesGridOptions = {};
        angular.copy(GridService.getGridConfiguration(), $scope.vacanciesGridOptions);
        $scope.vacanciesGridOptions.data = [];
        $scope.vacanciesGridOptions.columnDefs = VacancyService.getColumnDefs();

        VacancyService.fetchAllByManagerId(contactId)
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

        ActivityService.fetchByContactId(contactId)
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

    hratsApp.controller('DetailsModalController', function($scope, $modalInstance, $location, row,
                                                           ContactService, VacancyService, CompanyService,
                                                           DepartmentService, ActivityService, ActivityTypeService){
        var contactId = $('#pathId').val();
        $scope.contact = row.data;

        VacancyService.fetchAll()
            .success(function(data) {
                $scope.vacancyCollection = data || {};
            })
            .error(function(data) {
                alert("Unable to fetch vacancies ("+data+").");
            });

        ActivityTypeService.fetchAll()
            .success(function(data){
                $scope.activityTypeCollection = data || {};
            })
            .error(function(data) {
                alert("Unable to fetch activity types ("+data+").");
            });

        CompanyService.fetchAll()
            .success(function (data){
                $scope.companiesCollection = data || [];
            })
            .error(function (status, data){
                alert("Unable to fetch data ("+status+").");
            });

        $scope.removeRow = function(contact) {
            ContactService.removeRow(contact.id)
                .success(function(){
                    var lookupString = '/protected/contacts/';
                    var baseUrl = $location.absUrl();
                    var trimIndex = baseUrl.indexOf(lookupString);
                    baseUrl = baseUrl.substr(0,trimIndex+lookupString.length);
                    window.location = baseUrl;
                })
                .error(function(data,status){
                    alert("Unable to remove record ("+status+").");
                })
        };

        $scope.logActivity = function(contactData) {
            var ownerId = $('#userId');

            $scope.activity.candidate = {id: contactData.id};
            $scope.activity.owner = {id: ownerId};

            var activityStatusContext = { activity: $scope.activity, status: null};
            ActivityService.createRow(activityStatusContext)
                .success(function(data){
                    alert("Activity successfully logged\r\n"+data);
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

    hratsApp.controller('ModalInstanceController', function($scope, $modalInstance, $timeout, row, Upload,
                                                            ContactService, VacancyService, CompanyService,
                                                            DepartmentService, ActivityService, ActivityTypeService){
        VacancyService.fetchAll()
            .success(function(data) {
                $scope.vacancyCollection = data || {};
            })
            .error(function(data) {
                alert("Unable to fetch vacancies ("+data+").");
            });

        ActivityTypeService.fetchAll()
            .success(function(data){
                $scope.activityTypeCollection = data || {};
            })
            .error(function(data) {
                alert("Unable to fetch activity types ("+data+").");
            });

        CompanyService.fetchAll()
            .success(function (data){
                $scope.companiesCollection = data || [];
            })
            .error(function (status, data){
                alert("Unable to fetch data ("+status+").");
            });

        //Add contact variables
        $scope.newContact = angular.copy(row.data) || {};
        $scope.newContact.departmentList = $scope.newContact.departmentList || [];

        $scope.newContact.owner = {};
        $scope.newContact.owner.id = $('#userId').attr('value');

        //Edit/delete contact variables
        $scope.contact = row.data;

        $scope.departmentTransform = function(newDepartment) {
            return {
                name: newDepartment,
                company: {id: $scope.newContact.company.id}
            };
        };

        $scope.removeFromArray = function(array, value) {
            var index = array.indexOf(value);
            if(index !== -1){
                array.splice(index, 1);
            }
            return array;
        };

        $scope.fetchRelatedDepartments = function(company) {
            $scope.newContact.departmentList = [];
            DepartmentService.fetchAllByCompanyWhereManagerIsNull(company.id)
                .success(function(data){
                    company.departmentList = data;
                })
                .error(function(data, status){
                    alert("Unable to fetch departments ("+status+").");
                });
            return company.departmentList;
        };

        $scope.createContact = function (files) {
            $scope.newContact.role = "ROLE_MANAGER";

            var req = ContactService.setupReqData($scope.newContact, files);

            ContactService.sendCustomRequest('', req)
                .success(function (data) {
                    $timeout(function() {
                        alert('file: ' + files.name + ', Response: ' + JSON.stringify(data) + '\n');
                    });
                    $scope.contactsCollection.push(data);
                    $modalInstance.close();
                })
                .error(function (data, status) {
                    alert("Unable to fetch data: "+ data + " status: " + status);
                });
        };

        $scope.updateContact = function(contact) {
            ContactService.updateRow(contact)
                .success(function(data){
                    angular.copy(data, $scope.contact);
                    $modalInstance.close();
                })
                .error(function(data,status){
                    alert("Unable to update record ("+status+").");
                })
        };

        $scope.removeRow = function(contact) {
            ContactService.removeRow(contact.id)
                .success(function(){
                    $scope.contactsCollection = removeFromArray($scope.contactsCollection, contact);
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