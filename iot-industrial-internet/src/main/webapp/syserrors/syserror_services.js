/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */


var sysErrors = angular.module('sysErrors', ['ngResource']);



// Sends requests to RestAPI, get gets all, query gets one based on errorid.

sysErrors.factory('SysError', ['$resource',
    function($resource) {
        return $resource('1.0/errors/:errorid/:action/:filter', {}, {
            get: {method: 'GET', params: {action: 'list'}, isArray: true},
            query: {method: 'GET', params: {action: 'view'}, isArray: false},
            getHighAndFatal: {method: 'GET', params: {action: 'list'}, isArray: true}
        });
    }]);