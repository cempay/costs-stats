'use strict';

/* Category Controller */

var app = angular.module('ngdemo.controllers');

app.controller('CategoriesController', ['$scope', '$http', function ($scope, $http) {
    $http.get('/ngdemo/rest/user/categories')
        .success(function (response) {
            $scope.authFlag = '1';
            $scope.locres = response.locres;
            $scope.categories = response.categories;

            if ($scope.categories){
                $scope.categories[0].open = true;
                $scope.categories[0].frash = true;
            }
            $scope.oneAtATime = true;

            $scope.getNestedPurchasesByCategory = function (categ2, index) {
                if (!$scope.categories[index].open)
                    return;
                if ($scope.categories[index].frash)
                    return;
                $http.get('/ngdemo/rest/user/purchases/'+categ2.id)
                    .success(function (resp2) {
                        $scope.categories[index].purchases = resp2.purchases;
                        $scope.categories[index].frash = true;
                    })
                    .error(function (data2, status2, headers2, config2) {
                        if (status2 == 401) {
                            alert('not authorized2!');
                        }
                        else
                            alert('other error2!');
                    });
            };

            $scope.editPurchase = function (purchase) {
                location.href = '#/newpurchase/edit/' + purchase.id;
            };

            $scope.getPurchasesByCategory = function (categ) {
                location.href = '#/purchases/' + categ.id;
            }
        })
        .error(function (data, status, headers, config) {
            if (status == 401) {
                alert('not authorized!');
                location.href = '#/login';
            }
            else
                alert('other error!');
        });
}]);