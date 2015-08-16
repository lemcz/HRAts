(function(angular){

    var hratsApp = angular.module('HRAts');

    hratsApp.service('GridService', function() {
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
            }
        }
    });

    hratsApp.controller('UiGridController', function($log, $scope, $modal, $location, GridService){

        var collectionName = $scope.pageConfiguration.dataCollectionName || '';

        $scope.gridOptions = GridService.getGridConfiguration() || {};
        $scope.gridOptions.data = collectionName;
        $scope.gridOptions.columnDefs = $scope.pageConfiguration.columnDefs;

        hratsApp.config(function(uiSelectConfig) {
            uiSelectConfig.theme = 'bootstrap';
            uiSelectConfig.resetSearchInput = true;
            uiSelectConfig.appendToBody = true;
        });

        $scope.someGroupFn = function (item) {

            if (item.name[0] >= 'A' && item.name[0] <= 'M')
                return 'From A - M';

            if (item.name[0] >= 'N' && item.name[0] <= 'Z')
                return 'From N - Z';

        };

        $scope.redirect = function (location, row) {
            var rowsId = row.entity.id || 0;

            console.log(rowsId);

            var splitIndex = $location.$$absUrl.lastIndexOf('/protected');
            if (splitIndex !== -1) {
                var baseUrl = $location.$$absUrl.substring(0, splitIndex+'/protected'.length);

                window.location = baseUrl+'/'+location+'/'+rowsId;

            } else {
                alert('Could not relocate to '+location+' to display id '+rowsId+' item\'s details');
            }
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

                if(rows.length > 1) {
                    $scope.gridOptions.multipleSelected = true;
                } else
                {
                    $scope.gridOptions.multipleSelected = false;
                }

                for(var i = 0; i < rows.length; i++) {
                    $scope.row.append(rows[i].entity);
                }
                var msg = 'rows changed ' + rows.length + ' multipleSelected: '+ $scope.gridOptions.multipleSelected;
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