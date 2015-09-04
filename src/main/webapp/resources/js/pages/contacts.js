(function(angular){

    var hratsApp = angular.module('HRAts');

    hratsApp.service('ContactService', function ($http, uiGridConstants){

        var baseUrl = 'http://localhost:8080/HRAts/protected/contacts/';

        return {

            fetchAllContacts: function() {
                return $http.get(baseUrl);
            },

            fetchById: function(contactId) {
                return $http.get(baseUrl + contactId);
            },

            sendCustomRequest: function(endPoint, requestData) {
                requestData.url = baseUrl + endPoint;
                return $http(requestData);
            },
            createRow: function(contactData){
                var temporaryAppend = "uploadContactAndFiles";
                return $http.post(baseUrl + temporaryAppend, contactData);
            },

            updateRow: function(contactData) {
                return $http.put(baseUrl+contactData.id, contactData);
            },

            removeRow: function(contactId){
                return $http.delete(contactId);
            },

            getColumnDefs: function() {
                return [
                    { name:'id', width:50},
                    { name:'name', cellTemplate: '<div class="ui-grid-cell-contents"><a ng-click="grid.appScope.redirect(row.entity,\'contacts\')">{{COL_FIELD}}</a></div>', width:100 },
                    { name:'middleName', width:100 },
                    { name:'lastName', width:100 },
                    { name:'departmentList[0].company.name', width:100},
                    { name:'departmentList[0].name', width:100},
                    { name:'email', width:100, cellTemplate: '<div class="ui-grid-cell-contents"><a href="mailto:{{ COL_FIELD }}">{{ COL_FIELD }}</a></div>'},
                    { name:'phoneNumber', displayName: 'Phone', width:200},
                    { name:'note', width:300 },
                    { name:'dateEntered', cellFilter:'date: \'HH:MM:ss dd/MM/yyyy\'', width:150 },
                    { name:'dateModified', cellFilter:'date: \'HH:MM:ss dd/MM/yyyy\'', width:150, sort: {direction: uiGridConstants.DESC} },
                    { name:'owner.email', displayName:'Owner', width:150 }
                ];
            },

            setupReqData: function(data, files) {
                var formData = new FormData();
                formData.append('data', new Blob([JSON.stringify(data)], { type: "application/json" }));

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
        };
    });

    hratsApp.controller('ContactController', function($scope, $modal, ContactService){

        $scope.pageConfiguration = { dataCollectionName: 'contactsCollection' };
        $scope.pageConfiguration.columnDefs = ContactService.getColumnDefs();

        $scope.contactsCollection = [];

        ContactService.fetchAllContacts()
            .success(function (data) {
                $scope.contactsCollection = data;
            })
            .error(function (status, data) {
                alert("Unable to fetch data ("+status+").");
            });
    });

    hratsApp.controller('ModalInstanceController', function($scope, $modalInstance, $timeout, row, Upload, ContactService, CompanyService, DepartmentService){

        $scope.log = "";

        CompanyService.fetchAll()
            .success(function (data){
                console.log(data);
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
            var department = {
                    name: newDepartment,
                    company: {id: $scope.newContact.company.id}
            };

            return department;
        };

        $scope.removeFromArray = function(array, value) {
            var index = array.indexOf(value);
            if(index !== -1){
                array.splice(index, 1);
            }
            return array;
        };

        $scope.fetchRelatedDepartments = function(company) {
            console.log(company);
            $scope.newContact.departmentList = [];
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

        $scope.createContact = function (files) {
            $scope.newContact.role = "ROLE_MANAGER";

            var req = ContactService.setupReqData($scope.newContact, files);

            ContactService.sendCustomRequest('', req)
                .success(function (data) {
                    $timeout(function() {
                        $scope.log = 'file: ' + file.name + ', Response: ' + JSON.stringify(data) + '\n' + $scope.log;
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