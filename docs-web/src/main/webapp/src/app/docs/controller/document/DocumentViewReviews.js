'use strict';

/**
 * Document view reviews controller.
 */
angular.module('docs').controller('DocumentViewReviews', function($scope, $stateParams, Restangular) {
  // Load review data from server
  Restangular.one('review').get({
    document: $stateParams.id
  }).then(function(data) {
    $scope.reviews = data.reviews
  });   
    
});