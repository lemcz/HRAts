function Hello($scope, $http) {
    $http.get('http://rest-service.guides.spring.io/greeting'). //json with greeting
        success(function(data) {
            $scope.greeting = data;
        });
}