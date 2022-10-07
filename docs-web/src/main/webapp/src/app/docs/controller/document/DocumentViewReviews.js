'use strict';

/**
 * Document view reviews controller.
 */
angular.module('docs').controller('DocumentViewReviews', function($scope, $stateParams, Restangular) {
  Restangular.one('review', $stateParams.id).get().then(function(data) {
    $scope.reviews = data.reviews;
  });

  $scope.addReview = function () {
    const review = $scope.newReview
    review.id = $scope.document.id
    Restangular.all('review').post("", review);
  }
});