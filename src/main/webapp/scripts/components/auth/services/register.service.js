'use strict';

angular.module('gestionedelegheApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


