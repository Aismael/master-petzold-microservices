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
    $scope.url = {data: "accountPage.html"};

});

orderApp.factory('loadService', [function () {
    var listPath = null;
    return {
        init: function (path) {
            console.log("y");
            listPath = path;
        }, listPath: function () {
            return listPath
        }
    }
}])

orderApp.controller('dataService', function ($scope, $http) {
    $scope.data = null;
    //TODO init empty
    $scope.account = {
        id: null,
        name: "First",
        mail: "aismaelinc@gmail.com"
    }

    $scope.setHttpData = function (path) {
        $http.get(path).then(function (obj) {
            $scope.data = obj.data.config.view;
        })

    }
    $scope.getHttpData = function () {
        return $scope.data;
    }


})

orderApp.controller('accountPageCtrl', function ($scope, $http) {

    $scope.login = function () {
        $scope.path = $scope.data.account.path +
            $scope.data.account.one.path
        $scope.pathExt = $scope.data.account.one.mail.path + "/" +
            $scope.account.mail;
        $http.get($scope.path + $scope.pathExt).then(function () {
            $scope.pathExt = $scope.data.account.one.name.path + "/" +
                $scope.account.name;
            $http.get($scope.path + $scope.pathExt).then(function (data) {
                $scope.url.data = "orderChoosePage.html"
                $scope.account.id=data.data.id;
                console.log(data.data.id);
                //window.alert("login success");
            }, function () {
                $scope.url.data = "accountPage.html"
                window.alert("login fail");
            })
        }, function () {
            $scope.url.data = "accountPage.html"
            window.alert("login fail");
        })
    }
    $scope.new = function () {
        $scope.path = $scope.data.account.path +
            $scope.data.account.one.path;
        $http.post($scope.path, $scope.account).then(
            function () {
                $scope.url.data = "orderChoosePage.html"
                window.alert("account Post success");
            }, function () {
                $scope.url.data = "accountPage.html"
                $scope.account.id=data.data.id;
                $scope.account.mail=data.data.mail;
                $scope.account.name=data.data.name;

                window.alert("account Post fail");
            })
    }
})
orderApp.controller('orderChoosePageCtrl', function () {

})
orderApp.controller('favoritePageCtrl', function ($scope, $http) {
    $scope.myItems;
    $http.get($scope.data.favorite.path+
        $scope.data.favorite.all.path+
        $scope.data.favorite.all.account.path+"/"+
        $scope.account.id
    ).then(function (data) {
        //window.alert("Favorites found for this Account");
        console.log(data.data)
        $scope.myItems=data.data;
    },function () {
        window.alert("no Favorites found for this Account");
    })

    $scope.mySelectedItems = [];
    $scope.$watchCollection("mySelectedItems", function () {
        // your logic goes here
    });
    $scope.makeOrder=function () {
        $scope.path = $scope.data.favorite.path +
            $scope.data.favorite.one.path+$scope.data.favorite.one.order.path+"/"+
            $scope.mySelectedItems[0].id
        $http.post($scope.path).then(
            function () {
                $scope.url.data = "orderChoosePage.html"
                window.alert("order  success");
            }, function () {

                window.alert("order  fail");
            })
    }
})
orderApp.controller('orderPageCtrl', function () {

})