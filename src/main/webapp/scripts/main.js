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


        $http(request)
            .success(function (jsonData) {
            	$scope.produtos = jsonData.produtos.produtos;
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


        $http(request)
            .success(function (jsonData) {
                $scope.perfil = jsonData.perfil.perfil;
            })
            .error(function () {

        });
	
});

app.controller('buttonCtrl', function($scope, $http) {
	
	
});