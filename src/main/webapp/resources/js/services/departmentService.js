(function(angular) {
    var hratsApp = angular.module('HRAts');

    hratsApp.service('DepartmentService', function ($http){

        var baseUrl = 'http://localhost:8080/HRAts/protected/departments';

        var departmentServiceFunctions = {
             fetchAll: function() {
                 return $http.get(baseUrl);
             },

             fetchAllByName: function() {
                 return $http.get(baseUrl+'/byName');
             },

             fetchAllByCompany: function(companyId) {
                 return $http.get(baseUrl+'/?companyId='+companyId);
             },

             fetchAllByCompany_IdIn: function(companiesIds) {
                 var req = {
                     method: 'PUT',
                     url: baseUrl+'/perCompanies',
                     headers: {
                         type: 'list'
                     },
                     data: companiesIds
                 };

                 return $http(req);
             },

             fetchRelatedDepartments: function(companiesList) {

                 var companiesIds = [];

                 for (var i = 0; i < companiesList.length; i++) {
                     companiesIds.push(companiesList[i].id);
                 }

                 return departmentServiceFunctions.fetchAllByCompany_IdIn(companiesIds)
                     .success(function(data){
                        return data || [];
                     })
                     .error(function(data, status){
                         alert("Unable to fetch departments ("+status+").");
                     });
             }
         }
        
        return departmentServiceFunctions;
    });
})(angular);