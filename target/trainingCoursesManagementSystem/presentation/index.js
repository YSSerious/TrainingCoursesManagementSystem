/**
 * Created by Алексей on 21.10.2016.
 */
'use strict';
var app = angular.module('indexApp', ['ngRoute']);

app.controller('indexController', function ($scope, $http) {

    // Test function
    function getUser(id) {
        $http({
            method: 'GET',
            url: '/getUser',
            params: {id: id}
        })
            .success(function (data) {
                console.log(data);
                $scope.user = data;
            });
    };

    getUser(1);


});

// Routes add soon.

// app.config(function ($routeProvider) {
//
//     $routeProvider
//         .when('/.....', {
//         templateUrl: '',
//         controller: ''
//     })
//         .when('/....', {
//         templateUrl: '',
//         controller: ''
//     })
//         .otherwise({
//         redirectTo: '/...'
//     });
//
// });