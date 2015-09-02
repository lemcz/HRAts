(function(angular) {

    var hratsApp = angular.module('HRAts', ['ui.bootstrap', 'ui.grid', 'ui.grid.resizeColumns', 'ui.grid.pinning',
                                            'ui.grid.selection', 'ui.grid.moveColumns', 'ui.grid.exporter', 'ui.grid.grouping',
                                            'ngTagsInput', 'ui.select', 'ngFileUpload', 'textAngular', 'uiSwitch']);

    hratsApp.controller('LocationController', function($scope, $location){
        if($location.$$absUrl.lastIndexOf('/candidates') > 0){
            $scope.activeURL = 'candidates';
        } else if($location.$$absUrl.lastIndexOf('/vacancies') > 0){
            $scope.activeURL = 'vacancies';
        } else if($location.$$absUrl.lastIndexOf('/companies') > 0){
            $scope.activeURL = 'companies';
        } else if($location.$$absUrl.lastIndexOf('/contacts') > 0){
            $scope.activeURL = 'contacts';
        } else if($location.$$absUrl.lastIndexOf('/activities') > 0){
            $scope.activeURL = 'activities';
        } else if($location.$$absUrl.lastIndexOf('/candidates') > 0){
            $scope.activeURL = 'candidate';
        } else if($location.$$absUrl.lastIndexOf('/settings') > 0){
            $scope.activeURL = 'settings';
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

    hratsApp.directive("clickToEdit", function () {
        var editorTemplate = '' +
            '<div class="click-to-edit col-md-12">' +
                '<div ng-hide="view.editorEnabled">' +
                    '{{value}} ' +
                    '<span class="glyphicon glyphicon-pencil" ng-click="enableEditor()"></span>' +
                '</div>' +
                '<div ng-show="view.editorEnabled" class="input-group">' +
                    '<input type="text" class="form-control" ng-model="view.editableValue"/> ' +
                    '<span class="glyphicon glyphicon-ok input-group-addon" ng-click="save()"></span>' +
                    '  ' +
                    '<span class="glyphicon glyphicon-remove input-group-addon" ng-click="disableEditor()"></span>' +
                '</div>' +
            '</div>';

        return {
            restrict: "A",
            replace: true,
            template: editorTemplate,
            scope: {
                value: "=clickToEdit",
            },
            link: function (scope, element, attrs) {
                scope.view = {
                    editableValue: scope.value,
                    editorEnabled: false
                };

                scope.enableEditor = function () {
                    scope.view.editorEnabled = true;
                    scope.view.editableValue = scope.value;
                    setTimeout(function () {
                        element.find('input')[0].focus();
                        //element.find('input').focus().select(); // w/ jQuery
                    });
                };

                scope.disableEditor = function () {
                    scope.view.editorEnabled = false;
                };

                scope.save = function () {
                    scope.value = scope.view.editableValue;
                    scope.disableEditor();
                };

            }
        };
    });
})(angular);