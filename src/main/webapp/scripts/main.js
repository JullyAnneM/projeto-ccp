/**
 * 
 */

var app = angular.module('projeto-ccp', []);

app.controller('NavController', function($scope, $http) {

	var request = {
            method: 'get',
            url: 'json/products.json',
            dataType: 'json',
            contentType: "application/json"
        };

        $scope.products = new Array;

        $http(request)
            .success(function (jsonData) {
                $scope.products = jsonData;
                $scope.list = $scope.products;
            })
            .error(function () {

            });
    
});

app.controller('Controller', function($scope, $http) {
	
	var request = {
            method: 'get',
            url: 'json/profile.json',
            dataType: 'json',
            contentType: "application/json"
        };

        $scope.profile = new Array;

        $http(request)
            .success(function (jsonData) {
                $scope.profile = jsonData;
                $scope.list = $scope.profile;
            })
            .error(function () {

        });
	
});