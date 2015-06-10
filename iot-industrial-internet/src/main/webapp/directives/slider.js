/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */


IIFramework.directive('slider', function () {
    return {
        scope: {
            number: '=ngModel',
            change: '=change'
        },
        link: function (scope, elem, attrs) {
            if (typeof scope.number[0] == 'undefined') {
                scope.number[0] = 0;
            }
            
            if (typeof scope.number[1] == 'undefined') {
                scope.number[1] = 0;
            }
 
    
            $(elem).slider({
                min: -50,
                max: 100,
                values: [scope.number[0], scope.number[1]],
                slide: function (event, ui) {
                    
                    scope.change();
                    
                    scope.$apply(function () {
                        scope.number[0] = ui.values[0];
                        scope.number[1] = ui.values[1];
                    });
                },
                range: true
            });
        }
    }
});