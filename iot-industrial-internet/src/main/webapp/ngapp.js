/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */


var IIFramework = angular.module('IIFramework', [
    'ngRoute',
    'angular-flot',
    'informationSources',
    'configurations',
    'sysErrors',
    'angularMoment',
    'ui.bootstrap'
]);


IIFramework.config(function ($routeProvider) {
    $routeProvider.when('/', {
        controller: 'FrontController',
        templateUrl: 'front.html'
    })
            .when('/visual/now/:sensorid', {
                controller: 'VisualRealtimeController',
                templateUrl: 'information_sources/line_graph.html'
            })
            .when('/sources', {
                controller: 'InformationSourcesController',
                templateUrl: 'information_sources/list.html'
            })
            .when('/sources/:informationsourceid', {
                controller: 'InformationSourceController',
                templateUrl: 'information_sources/view.html'
            })
            .when('/sensors/:sensorid', {
                controller: 'SensorController',
                templateUrl: 'information_sources/sensor.html'
            })
            .when('/configurations', {
                controller: 'InformationSourceConfigurationsController',
                templateUrl: 'configurations/configurations.html'
            })
            .when('/configurations/add', {
                controller: 'AddInformationSourceController',
                templateUrl: 'configurations/information_source_form.html'
            })
            .when('/configurations/:configid/edit', {
                controller: 'EditInformationSourceController',
                templateUrl: 'configurations/information_source_form.html'
            })

            // Controllers for SysErrors
            .when('/syserrors/:errorid', {
                controller: 'SysErrorDetailsController',
                templateUrl: 'syserrors/view.html'
            })
            .when('/syserrors/', {
                controller: 'SysErrorsListController',
                templateUrl: 'syserrors/list.html'
            });
});
