var orderApp = angular.module('orderApp', ['ui.bootstrap', "ngTable", 'trNgGrid']);


orderApp.controller('configCtrl', function ($scope, $http, loadService) {
    $scope.loadService = loadService;
    $scope.$watch(
        function () {
            return loadService.listPath();
        }
        , function (oldArg, newArg) {
            if (newArg !== null) {
                $scope.setHttpData(loadService.listPath());
            }
        })
    $scope.url = {data: "mainPage.html"};

});

orderApp.factory('loadService', [function () {
    var listPath = null;
    return {
        init: function (path) {
            listPath = path;
        }, listPath: function () {
            return listPath
        }
    }
}])

orderApp.controller('dataService', function ($scope, $http) {
    $scope.data = null;
    $scope.setHttpData = function (path) {
        $http.get(path).then(function (obj) {
            $scope.data = obj.data.config.view;
        })

    }
    $scope.getHttpData = function () {
        return $scope.data;
    }

})

orderApp.controller('mainPageCtrl', function ($scope, $http) {
    $scope.call = {orderId:"0",accountId:"0"}
    $http.get("/call/data").then(function (data) {
        console.log(data)
        $scope.call.orderId=data.data.orderId;
        $scope.call.accountId=data.data.accountId;
    }, function () {
        window.alert("login fail");
    })
})


