app.controller('ConfirmacaoFormularioController',['$rootScope','$http','$q','$timeout',function($scope, $http, $q,$timeout,$window) {
			checaSessionStorage("Preencha o formulário antes de confirmar.");
			$scope.estaAtivo = true;
			$scope.acreditacoes = JSON.parse(sessionStorage.getItem('acreditacoes'));
			$scope.podeImprimir = false;
			$scope.flag = true;


			$scope.parceiro = {};
			$scope.parceiro.nome_completo = sessionStorage.getItem('nome');
			$scope.parceiro.cpf_parceiro = sessionStorage.getItem('cpf');
			$scope.parceiro.email = sessionStorage.getItem('email');
			$scope.parceiro.cargo = sessionStorage.getItem('cargo');
			$scope.parceiro.telefone = sessionStorage.getItem('telefone');
			$scope.parceiro.nome_empresa = sessionStorage.getItem('empresa');

			$scope.emiteAlerta = function(){
				alert("Obrigado por se cadastrar, seus dados foram salvos com sucesso! Verifique seu e-mail com os dados registrados.");
			}
			
			$scope.emiteAlertaErro = function(){
				alert("Houve um erro ao salvar seu formulário, por favor faça-o novamente.");
				window.location.href = "/projeto-ccp-0.0.1-SNAPSHOT/";
			}
			$scope.salvarDados = function(parceiro) {
			
				document.body.style.cursor='wait';
				$scope.estaAtivo = false;
				
				$scope.enviaEmail = function(){
					$http.post("receiver",JSON.stringify(parceiro)+ "//"+ JSON.stringify($scope.acreditacoes)).then(function(response){
						document.body.style.cursor='default';
						console.log(response);
							if(response.status == 201){
								$scope.podeImprimir = true;
								if(confirm("Gostaria de imprimir o relatório?")){
									$scope.print();
								}
								$scope.emiteAlerta();
							
							} else {
								alert("Houve um erro, tente enviar novamente a solicitação.");
							}
						},function (response){
							alert("Houve um erro, tente enviar novamente a solicitação.");
						})
				}
				
				 if(!($scope.acreditacoes == null)){
					 $scope.enviaEmail();
				 } else {
				 alert("Selecione acreditações antes de confirmar o cadastro.");
				 window.location.href =
				 "acreditacoes.html";
				 }
			}
			
			$scope.cancelAction = function(){
				if(confirm('Tem certeza que deseja cancelar o envio do formulário?')){
					window.location.href = "/projeto-ccp-0.0.1-SNAPSHOT/";
				} else {
					
				}
			}
			
			$scope.print = function(){
				function listaAcreditacoes(){
					var listaDeAcreditacoes = "";
					var cont = 0;
					
					for (var i = 0; i < $scope.acreditacoes.length; i++){
						if($scope.acreditacoes[i].nome_perfil === "Vendas"){
						cont++;
						
						if(cont === 1){
							listaDeAcreditacoes += "<h4>Vendas</h4>";
						}
						
						listaDeAcreditacoes += "<li>" + $scope.acreditacoes[i].nome_acreditacao + " </li><br>"
						}
					}
					cont = 0;
					for (var i = 0; i < $scope.acreditacoes.length; i++){
						if($scope.acreditacoes[i].nome_perfil === "Pré-Vendas"){
						cont++;
						
						if(cont === 1){
							listaDeAcreditacoes += "<h4>Pré-Vendas</h4>";
						}
						
						listaDeAcreditacoes += "<li>" + $scope.acreditacoes[i].nome_acreditacao + " </li><br>"
						}
					}
					cont = 0;
					for (var i = 0; i < $scope.acreditacoes.length; i++){
						if($scope.acreditacoes[i].nome_perfil === "Entrega de Serviços"){
						cont++;
						
						if(cont === 1){
							listaDeAcreditacoes += "<h4>Entrega de Serviços</h4>";
						}
						
						listaDeAcreditacoes += "<li>" + $scope.acreditacoes[i].nome_acreditacao + " </li><br>"
						}
					}
					cont = 0;
					return listaDeAcreditacoes;
				}
				var lista = listaAcreditacoes();
				var w = window.open();
				w.document.write("<h1>" + $scope.parceiro.nome_completo + ",</h1> <br><br>" +
						"<h2>Confira os dados que registrou no sistema de capacitação de parceiros: </h2>"+ 
						"<h3>CPF: " + $scope.parceiro.cpf_parceiro + "</h3>" +  
						"<h3>E-mail: " + $scope.parceiro.email +  "</h3>" + 
						"<h3>Empresa: "+ $scope.parceiro.nome_empresa +  "</h3>" + 
						"<h3>Cargo: "+ $scope.parceiro.cargo+  "</h3>" + 
						"<h3>Telefone: " + $scope.parceiro.telefone +  "</h3>" + 
						"<br><br> Abaixo encontra-se a lista de acreditações que selecionou: " + 
						"<br><br>"+ lista
						 + "");
				w.print();
				w.close();
			}
			window.onbeforeunload = function(e){
				sessionStorage.clear();	
				window.location.href = "/projeto-ccp-0.0.1-SNAPSHOT/"
				return "Deseja atualizar a página? As informações podem ser perdidas.";
					
				
			}
			
		} ]);
