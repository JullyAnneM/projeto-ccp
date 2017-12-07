var app = angular.module('projeto-ccp');

app.controller('botaoController', function($scope, $http) {
	
	
	
	$scope.saveSession = function(acreditacao){
		
		$scope.acreditacoes = [];
		
		for (var i = 0; i < acreditacao.length; i++) {
			$scope.acreditacoes.push(acreditacao[i]);
		}
			sessionStorage.setItem('acreditacoes', JSON.stringify($scope.acreditacoes));
			window.location.href = "confirmacao.html";
	}
	$scope.cancelAction = function(){
		if(confirm('Tem certeza que deseja cancelar o envio do formulário?')){
			window.location.href = "/projeto-ccp-0.0.1-SNAPSHOT/";
		} else {
			
		}
	}
});