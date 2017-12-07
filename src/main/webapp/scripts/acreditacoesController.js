
var app = angular.module('projeto-ccp');

function ordenarPorPropriedade(array, propriedade) {
    return array.sort(function(a, b) {
        var x = a[propriedade]; var y = b[propriedade];
        return ((x < y) ? -1 : ((x > y) ? 1 : 0));
    });
   
}
app.controller('AcreditacoesController', ['$rootScope', '$http', function($scope, $http) {
	$scope.arrayDeAcreditacoesPerfil = [];
	$scope.arrayDeAcreditacoesProduto = [];
	checaSessionStorage("Redirecionando para página inicial.");
		
		$scope.stateChangedPerfil = function(state) {
		$scope.acreditacoesSelecionadas = [];
		$scope.ac = [];
			

			$scope.id = state.identificador;
	
			$http({
				method: "GET",
				url: URL + "/vdb/Dados/json/Acreditacao-Perfil/" + $scope.id 
			
			}).then(function (response){
					
				if(state.id_perfil){
					if (angular.isArray(response.data.acreditacao)){
						for (var i = 0; i < response.data.acreditacao.length; i++){
							$scope.arrayDeAcreditacoesPerfil.push(response.data.acreditacao[i]);
						}
						
					} else {
						$scope.arrayDeAcreditacoesPerfil.push(response.data.acreditacao);	
						
					}
					
					for (var i = 0; i < $scope.arrayDeAcreditacoesPerfil.length;i++){
						var objAtual = $scope.arrayDeAcreditacoesPerfil[i];
						var cont = 0;
						for (var j = 0; j < $scope.arrayDeAcreditacoesPerfil.length; j++ ){
							if (objAtual.id_acreditacao == $scope.arrayDeAcreditacoesPerfil[j].id_acreditacao){
								cont++;
							}
							if(cont > 1){
								$scope.arrayDeAcreditacoesPerfil.splice(j,1);
							}
						}
					}
				} else {
					if(angular.isArray(response.data.acreditacao)){
						var cont = 0;
						for (var i = 0; i < response.data.acreditacao.length;i++){
							
							for (var j = 0; j < $scope.arrayDeAcreditacoesPerfil.length; j++){
								if (response.data.acreditacao[i].id_acreditacao == $scope.arrayDeAcreditacoesPerfil[j].id_acreditacao){
									$scope.arrayDeAcreditacoesPerfil.splice(j,1);
								}
							}
						}
					} else {
						for (var i = 0; i < $scope.arrayDeAcreditacoesPerfil.length; i++){
							if (response.data.acreditacao.id_acreditacao == $scope.arrayDeAcreditacoesPerfil[i].id_acreditacao){
								$scope.arrayDeAcreditacoesPerfil.splice(i,1);
							}
						}
					}
					
				}
				
				
				$scope.ac = $scope.compararAcreditacoesERetornarSoAsEmComum();
				
			});	
		}
	
	
	$scope.stateChangedProduto = function(state) {
		$scope.id = state.identificador;
		$scope.acreditacoesSelecionadas = [];
		$scope.ac = [];

		
		$http({
			method: "GET",
			url: URL + "/vdb/Dados/json/Acreditacao-Produto/" + $scope.id 
		
		}).then(function (response){
			
			if(state.id_produto){
				if(response.data.acreditacao == undefined){
					alert("Este produto não contém acreditações relacionadas");
					return 0;
				}
				if (angular.isArray(response.data.acreditacao)){
					for (var i = 0; i < response.data.acreditacao.length; i++){
						$scope.arrayDeAcreditacoesProduto.push(response.data.acreditacao[i]);
					}
				} else {
					$scope.arrayDeAcreditacoesProduto.push(response.data.acreditacao);	
				}
				for (var i = 0; i < $scope.arrayDeAcreditacoesProduto.length;i++){
					var objAtual = $scope.arrayDeAcreditacoesProduto[i];
					var cont = 0;
					for (var j = 0; j < $scope.arrayDeAcreditacoesProduto.length; j++ ){
						if (objAtual.id_acreditacao == $scope.arrayDeAcreditacoesProduto[j].id_acreditacao){
							cont++;
						}
						if(cont > 1){
							$scope.arrayDeAcreditacoesProduto.splice(j,1);
						}
					}
				}
			} else {
				if(angular.isArray(response.data.acreditacao)){
					var cont = 0;
					for (var i = 0; i < response.data.acreditacao.length;i++){
						
						for (var j = 0; j < $scope.arrayDeAcreditacoesProduto.length; j++){
							if (response.data.acreditacao[i].id_acreditacao == $scope.arrayDeAcreditacoesProduto[j].id_acreditacao){
								$scope.arrayDeAcreditacoesProduto.splice(j,1);
							}
						}
					}
				} else {
					for (var i = 0; i < $scope.arrayDeAcreditacoesProduto.length; i++){
						if (response.data.acreditacao.id_acreditacao == $scope.arrayDeAcreditacoesProduto[i].id_acreditacao){
							$scope.arrayDeAcreditacoesProduto.splice(i,1);
						}
					}
				}
				
			}
			$scope.ac = $scope.compararAcreditacoesERetornarSoAsEmComum();
			
		});
	}
	
	$scope.compararAcreditacoesERetornarSoAsEmComum = function () {
		var arrayFinal = [];
		if ($scope.arrayDeAcreditacoesPerfil.length == 0 && $scope.arrayDeAcreditacoesProduto.length == 0){
			return arrayFinal;
		} else if ($scope.arrayDeAcreditacoesPerfil.length == 0){
			
			arrayFinal = $scope.arrayDeAcreditacoesProduto;
			arrayFinal = ordenarPorPropriedade(arrayFinal,"nome_acreditacao"); 
			arrayFinal = ordenarPorPropriedade(arrayFinal,"id_perfil"); 
			return arrayFinal;

		} else if ($scope.arrayDeAcreditacoesProduto.length == 0){
			arrayFinal = $scope.arrayDeAcreditacoesPerfil;
			arrayFinal = ordenarPorPropriedade(arrayFinal,"nome_acreditacao"); 
			arrayFinal = ordenarPorPropriedade(arrayFinal,"id_perfil"); 
			return arrayFinal;
		} else {
			for(var i = 0; i < $scope.arrayDeAcreditacoesPerfil.length; i++){
				
				for(var j = 0; j < $scope.arrayDeAcreditacoesProduto.length; j++){
					if($scope.arrayDeAcreditacoesPerfil[i].id_acreditacao == $scope.arrayDeAcreditacoesProduto[j].id_acreditacao){
						arrayFinal.push($scope.arrayDeAcreditacoesPerfil[i]);

					}
				}
			}
			arrayFinal = ordenarPorPropriedade(arrayFinal,"nome_acreditacao"); 
			arrayFinal = ordenarPorPropriedade(arrayFinal,"id_perfil"); 
			return arrayFinal;
			}
		
	}
	
	$scope.acreditacoesSelecionadas = [];
	$scope.adicionaAcreditacao = function (event, acreditacaoSelecionada){
		console.log(event);
		if(event.target.checked){
			$scope.acreditacoesSelecionadas.push(acreditacaoSelecionada);
			console.log($scope.acreditacoesSelecionadas);
		} else {
			$scope.acreditacoesSelecionadas.forEach(function(acreditacaoAtual){
				if(acreditacaoAtual.id_acreditacao == acreditacaoSelecionada.id_acreditacao){
					console.log("Acreditacao removida: " + acreditacaoSelecionada.id_acreditacao);
					var index = $scope.acreditacoesSelecionadas.indexOf(acreditacaoAtual);
					$scope.acreditacoesSelecionadas.splice(index,1);
				}
				
			})
		}
		console.log($scope.acreditacoesSelecionadas.length);
	}

	
}]);
