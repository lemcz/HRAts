(function(angular){

    var hratsApp = angular.module('HRAts');

    hratsApp.filter('propsFilter', function() {
        return function(items, props) {
            var out = [];

            if (angular.isArray(items)) {
                items.forEach(function(item) {
                    var itemMatches = false;

                    var keys = Object.keys(props);
                    for (var i = 0; i < keys.length; i++) {
                        var prop = keys[i];
                        var text = props[prop].toLowerCase();
                        if (item[prop].toString().toLowerCase().indexOf(text) !== -1) {
                            itemMatches = true;
                            break;
                        }
                    }

                    if (itemMatches) {
                        out.push(item);
                    }
                });
            } else {
                // Let the output be the input untouched
                out = items;
            }

            return out;
        }
    });

    hratsApp.service('ContactService', function ($http){

        var baseUrl = 'http://localhost:8080/HRAts/protected/contacts/';

        this.paginationOptions = function(){
            return [10, 15, 25, 50, 100];
        };

        this.fetchAll = function () {
            return $http.get(baseUrl);
        };

        this.fetchById = function(contactId) {
            return $http.get(baseUrl + contactId)
        };

        this.sendCustomRequest = function(endPoint, requestData) {
            requestData.url = baseUrl + endPoint;
            return $http(requestData);
        };

        this.createRow = function(contactData){
            var temporaryAppend = "uploadContactAndFiles";
            return $http.post(baseUrl + temporaryAppend, contactData);
        };

        this.updateRow = function(contactData) {
            return $http.put(baseUrl+contactData.id, contactData);
        };

        this.removeRow = function(contactId){
            return $http.delete(contactId);
        };
    });

    //todo find a better place for this
    hratsApp.service('DepartmentService', function ($http){

        var baseUrl = 'http://localhost:8080/HRAts/protected/departments';
        this.fetchAll = function() {
            return $http.get(baseUrl);
        };

        this.fetchAllByName = function() {
            return $http.get(baseUrl+'/byName');
        };

        this.fetchAllByCompany = function(companyId) {
            return $http.get(baseUrl+'/?companyId='+companyId);
        }
    });

    hratsApp.controller('ContactController', function($scope, $modal, ContactService){

        $scope.contactsCollection = [];

        $scope.paginationOptions = ContactService.paginationOptions();

        ContactService.fetchAll()
            .success(function (data) {
                $scope.contactsCollection = data;
                $scope.displayedCollection = [].concat($scope.contactsCollection);
            })
            .error(function (status, data) {
                alert("Unable to fetch data ("+status+").");
            });

        $scope.openModal = function(modalTemplate, contact){

            var modalInstance = $modal.open({
                animation: false,
                templateUrl: modalTemplate,
                controller: 'ModalInstanceController',
                size: 'lg',
                backdrop: true,
                scope: $scope,
                resolve: {
                    contact: function(){
                        return contact;
                    }
                }
            });
        };
    });

    hratsApp.controller('ModalInstanceController', function($scope, $modalInstance, $timeout, Upload, ContactService, contact, CompanyService, DepartmentService){

        $scope.companiesCollection = [];

        $scope.log = "";

        CompanyService.fetchAll()
            .success(function (data){
                console.log(data);
                $scope.companiesCollection = data;
            })
            .error(function (status, data){
                alert("Unable to fetch data ("+status+").");
            });

        //Add contact variables
        $scope.createContactSuccess = true;
        $scope.newContact = angular.copy(contact) || {};
        $scope.newContact.departmentList = $scope.newContact.departmentList || [];

        $scope.newContact.owner = {};
        $scope.newContact.owner.id = $('#userId').attr('value');

        //Edit/delete contact variables
        $scope.contact = contact;

        $scope.departmentTransform = function(newDepartment) {
            var department = {
                    name: newDepartment,
                    company: {id: $scope.newContact.company.id}
            };

            return department;
        };

        $scope.fetchRelatedDepartments = function(company) {
            console.log(company);
            $scope.newContact.departmentList = {};
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
            var formData = new FormData();
            formData.append('data', new Blob([JSON.stringify($scope.newContact)], { type: "application/json" }));

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
                    var index = $scope.contactsCollection.indexOf(contact);
                    if(index !== -1){
                        $scope.contactsCollection.splice(index, 1);
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