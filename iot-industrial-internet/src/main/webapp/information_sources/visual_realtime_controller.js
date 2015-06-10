/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */


IIFramework.controller('VisualRealtimeController', function ($scope, InformationSource, Readout, $routeParams) {
    var dataToDisplay = [];

    $scope.from = 0;
    $scope.to = 100;

    $scope.number = [0, 80];
    $scope.threshold = 22.5;

    $scope.readouts = Readout.query({sensorid: $routeParams.sensorid}, function () {
        $scope.id = $routeParams.sensorid;

        for (var i = 0; i < $scope.readouts.length; i++) {
            var time = $scope.readouts[i].time;
            var value = $scope.readouts[i].value;

            dataToDisplay[i] = [(time * 1000), value];
        }

        $scope.flotData = [{
                data: dataToDisplay,
                label: " " + ($scope.readouts[0].quantity) + " (" + $scope.readouts[0].unit + ")"
            }];
    });

    $scope.change = function () {
        $scope.dataOptions.yaxis.min = $scope.number[0];
        $scope.dataOptions.yaxis.max = $scope.number[1];
    };

    $scope.setMode = function (parameters) {
        console.log(parameters);
        if (parameters === 1) {
            $scope.dataOptions.xaxis.minTickSize = [1, "hour"];
            

        };
        if (parameters === 7) {
            $scope.dataOptions.xaxis.minTickSize = [1, "day"];


        };
        if (parameters === 31) {
            $scope.dataOptions.xaxis.minTickSize = [1, "month"];


        };
        if (parameters === 365) {
            $scope.dataOptions.xaxis.minTickSize = [1, "month"];


        };
    };

    $scope.setThreshold = function () {
        $scope.dataOptions.series = {
            lines: {
                show: true,
            },
            points: {
                show: true
            },
            color: "rgb(30, 180, 20)",
            threshold: {
                below: $scope.threshold,
                color: "rgb(200, 20, 30)"
            }
        }

    }

    $scope.dataOptions = {
        series: {
            lines: {
                show: true,
            },
            points: {
                show: true
            }
        },
        grid: {
            hoverable: true // needed for tooltip to work
        },
        xaxis: {
            mode: "time",
            timeformat: "%d.%m.%y %H:%M:%S"
        },
        yaxis: {
            min: $scope.number[0],
            max: $scope.number[1]
        },
        tooltip: true,
        tooltipOpts: {
            content: "'%y",
            shifts: {
                x: -60,
                y: 25
            }
        },
        zoom: {
            interactive: true
        },
        pan: {
            interactive: true
        }
    };

}
);

