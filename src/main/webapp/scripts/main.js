/**
 * 
 */

var app = angular.module('projeto-ccp',[]);

var URL = "http://localhost:5000";

function checaSessionStorage(msg){
	if(!sessionStorage.length){
		alert(msg);
		window.location.href = "http://localhost:8080/projeto-ccp-0.0.1-SNAPSHOT/";
		return
	}
}



