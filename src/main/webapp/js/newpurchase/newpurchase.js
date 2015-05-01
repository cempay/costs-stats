'use strict';

var app = angular.module('ngdemo.controllers');

//todo: Сделать post-запрос
app.controller('NewPurchaseController', ['$scope', '$http', '$routeParams', function ($scope, $http, $routeParams) {
    var path = $routeParams.purchaseName ? ('/' + $routeParams.categoryType
			+ '/' + $routeParams.payDate
			+ '/' + $routeParams.purchaseName 
			+ '/' + $routeParams.price) : '';
    	
    $http.get('/ngdemo/rest/user/newpurchase' + path)
    .success(function(response) {
    	$scope.data = response
    	$scope.locres = response.locres
    	$scope.send = function(){
    		if ($scope.isEmpty($scope.purchaseName)){    			
    			alert($scope.locres.InvalidFieldValue.replace("%s", $scope.locres.PurchaseName));
    		}
			else if ($scope.isEmpty($scope.price)) {
				alert($scope.locres.InvalidFieldValue.replace("%s", $scope.locres.Price));
			} 
			else if ($scope.isEmpty($scope.payDate)) {
				alert($scope.locres.InvalidFieldValue.replace("%s", $scope.locres.PayDate));
			}
    		else {
    			console.log('NewPurchaseController: befor href, categoryType: '+ $scope.categoryType.id);
    			console.log('NewPurchaseController: befor href, payDate: '+ $scope.payDate);
    			console.log('NewPurchaseController: befor href, purchaseName: '+ $scope.purchaseName);
    			console.log('NewPurchaseController: befor href, price: '+ $scope.price);
				var link = '#/newpurchase' 
    				+ '/' + $scope.categoryType.id
					+ '/' + $scope.payDate
    				+ '/' + $scope.purchaseName 
    				+ '/' + $scope.price;
				console.log('NewPurchaseController: befor href, link='+link);
    			location.href = link;
    			console.log('NewPurchaseController: after href');
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