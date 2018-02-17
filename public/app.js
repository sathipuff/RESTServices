(function () {
'use strict';

angular.module('BusArrivalApp', [])
.controller('BusArrivalController', BusArrivalController)
.service('BusArrivalService', BusArrivalService)
.constant('RPI_ENDPOINT', "http://192.168.1.119:8080")
.filter('remainingMins', function(){
  return remainingMins;
} );


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
console.log("DIFF"+minutesDifference);
  if(minutesDifference <= 0){
    console.log("HIT 0 "+minutesDifference);
    displayMinsStr  = 'Arr';
  }
  else{
    displayMinsStr  = minutesDifference + ' min';
  }
  return displayMinsStr.padStart(6,' ');
}

BusArrivalController.$inject = ['BusArrivalService'];
function BusArrivalController(BusArrivalService) {
  var busArrivalCtlr = this;
  var promise = BusArrivalService.getBusStopArrival('66481,66551,66589,64129');

  promise.then(function (response) {
// console.log(response.data);
    busArrivalCtlr.arrivals = response.data;
  })
  .catch(function (error) {
    console.log(error);
  });

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

}


})();
