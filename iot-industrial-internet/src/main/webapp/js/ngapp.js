/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */


var IIFramework = angular.module('IIFramework', [
    'ngRoute',
    'angular-flot',
    'informationSources'
]);


IIFramework.config(function ($routeProvider) {
    $routeProvider.when('/', {
        controller: 'FrontController',
        templateUrl: 'ngviews/front.html'
    })
            .when('/visual/now', {
                controller: 'VisualRealtimeController',
                templateUrl: 'ngviews/visual_realtime.html'
            })
            .when('/visual/history', {
                controller: 'FrontController',
                templateUrl: 'ngviews/visual_history.html'
            })
            .when('/sources', {
                controller: 'InformationSourcesController',
                templateUrl: 'ngviews/information_sources.html'
            })
            .when('/sources/:datasourceid', {
                controller: 'InformationSourceController',
                templateUrl: 'ngviews/information_source.html'
            })
            .when('/sensors/:sensorid', {
                controller: 'SensorController',
                templateUrl: 'ngviews/sensor.html'});
});
