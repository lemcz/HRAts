(function(angular){

    var hratsApp = angular.module('HRAts');

    hratsApp.service('CompanyService', function ($http){

        var baseUrl = 'http://localhost:8080/HRAts/protected/companies/';

        return {

            fetchAll : function () {
                return $http.get(baseUrl);
            },

            fetchById : function(companyId) {
                return $http.get(baseUrl + companyId)
            },

            createRow : function(companyData){
                return $http.post(baseUrl, companyData);
            },

            updateRow : function(companyData) {
                return $http.put(baseUrl+companyData.id, companyData);
            },

            removeRow : function(companyId){
                return $http.delete(companyId);
            },

            getColumnDefs: function() {
                return [
                    { name:'id', width:50 },
                    { name:'name', width:100 },
                    { name:'website', width:100, cellTemplate: '<div class="text-center"><a href="{{ COL_FIELD }}">{{ COL_FIELD }}</a></div>'},
                    { name:'phone', width:200},
                    { name:'country', width:200},
                    { name:'about', width:300 },
                    { name:'dateEntered', cellFilter:'date', width:150 },
                    { name:'dateModified', cellFilter:'date', width:150 },
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
        }
    });
})(angular);