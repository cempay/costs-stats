'use strict';

/* Controllers */


var app = angular.module('ngdemo.controllers', ['angularCharts']);


// Clear browser cache (in development mode)
//
// http://stackoverflow.com/questions/14718826/angularjs-disable-partial-caching-on-dev-machine
app.run(function ($rootScope, $templateCache, $http) {
    $rootScope.$on('$viewContentLoaded', function () {
        $templateCache.removeAll();
    });
	//$http.defaults.headers.common.Authorization = "Basic YWRtaW46YWRtaW4=";
});

app.controller('MyCtrl1', ['$scope', '$http', 'UserFactory', function ($scope, $http, UserFactory) {
    $scope.bla = 'bla from controller 2';
    $http.get('/ngdemo/rest/users')
    .success(function(response) {
    	$scope.users = response;
    	});
}]);