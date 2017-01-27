var orderApp = angular.module('orderApp', ['ui.bootstrap', "ngTable", 'trNgGrid']);

orderApp.controller('configCtrl', function ($scope, $http, dataService) {
    $scope.init = function (path) {
        $http.get(path).then(function (obj) {
            $scope.config = obj.data.config;
            dataService.setData(obj.data.config);
            console.log($scope.config);
        });
    }
    dataService.setData($scope.config);
});

orderApp.factory('dataService', function () {
    this.data;
    return {
        setData: function (data) {
            this.data = data;
        }, getData: function () {
            return this.data;
        }
    }
})


orderApp.controller('tabCtrl', function ($scope, dataService, $q) {
    $scope.getBType = function (test) {
        return ( typeof test);
    }
    $scope.status = {
        isFirstOpen: true,
        isFirstDisabled: false
    };

    $q.when(dataService.getData() != 'undefined')
    {
        console.log(dataService);
        console.log(dataService.getData());
    }

    $scope.Sides = [
        {
            "id": 1,
            "name": "Account",
            "url": 'app/snippets/account.html',
            "controller": "restGetCtrl"
        },
        {
            "id": 2,
            "name": "Item",
            "url": 'app/snippets/item.html',
            "controller": "restGetCtrl"
        }
    ];

    $scope.model = {
        name: 'Sides'
    };

    $scope.setActive = function (id) {
        $scope.active = id;
    };
});

orderApp.controller('restGetCtrl', ['NgTableParams', '$scope', '$http', function (NgTableParams, $scope, $http) {
    $scope.listPath = null;
    $scope.init = function (listPath) {
        $http.get(listPath).then(function (obj) {
            $scope.things = obj.data;
            $scope.tableParams = new NgTableParams({}, {});
            $scope.tableParams.settings({
                dataset: $scope.things
            });
        });
        this.tableParams = $scope.tableParams;
    }
}]);

orderApp.controller('pageCtrl', function ($scope) {
    $scope.status = {
        isFirstOpen: true,
        isFirstDisabled: false
    };
    $scope.sides = [
        {
            "id": 1,
            "name": "accountPage",
            "url": 'app/pages/accountPage.html',
            "controller": "accountPageCtrl"
        },
        {
            "id": 2,
            "name": "orderPage",
            "url": 'app/pages/orderPage.html',
            "controller": "orderPageCtrl"
        }
    ];

    $scope.model = {
        name: 'Sides'
    };

    $scope.setActive = function (id) {
        $scope.active = id;
    };
});