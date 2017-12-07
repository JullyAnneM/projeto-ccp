var app = angular.module('projeto-ccp');

app.controller('PerfilController', ['$rootScope', '$http', function($scope, $http) {
	$http({
		method: "GET",
		url: URL + "/vdb/Dados/json/Perfil"
	
	}).then(function (response){
		$scope.perfis = response.data.perfil;
		console.log("Dados recebidos Controller Perfil!");
	});
}]);