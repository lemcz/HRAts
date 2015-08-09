(function(angular) {

    var hratsApp = angular.module('HRAts', ['ui.bootstrap', 'ui.grid', 'ui.grid.resizeColumns', 'ui.grid.pinning',
                                            'ui.grid.selection', 'ui.grid.moveColumns', 'ui.grid.exporter', 'ui.grid.grouping',
                                            'ngTagsInput', 'ui.select', /*'ngSanitize',*/ 'ngFileUpload', 'textAngular']);

    hratsApp.controller('LocationController', function($scope, $location){
        if($location.$$absUrl.lastIndexOf('/candidates') > 0){
            $scope.activeURL = 'candidates';
        } else if($location.$$absUrl.lastIndexOf('/vacancies') > 0){
            $scope.activeURL = 'vacancies';
        } else if($location.$$absUrl.lastIndexOf('/companies') > 0){
            $scope.activeURL = 'companies';
        } else if($location.$$absUrl.lastIndexOf('/contacts') > 0){
            $scope.activeURL = 'contacts';
        } else if($location.$$absUrl.lastIndexOf('/candidates') > 0){
            $scope.activeURL = 'candidates';
        } else if($location.$$absUrl.lastIndexOf('/activities') > 0){
            $scope.activeURL = 'activities';
        } else if($location.$$absUrl.lastIndexOf('/candidate') > 0){
            $scope.activeURL = 'candidate';
        }
        else{
            $scope.activeURL = 'home';
        }
    });

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
})(angular);