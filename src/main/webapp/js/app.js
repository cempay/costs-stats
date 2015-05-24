'use strict';

// Declare app level module which depends on filters, and services
angular.module('ngdemo', ['ngRoute', 'ui.bootstrap', 'ngdemo.filters', 'ngdemo.services', 'ngdemo.directives', 'ngdemo.controllers']).
    config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/view1', {templateUrl: 'partials/partial1.html', controller: 'MyCtrl1'});
        $routeProvider.when('/login', {templateUrl: 'partials/login.html', controller: 'LoginController'});
        $routeProvider.when('/categories', {templateUrl: 'partials/categories.html', controller: 'CategoriesController'});
        $routeProvider.when('/purchases/:categoryId', {templateUrl: 'partials/purchases.html', controller: 'PurchasesController'});
        $routeProvider.when('/newcategory', {templateUrl: 'partials/newcategory.html', controller: 'NewCategoryController'});
        $routeProvider.when('/newcategory/:categoryName', {templateUrl: 'partials/newcategory.html', controller: 'NewCategoryController'});
        $routeProvider.when('/newpurchase', {templateUrl: 'partials/newpurchase.html', controller: 'NewPurchaseController'});
        $routeProvider.when('/newpurchase/:categoryType/:payDate/:purchaseName/:price/:purchId', {templateUrl: 'partials/newpurchase.html', controller: 'NewPurchaseController'});
        $routeProvider.when('/newpurchase/edit/:purchaseId', {templateUrl: 'partials/newpurchase.html', controller: 'NewPurchaseController'});
        $routeProvider.when('/statistic', {templateUrl: 'partials/statistic.html', controller: 'StatisticController'});
        $routeProvider.otherwise({redirectTo: '/login'});
    }]);
