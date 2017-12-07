var app = angular.module('projeto-ccp');


app.controller('EmpresaController', ['$rootScope', '$http', function($scope, $http) {
	$http({
		method: "GET",
		url: URL + "/vdb/Dados/json/Empresa"
	
	}).then(function (response){
		$scope.empresas = response.data.empresas;
	});
	
	$scope.teste = function (emp) {
		$scope.empresa = emp;
		sessionStorage.setItem('id_empresa', emp.id_empresa);
	}
	

}]);
