'use strict';

/* Services */


// Demonstrate how to register services
// In this case it is a simple value service.
var quartzGuiServices = angular.module('myApp.services', ['ngResource']);
quartzGuiServices.value('version', '0.1');
quartzGuiServices.factory('ServerConfig', ['$resource',
   function($resource) {
		return $resource('rest/server/:serverConfigId', {});
	}
]);
