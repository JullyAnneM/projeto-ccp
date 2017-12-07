var app = angular.module('projeto-ccp');
app.controller('ProdutosController', ['$rootScope', '$http', function($scope, $http) {
	$http({
		method: "GET",
		url: URL + "/vdb/Dados/json/Produto"
	
	}).then(function (response){
		$scope.produtos = response.data.produtos;
		console.log("Dados recebidos NavController Produto!");
	});
}]);