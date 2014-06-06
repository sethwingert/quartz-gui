'use strict';

/* Controllers */

angular.module('myApp.controllers', [])
  .controller('MyCtrl1', [function() {

  }])
  .controller('MyCtrl2', [function() {

  }])
  .controller('ServerConfigController', ['$scope', function($scope){
	  $scope.config = {
	    location: "remote"  //default location
	  };
	  $scope.submit = function(config) {
	  };
  }]);
