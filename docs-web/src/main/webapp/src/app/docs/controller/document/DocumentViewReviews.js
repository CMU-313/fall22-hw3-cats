'use strict';

/**
 * Document view reviews controller.
 */
angular.module('docs').controller('DocumentViewReviews', function($scope, $stateParams, Restangular) {
  Restangular.one('reviews').get({
    documentId: $stateParams.id
  }).then(function(data) {
    console.log(data)
    $scope.reviews = data.reviews;
  });
});