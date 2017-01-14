var orderApp = angular.module('orderApp', ['ui.bootstrap', "ngTable"]);

orderApp.controller('listController',['NgTableParams','$scope','$http', function listController(NgTableParams,$scope, $http) {
    $scope.listPath=null;
    $scope.init=function (listPath) {
        $http.get('/'+listPath).
        then(function(obj) {
            $scope.things = obj.data;
            $scope.tableParams = new NgTableParams({},{});
            //$scope.tableParams.data=$scope.things;
            $scope.tableParams.settings({
                dataset: $scope.things
            });
        });
        this.tableParams=$scope.tableParams;
    }
}]);

orderApp.controller('dataController',['NgTableParams','$scope','$http', function dataController(NgTableParams,$scope) {
    $scope.listPath=null;
    $scope.init=function (dataController) {
            $scope.things = dataController;
            $scope.tableParams = new NgTableParams({},{});
            $scope.tableParams.settings({
                dataset: $scope.things
            });
        this.tableParams=$scope.tableParams;
    }
}]);


orderApp.controller('tabCtrl', function ($scope) {
    $scope.getBType = function (test) {
        return ( typeof test);
    }
    $scope.status = {
        isFirstOpen: true,
        isFirstDisabled: false
    };
    //TODO get Topics via Rest from server
    $scope.topics = [
        {
            "id": 1,
            "name": "Account",
            "url": 'app/tabs/account.html',
            "listPath": "Account"
        },
        {
            "id": 2,
            "name": "Item",
            "url": 'app/tabs/item.html',
            "listPath": "Item"
        }
    ];

    $scope.model = {
        name: 'Topics'
    };

    $scope.setActive = function (id) {
        $scope.active = id;
    };
});