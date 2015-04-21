'use strict';

angular.module('gestionedelegheApp')
    .controller('DelegaDetailController', function ($scope, $stateParams, Delega) {
        $scope.delega = {};
        $scope.load = function (id) {
            Delega.get({id: id}, function(result) {
              $scope.delega = result;
            });
        };
        $scope.load($stateParams.id);
    });
