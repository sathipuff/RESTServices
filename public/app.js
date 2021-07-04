(function () {
'use strict';
var app =
angular.module('BusArrivalApp', ['ui.router'])
.controller('BusArrivalController', BusArrivalController)
.service('BusArrivalService', BusArrivalService)
.constant('RPI_ENDPOINT', "http://127.0.0.1:8080")
.filter('remainingMins', function(){
  return remainingMins;
} );

app.config(['$httpProvider', '$stateProvider', '$urlRouterProvider',
  function($httpProvider, $stateProvider, $urlRouterProvider) {

    $stateProvider
      .state('remotecontrol', {
        url: '/remotecontrol',
        templateUrl: '/pages/remotecontrol.html',
      })
      .state('busarrival', {
        url: '/busarrival',
        templateUrl: '/pages/busarrival.html',
      });

    $urlRouterProvider.otherwise('/pages/busarrival.html');
  }
]);


var remainingMins = function(busArrivalTime,currentTime){
  var busDate = new Date(busArrivalTime);
  var busTime = busDate.getTime();
  // console.log("Bus TIming : "+busTime);
  // console.log("currentTime : "+currentTime);

  if( isNaN(busTime) ||isNaN(currentTime))
    return '-';

  var difference = busTime - currentTime;
  var minutesDifference = Math.floor(difference/1000/60);

var displayMinsStr;
console.log("DIFF : "+minutesDifference);
  if(minutesDifference <= 0){
    console.log("HIT 0 "+minutesDifference);
    displayMinsStr  = 'Arr';
  }
  else{
    displayMinsStr  = minutesDifference + ' min';
  }
  return displayMinsStr.padStart(6,' ');
}

BusArrivalController.$inject = ['BusArrivalService','$scope'];
function BusArrivalController(BusArrivalService, $scope) {
  var busArrivalCtlr = this;
  var promise = BusArrivalService.getBusStopArrival('66481,66551,66589,64129');

  promise.then(function (response) {
// console.log(response.data);
    busArrivalCtlr.arrivals = response.data;
  })
  .catch(function (error) {
    console.log(error);
  });

  $scope.sendCommand = function(component, cmd){
        console.log("Calling send command in service");
        BusArrivalService.sendRemoteControlCommand(component,cmd);
  };
}

BusArrivalService.$inject = ['$http','RPI_ENDPOINT'];
function BusArrivalService($http, RPI_ENDPOINT) {
  var service = this;
  service.getBusStopArrival = function (busSTopId) {
    var response = $http({
      method: 'GET',
      url: (RPI_ENDPOINT+"/getArrivalTiming?busStops="+busSTopId)

    });
    return response;
  };

  service.sendRemoteControlCommand = function (component, cmd) {
    var data = {
        component: component,
        command: cmd
    };
    var jsondata = JSON.stringify(data);
    console.log('sending remote control command');
    console.log(jsondata);
    var response = $http({
        method: 'POST',
        url: (RPI_ENDPOINT+"/remoteControlAction"),
        data: jsondata
    });
  };
}


})();
