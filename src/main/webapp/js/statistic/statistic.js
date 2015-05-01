'use strict';

var app = angular.module('ngdemo.controllers');

//Статистика категорий
app.controller('StatisticController', ['$scope', '$http', function ($scope, $http) {
    $http.get('/ngdemo/rest/user/statistic')
    .success(function(response) {
    	$scope.categoryInfoList = response.categoryInfoList;
/*    	$scope.getPurchasesByCategory = function(categ) {
    		alert('getPurchasesByCategory: ' + categ.id);
    		location.href = '#/purchases/'+categ.id;
    	}*/	
		$scope.config = {
			title: 'Статистика по месяцам',
			tooltips: true,
			labels: false,
			mouseover: function() {},
			mouseout: function() {},
			click: function() {},
			legend: {
			  display: true,
			  //could be 'left, right'
			  position: 'left'
			}
		};
		$scope.chartType = 'bar';
/*		$scope.Fchart  = {
			series: ['Employer'],
			data: [
			  {x:"abc",y:[450]},{x:"bcd",y:[500]},{x:"cds",y:[350]}
			  ]
		};*/
		$scope.Fchart  = {
				series: response.series,
				data: response.data
			};
    		
    	
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