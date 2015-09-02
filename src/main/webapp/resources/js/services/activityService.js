(function(angular) {
    var hratsApp = angular.module('HRAts');

    hratsApp.service('ActivityService', function ($http, uiGridConstants){

        var baseUrl = 'http://localhost:8080/HRAts/protected/activities/';

        return {
            paginationOptions : function(){
                return [10, 15, 25, 50, 100];
            },

            fetchAll : function () {
                return $http.get(baseUrl);
            },

            fetchById : function(activityId) {
                return $http.get(baseUrl + activityId)
            },

            fetchByCandidateId : function(candidateId) {
                return $http.get(baseUrl + candidateId);
            },

            createRow : function(activityData){
                return $http.post(baseUrl, activityData);
            },

            updateRow : function(activityData) {
                return $http.put(baseUrl+activityData.id, activityData);
            },

            removeRow : function(activityId){
                return $http.delete(activityId);
            },

            getColumnDefs: function() {
                return [
                    { name:'id', width:50 },
                    { name:'activityType.name', width:100 },
                    { name:'candidate.email', width:100 },
                    { name:'note', width:300 },
                    { name:'dateEntered', cellFilter:'date: \'HH:MM:ss dd/MM/yyyy\'', width:150, sort: {direction: uiGridConstants.DESC} },
                    { name:'owner.email', displayName:'Owner', width:150 }
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
                }

                return req;
            }
        };
    });
})(angular);