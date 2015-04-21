'use strict';

angular.module('gestionedelegheApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('delega', {
                parent: 'entity',
                url: '/delega',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'gestionedelegheApp.delega.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/delega/delegas.html',
                        controller: 'DelegaController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('delega');
                        return $translate.refresh();
                    }]
                }
            })
            .state('delegaDetail', {
                parent: 'entity',
                url: '/delega/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'gestionedelegheApp.delega.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/delega/delega-detail.html',
                        controller: 'DelegaDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('delega');
                        return $translate.refresh();
                    }]
                }
            });
    });
