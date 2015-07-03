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

        this.createRow = function(contactData){
            return $http.post(baseUrl, contactData);
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

    hratsApp.controller('ModalInstanceController', function($scope, $modalInstance, ContactService, contact, CompanyService, DepartmentService){

        $scope.companiesCollection = [];

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

        $scope.departmentTransform = function(newDepartment) {
            var department = {
                    name: newDepartment,
                    company: {id: $scope.newContact.company.id}
            };

            return department;
        };

        $scope.newContact.owner = {};
        $scope.newContact.owner.id = $('#userId').attr('value');

        $scope.fetchRelatedDepartments = function(company) {
            DepartmentService.fetchAllByCompany(company.id)
                .success(function(data){
                    $scope.newContact.departments = data;
                })
                .error(function(data, status){
                    alert("Unable to fetch departments ("+status+").");
                });
            return $scope.newContact.departments;
        };

        //Edit/delete contact variables
        $scope.contact = contact;

        $scope.createContact = function(){
            $scope.newContact.role = "ROLE_MANAGER";
            console.log($scope.newContact);
            ContactService.createRow($scope.newContact)
                .success(function(data){
                    console.log(data);
                    $scope.contactsCollection.push(data);
                })
                .error(function(data,status){
                    $scope.createContactSuccess = false;
                    alert("Unable to create record ("+status+").");
                });

            $modalInstance.close();
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