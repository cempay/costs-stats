'use strict';

/* Category Controller */

var app = angular.module('ngdemo.controllers');

app.controller('CategoriesController', ['$scope', '$http', function ($scope, $http) {
    $http.get('/ngdemo/rest/user/categories')
    .success(function(response) {
		$scope.authFlag = '1';
    	$scope.locres = response.locres;
    	$scope.categories = response.categories;    	
    	$scope.getPurchasesByCategory = function(categ) {
    		location.href = '#/purchases/'+categ.id;
    	}
    	})
	.error(function(data, status, headers, config) {
		if (status == 401) {
			alert('not authorized!');
			location.href = '#/login';
		}
		else
			alert('other error!');
	});
}]);