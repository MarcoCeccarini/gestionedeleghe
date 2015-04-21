'use strict';

angular.module('gestionedelegheApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
