'use strict';

var app = angular.module('ngdemo.controllers');

//todo: Сделать post-запрос
app.controller('NewCategoryController', ['$scope', '$http', '$routeParams', function ($scope, $http, $routeParams) {
    var path = $routeParams.categoryName ? '/'+$routeParams.categoryName : '';
    	
    $http.get('/ngdemo/rest/user/newcategory' + path)
    .success(function(response) {
    	$scope.data = response
    	$scope.send = function(){
    		if ($scope.isEmpty($scope.categoryName)){
    			alert(response.locres.CategoryNameEmptyAlert);
    		} else {
    			location.href = '#/newcategory/'+$scope.categoryName;
    		}
    	}
    	$scope.isEmpty = function (str) {
    	    return (!str || 0 === str.trim().length);
    	} 
    	
    	if (response.info && response.info.success) {
    		alert(response.info.success);
    	} else if (response.errors && response.errors.length > 0) {
    		var label = '';
			for (var i = 0, len = response.errors.length; i < len; i++) {
				label = label + '- ' + response.errors[i] + '\n';  
			}
			alert(label);
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