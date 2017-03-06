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

orderApp.config(function ($locationProvider) {
    $locationProvider.html5Mode({
        enabled: true,
        requireBase: false
    });
});
orderApp.controller('mainPageCtrl', function ($scope, $http,$location) {

    $scope.call = $location.search();
    console.log($scope.call)

    if(!$scope.call.orderId&&!$scope.call){
        window.alert("login fail");
    }

    $scope.load= function () {
        $scope.order="";
        $scope.path=$scope.data.order.path+$scope.data.order.one.path+$scope.data.order.one.idAndAccount.path;
        console.log($scope.path+"/"+$scope.call.orderId+"/"+$scope.call.accountId+"/")
        $http.get($scope.path+"/"+$scope.call.orderId+"/"+$scope.call.accountId+"/").then(function (data) {
            console.log("ssssssssssssssssss")
            console.log(data)
            $scope.order=data.data;
        }, function () {
            window.alert("order load fail");
        })

        $scope.bankAccounts="";
        $scope.initBankAccounts=function () {
            $scope.path=$scope.data.bankAccount.path+$scope.data.bankAccount.all.path+$scope.data.bankAccount.all.account.path
            $http.get($scope.path+"/"+$scope.call.accountId).then(function (data) {
                console.log(data)
                $scope.bankAccounts=data.data;
            }, function () {
                window.alert("bankAccount fail");
            })
        }
        $scope.initBankAccounts();
        $scope.banks="";
        $scope.path=$scope.data.bank.path+$scope.data.bank.all.path;
        $http.get($scope.path).then(function (data) {
            console.log(data)
            $scope.banks=data.data;
        }, function () {
            window.alert("banks fail");
        })
    }
    $scope.load();

    $scope.makeBankAccount=function(bankid){
        $scope.path=$scope.data.bankAccount.path+$scope.data.bankAccount.one.path+$scope.data.bankAccount.one.account.path;
        var nba={   accountId: $scope.call.accountId,
                    bankId: bankid}
        console.log($scope.call.accountId);
        console.log(bankid);
        console.log(nba)
        $http.post($scope.path,nba).then(function (data) {
            console.log(data)
            $scope.initBankAccounts()

        }, function () {
            window.alert("bankAccount make fail");
        })
    }
    $scope.pay=function(bankaccid){
        $scope.path=$scope.data.bankAccount.path+$scope.data.bankAccount.one.path+$scope.data.bankAccount.one.pay.path;
        var pay={
            bankAccountId: bankaccid,
            orderId: $scope.call.orderId
        }
        $http.post($scope.path,pay).then(function (data) {
            console.log(data)
            $scope.url.data="successPage.html";
        }, function () {
            window.alert("pay fail");
        })
        console.log($scope.call.accountId);
        console.log(bankaccid);
    }


})


