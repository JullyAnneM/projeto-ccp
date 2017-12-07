var app = angular.module('projeto-ccp');

app.controller('FormularioController', ['$rootScope', '$http', function($scope, $http,$sessionStorage) {
	sessionStorage.clear();
	$scope.saveSession = function(nome, cpf, email, cargo, telefone, empresa){
		
		sessionStorage.setItem('nome', nome);
		sessionStorage.setItem('cpf', cpf);
		sessionStorage.setItem('email', email);
		sessionStorage.setItem('cargo', cargo);
		sessionStorage.setItem('telefone', telefone);
		sessionStorage.setItem('empresa', empresa);
		
		window.location.href = "acreditacao.html";
	}
}]);
