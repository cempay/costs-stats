'use strict';

/* Login Controller */

var app = angular.module('ngdemo.controllers');

app.controller('LoginController', ['$scope', '$http', function ($scope, $http) {
	$scope.authFlag = '0';
    $http.get('/ngdemo/rest/login')
    .success(function(response) {
		$scope.locres = response.locres;
    	$scope.checkLogin = function(){
    		//alert('try to login2');
			//todo change btoa for IE9
			/*if (!$scope.name) {
				$scope.name = 'q';
				$scope.p = '1';
			}*/
    		var base64 = btoa($scope.name + ':' + $scope.p);
    		var authHeader = 'Basic ' + base64;
    		var req = {
    				 method: 'GET',
    				 url: '/ngdemo/rest/user/checkLogin',
    				 headers: {
    				   'Authorization': authHeader
    				 }
    		}
    		$http(req).success(function(resp){
    			$http.defaults.headers.common.Authorization = authHeader;				
        		location.href = '#/categories';
    		})
    		.error(function(data, status, headers, config){
    			if (status == 401)
    				alert('not authorized!');
    			else
    				alert('other error!');    			
    		});

    	}
    })
	.error(function(data, status, headers, config) {
		if (status == 401)
			alert('not authorized_1!');
		else
			alert('other error!_1');
	});
}]);