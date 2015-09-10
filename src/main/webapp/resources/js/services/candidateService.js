(function(angular){

    var hratsApp = angular.module('HRAts');

    hratsApp.service('CandidateService', function ($http, uiGridConstants){

        var baseUrl = 'http://localhost:8080/HRAts/protected/candidates/';

        return {

            fetchAll: function () {
                return $http.get(baseUrl);
            },

            fetchAllByVacancyId: function(vacancyId) {
                return $http.get(baseUrl + '?search=vacancy&id=' + vacancyId);
            },

            fetchAllNotAssignedToVacancy: function(vacancyId) {
                return $http.get(baseUrl + 'notInVacancy/' + vacancyId);
            },

            fetchById: function(candidateId) {
                return $http.get(baseUrl + candidateId)
            },

            createRow: function(candidateData){
                return $http.post(baseUrl, candidateData);
            },

            updateRow: function(candidateData) {
                return $http.put(baseUrl+'/'+candidateData.id, candidateData);
            },

            removeRow: function(candidateId){
                return $http.delete(candidateId);
            },

            getColumnDefs: function() {
                return [
                    { name:'id', width:50 },
                    { name:'name', cellTemplate: '<div class="ui-grid-cell-contents"><a data-toggle="modal" ng-click="grid.appScope.redirect(row.entity, \'candidates\', row.entity)" role="button" >{{COL_FIELD}}</a></div>', width:100 },
                    { name:'middleName', width:100 },
                    { name:'lastName', width:100 },
                    { name:'candidateInformation.address', displayName: "Address", width:150},
                    { name:'candidateInformation.city', displayName: "City", width:150},
                    { name:'candidateInformation.state', displayName: "State", width:150},
                    { name:'candidateInformation.zipcode', displayName: "Zip Code", width:150},
                    { name:'phoneNumber', width:150},
                    { name:'email', width:100, cellTemplate: '<div class="ui-grid-cell-contents text-center"><a href="mailto:{{ COL_FIELD }}">{{ COL_FIELD }}</a></div>'},
                    { name:'phoneNumber', width:200},
                    { name:'note', width:300 },
                    { name:'dateEntered', cellFilter:'date: \'HH:MM:ss dd/MM/yyyy\'', width:150 },
                    { name:'dateModified', cellFilter:'date: \'HH:MM:ss dd/MM/yyyy\'', width:150, sort: {direction: uiGridConstants.DESC} },
                    { name:'owner.email', displayName:'Owner', cellTemplate: '<div class="ui-grid-cell-contents"><a href="mailto:{{ COL_FIELD }}">{{ COL_FIELD }}</a></div>', width:150 }
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
})(angular);