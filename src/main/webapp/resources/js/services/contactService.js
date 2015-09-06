(function(angular){

    var hratsApp = angular.module('HRAts');

    hratsApp.service('ContactService', function ($http, uiGridConstants){

        var baseUrl = 'http://localhost:8080/HRAts/protected/contacts/';

        return {

            fetchAllContacts: function() {
                return $http.get(baseUrl);
            },

            fetchById: function(contactId) {
                return $http.get(baseUrl + '/' + contactId);
            },

            fetchAllByCompanyId: function(companyId) {
                return $http.get(baseUrl + '?search=company&id=' + companyId);
            },

            fetchByVacancyId: function(vacancyId) {
                return $http.get(baseUrl + '/perVacancy?id='+vacancyId);
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
})(angular);