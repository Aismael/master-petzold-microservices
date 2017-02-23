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
    if(!$scope.call){
    $scope.call = {orderId:"0",accountId:"0"}
    $http.get("/call/data").then(function (data) {
        console.log(data)
        $scope.call.orderId=data.data.orderId;
        $scope.call.accountId=data.data.accountId;
    }, function () {
        window.alert("login fail");
    })
    }

    $scope.$watchCollection("call", function () {
        $scope.order="";
        $scope.path=$scope.data.order.path+$scope.data.order.one.path+$scope.data.order.one.idAndAccount.path;
        console.log($scope.path+"/"+$scope.call.orderId+"/"+$scope.call.accountId+"/")
        $http.get($scope.path+"/"+$scope.call.orderId+"/"+$scope.call.accountId+"/").then(function (data) {
            console.log(data)
            $scope.order=data.data;
        }, function () {
            window.alert("order load fail");
        })

        $scope.bankAccounts="";
        $scope.path=$scope.data.bankAccount.path+$scope.data.bankAccount.all.path+$scope.data.bankAccount.all.account.path
        $http.get($scope.path+"/"+$scope.call.accountId).then(function (data) {
            console.log(data)
            $scope.bankAccounts=data.data;
        }, function () {
            window.alert("bankAccount fail");
        })
        $scope.banks="";
        $scope.path=$scope.data.bank.path+$scope.data.bank.all.path;
        $http.get($scope.path).then(function (data) {
            console.log(data)
            $scope.banks=data.data;
        }, function () {
            window.alert("banks fail");
        })
    });


})


