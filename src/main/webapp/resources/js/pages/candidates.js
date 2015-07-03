(function(angular) {

    var hratsApp = angular.module('HRAts');

    hratsApp.controller('CandidatesController', ['$scope', '$http', function($scope, $http){

    $scope.pageToGet = 0;

    $scope.state = 'busy';

    $scope.lastAction = '';

    $scope.url = "/HRAts/protected/candidates/";

    $scope.errorOnSubmit = false;
    $scope.errorIllegalAccess = false;
    $scope.displayMessageToUser = false;
    $scope.displayValidationError = false;
    $scope.displaySearchMessage = false;
    $scope.displaySearchButton = false;
    $scope.displayCreateCandidateButton = false;

    $scope.candidate = {}

    $scope.searchFor = ""

    $scope.getCandidateList = function () {
        var url = $scope.url;
        $scope.lastAction = 'list';

        $scope.startDialogAjaxRequest();

        var config = {params: {page: $scope.pageToGet}};

        $http.get(url, config)
            .success(function (data) {
                $scope.finishAjaxCallOnSuccess(data, null, false);
            })
            .error(function () {
                $scope.state = 'error';
                $scope.displayCreateCandidateButton = false;
            });
    }

    $scope.populateTable = function (data) {
        if (data.pagesCount > 0) {
            $scope.state = 'list';

            $scope.page = {
                source: data.candidates,
                currentPage: $scope.pageToGet,
                pagesCount: data.pagesCount,
                totalCandidates: data.totalCandidates
            };

            if ($scope.page.pagesCount <= $scope.page.currentPage) {
                $scope.pageToGet = $scope.page.pagesCount - 1;
                $scope.page.currentPage = $scope.page.pagesCount - 1;
            }

            $scope.displayCreateCandidateButton = true;
            $scope.displaySearchButton = true;
        } else {
            $scope.state = 'noresult';
            $scope.displayCreateCandidateButton = true;

            if (!$scope.searchFor) {
                $scope.displaySearchButton = false;
            }
        }

        if (data.actionMessage || data.searchMessage) {
            $scope.displayMessageToUser = $scope.lastAction != 'search';

            $scope.page.actionMessage = data.actionMessage;
            $scope.page.searchMessage = data.searchMessage;
        } else {
            $scope.displayMessageToUser = false;
        }
    }

    $scope.changePage = function (page) {
        $scope.pageToGet = page;

        if ($scope.searchFor) {
            $scope.searchCandidate($scope.searchFor, true);
        } else {
            $scope.getCandidateList();
        }
    };

    $scope.exit = function (modalId) {
        $(modalId).modal('hide');

        $scope.candidate = {};
        $scope.errorOnSubmit = false;
        $scope.errorIllegalAccess = false;
        $scope.displayValidationError = false;
    }

    $scope.finishAjaxCallOnSuccess = function (data, modalId, isPagination) {
        $scope.populateTable(data);

        if (!isPagination) {
            if (modalId) {
                $scope.exit(modalId);
            }
        }
        $scope.lastAction = '';
    }

    $scope.startDialogAjaxRequest = function () {
        $scope.displayValidationError = false;
        $scope.previousState = $scope.state;
        $scope.state = 'busy';
    }

    $scope.handleErrorInDialogs = function (status) {
        $scope.state = $scope.previousState;

        // illegal access
        if (status == 403) {
            $scope.errorIllegalAccess = true;
            return;
        }

        $scope.errorOnSubmit = true;
        $scope.lastAction = '';
    }

    $scope.addSearchParametersIfNeeded = function (config, isPagination) {
        if (!config.params) {
            config.params = {};
        }

        config.params.page = $scope.pageToGet;

        if ($scope.searchFor) {
            config.params.searchFor = $scope.searchFor;
        }
    }

    $scope.resetCandidate = function () {
        $scope.candidate = {};
    };

    $scope.createCandidate = function (newCandidateForm) {
        if (!newCandidateForm.$valid) {
            $scope.displayValidationError = true;
            return;
        }

        $scope.lastAction = 'create';

        var url = $scope.url;

        var config = {headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}};

        $scope.addSearchParametersIfNeeded(config, false);

        $scope.startDialogAjaxRequest();

        $http.post(url, $.param($scope.candidate), config)
            .success(function (data) {
                $scope.finishAjaxCallOnSuccess(data, "#addCandidatesModal", false);
            })
            .error(function (data, status, headers, config) {
                $scope.handleErrorInDialogs(status);
            });
    };

    $scope.selectedCandidate = function (candidate) {
        var selectedCandidate = angular.copy(candidate);
        $scope.candidate = selectedCandidate;
    }

    $scope.updateCandidate = function (updateCandidateForm) {
        if (!updateCandidateForm.$valid) {
            $scope.displayValidationError = true;
            return;
        }

        $scope.lastAction = 'update';

        var url = $scope.url + $scope.candidate.id;

        $scope.startDialogAjaxRequest();

        var config = {};

        $scope.addSearchParametersIfNeeded(config, false);

        $http.put(url, $scope.candidate, config)
            .success(function (data) {
                $scope.finishAjaxCallOnSuccess(data, "#updateCandidatesModal", false);
            })
            .error(function (data, status, headers, config) {
                $scope.handleErrorInDialogs(status);
            });
    };

    $scope.searchCandidate = function (searchCandidateForm, isPagination) {
        if (!($scope.searchFor) && (!searchCandidateForm.$valid)) {
            $scope.displayValidationError = true;
            return;
        }

        $scope.lastAction = 'search';

        var url = $scope.url + $scope.searchFor;

        $scope.startDialogAjaxRequest();

        var config = {};

        if ($scope.searchFor) {
            $scope.addSearchParametersIfNeeded(config, isPagination);
        }

        $http.get(url, config)
            .success(function (data) {
                $scope.finishAjaxCallOnSuccess(data, "#searchCandidatesModal", isPagination);
                $scope.displaySearchMessage = true;
            })
            .error(function (data, status, headers, config) {
                $scope.handleErrorInDialogs(status);
            });
    };

    $scope.deleteCandidate = function () {
        $scope.lastAction = 'delete';

        var url = $scope.url + $scope.candidate.id;

        $scope.startDialogAjaxRequest();

        var params = {searchFor: $scope.searchFor, page: $scope.pageToGet};

        $http({
            method: 'DELETE',
            url: url,
            params: params
        }).success(function (data) {
            $scope.resetCandidate();
            $scope.finishAjaxCallOnSuccess(data, "#deleteCandidatesModal", false);
        }).error(function (data, status, headers, config) {
            $scope.handleErrorInDialogs(status);
        });
    };

    $scope.resetSearch = function () {
        $scope.searchFor = "";
        $scope.pageToGet = 0;
        $scope.getCandidateList();
        $scope.displaySearchMessage = false;
    }

    $scope.getCandidateList();
}]);
})(angular);