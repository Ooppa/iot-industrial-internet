/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */

var configurations = angular.module('configurations', ['ngResource']);

configurations.factory('InformationSourceConfiguration', ['$resource',
  function ($resource) {
    return $resource('1.0/configurations/informationsources/:configid/:action', {}, {
      get: {method: 'GET', params: {action: 'view'}},
      query: {method: 'GET', params: {action: 'list'}, isArray: true},
      edit: {method: 'POST', params: {action: 'edit'}},
      save: {method: 'POST', params: {action: 'add'}},
      delete: {method: 'DELETE', params: {action: 'delete'}}
    });
}]);
