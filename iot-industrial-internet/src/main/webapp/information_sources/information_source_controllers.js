/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
informationSources.controller('InformationSourcesController', ['$scope', 'InformationSource', function ($scope, InformationSource) {
        $scope.sources = InformationSource.query();
    }]);

informationSources.controller('InformationSourceController', ['$scope', '$routeParams', 'Device', 'InformationSource', 'Sensor',
    function ($scope, $routeParams, Device, InformationSource, Sensor) {

        populateDevicesWithSensors = function() {
            for (var i = 0; i < $scope.devices.length; i++) {
                $scope.devices[i].sensors = Sensor.query({deviceid: $scope.devices[i].id});
            }
        };

        $scope.source = InformationSource.get({informationsourceid: $routeParams.informationsourceid});
        $scope.devices = Device.query({informationsourceid: $routeParams.informationsourceid}, function (value, headers) {
            populateDevicesWithSensors();
        });

    }]);

informationSources.controller('SensorController', ['$scope', '$routeParams', 'Sensor', 'Readout', function ($scope, $routeParams, Sensor, Readout) {
        $scope.sensor = Sensor.get({sensorid: $routeParams.sensorid});
        $scope.readouts = Readout.query({sensorid: $routeParams.sensorid});
        $scope.filter = function () {
            $scope.readouts = Readout.query({sensorid: $routeParams.sensorid, more: $scope.more, less: $scope.less});
        }
    }]);
