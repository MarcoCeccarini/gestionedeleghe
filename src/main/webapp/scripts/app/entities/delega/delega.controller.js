'use strict';

angular.module('gestionedelegheApp')
    .controller('DelegaController', function ($scope, Delega, ParseLinks) {
        $scope.delegas = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Delega.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.delegas = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Delega.update($scope.delega,
                function () {
                    $scope.loadAll();
                    $('#saveDelegaModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Delega.get({id: id}, function(result) {
                $scope.delega = result;
                $('#saveDelegaModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Delega.get({id: id}, function(result) {
                $scope.delega = result;
                $('#deleteDelegaConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Delega.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteDelegaConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.delega = {nome: null, cognome: null, codiceFiscale: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
        

    });
