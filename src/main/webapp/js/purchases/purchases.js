'use strict';

var app = angular.module('ngdemo.controllers');

app.controller('PurchasesController', ['$scope', '$http', '$routeParams', function ($scope, $http, $routeParams) {
    $scope.bla = 'bla from controller 4';
    $http.get('/ngdemo/rest/user/purchases/'+$routeParams.categoryId)
    .success(function(response) {
    	$scope.purchases = response.purchases;
    	});
}]);