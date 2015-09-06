(function(angular){

    var hratsApp = angular.module('HRAts');

    hratsApp.service('GridService', function($location) {
        var gridConfiguration = {
            enableColumnResizing: true,
            enableFiltering: true,
            enableGridMenu: true,
            enableRowSelection: true,
            enableFullRowSelection: true,
            enableRowHeaderSelection: true,
            multiSelect: true,
            showGridFooter: true,
            showColumnFooter: true,
            fastWatch: true,
            multipleSelected: true,

            rowIdentity : function(row) {
                return row.id;
            },
            getRowIdentity : function(row) {
                return row.id;
            }
        };

        return {
            getGridConfiguration: function() {
                return gridConfiguration;
            },

            redirect: function(row, endpoint) {
                var rowsId = row.id || 0;

                var baseUrl = $location.absUrl();
                var trimIndex = baseUrl.indexOf('protected');
                baseUrl = baseUrl.substr(0,trimIndex+'protected'.length);

                window.location = baseUrl+'/'+endpoint+'/'+rowsId;
            }
        }
    });

    hratsApp.controller('UiGridController', function($scope, $modal, $location, GridService){

        var pageConfiguration = $scope.pageConfiguration || {};
        var collectionName = pageConfiguration.dataCollectionName || '';

        $scope.gridOptions = GridService.getGridConfiguration() || {};
        $scope.gridOptions.data = collectionName;
        $scope.gridOptions.columnDefs = pageConfiguration.columnDefs || '';

        $scope.redirect = function (row, endpoint) {
            GridService.redirect(row, endpoint);
        };

        $scope.gridOptions.onRegisterApi = function(gridApi){
            //set gridApi on scope
            $scope.gridApi = gridApi;
            gridApi.selection.on.rowSelectionChanged($scope,function(row){
                $scope.gridOptions.selectedRows = $scope.gridApi.selection.getSelectedRows();
                if ($scope.gridOptions.selectedRows.length !== 1) {
                    $scope.gridOptions.multipleSelected = true;
                    $scope.row = {};
                } else {
                    $scope.gridOptions.multipleSelected = false;
                    $scope.row = row.entity;
                }
            });

            gridApi.selection.on.rowSelectionChangedBatch($scope,function(rows){

                $scope.gridOptions.multipleSelected = rows.length > 1;

                for(var i = 0; i < rows.length; i++) {
                    $scope.row.append(rows[i].entity);
                }
            });
        };

        $scope.openModal = function(modalTemplate, row){
            var modalInstance = $modal.open({
                animation: false,
                templateUrl: modalTemplate,
                controller: 'ModalInstanceController',
                size: 'lg',
                backdrop: true,
                scope: $scope,
                resolve: {
                    row: function(){
                        return {
                            modalName: modalTemplate,
                            data: row || {}
                        };
                    }
                }
            });
        };
    });
})(angular);