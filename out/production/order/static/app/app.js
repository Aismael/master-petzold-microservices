// Define the `phonecatApp` module
var phonecatApp = angular.module('orderApp', ['ui.bootstrap']);

// Define the `PhoneListController` controller on the `phonecatApp` module
phonecatApp.controller('AccountListController', function PhoneListController($scope, $http) {
    $http.get('/Account').
    then(function(obj) {
        $scope.accounts = obj.data;
        console.log(obj.data);
    });
    console.log($scope.accounts);
});

