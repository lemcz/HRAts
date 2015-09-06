
(function(angular) {
    var hratsApp = angular.module('HRAts');


    hratsApp.service('VacancyService', function ($http) {

        var baseUrl = 'http://localhost:8080/HRAts/protected/vacancies/';

        var vacancyServiceFunctions = {
            fetchAll: function () {
                return $http.get(baseUrl);
            },

            fetchById: function (vacancyId) {
                return $http.get(baseUrl + vacancyId)
            },

            fetchAllByDepartment_IdIn: function(departmentsIds) {
                var req = {
                    method: 'POST',
                    url: baseUrl+'/perDepartments',
                    headers: {
                        type: 'list'
                    },
                    data: departmentsIds
                };

                return $http(req);
            },

            fetchAllByDepartment_IdInAndCandidateIdEqual: function(departmentsIds, candidateId) {
                var req = {
                    method: 'GET',
                    url: baseUrl+'/perDepartments?candidateId='+candidateId,
                    data: departmentsIds
                };

                return $http(req);
            },

            fetchAllByManagerId: function(managerId) {
                return $http.get(baseUrl +'?search=manager&id=' + managerId);
            },

            fetchByCandidateId: function(candidateId) {
                return $http.get(baseUrl+'?search=candidate&id='+ candidateId);
            },

            createRow: function (vacancyData) {
                return $http.post(baseUrl, vacancyData);
            },

            updateRow: function (vacancyData) {
                return $http.put(baseUrl + vacancyData.id, vacancyData);
            },

            removeRow: function (vacancyId) {
                return $http.delete(vacancyId);
            },

            getColumnDefs: function () {
                return [
                    {name: 'id', width: 50},
                    {name:'name', cellTemplate: '<div class="ui-grid-cell-contents"><a ng-click="grid.appScope.redirect(row.entity,\'vacancies\')">{{COL_FIELD}}</a></div>', width:100 },
                    {name: 'department.company.name', displayName: 'Company', width: 100},
                    {name: 'department.name', width: 100},
                    {name: 'about', width: 300},
                    {name: 'dateEntered', cellFilter: 'date', width: 150},
                    {name: 'dateModified', cellFilter: 'date', width: 150},
                    {name: 'owner.email', displayName: 'Owner', width: 150}
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
            },

            fetchRelatedVacancies: function(departmentsList) {

                var departmentsIds = [];

                for (var i = 0; i < departmentsList.length; i++) {
                    departmentsIds.push(departmentsList[i].id);
                }

                return vacancyServiceFunctions.fetchAllByDepartment_IdIn(departmentsIds)
                    .success(function(data){
                        return data || [];
                    })
                    .error(function(data, status){
                        alert("Unable to fetch departments ("+status+").");
                    });
            }
        };

        return vacancyServiceFunctions;

    });
})(angular);