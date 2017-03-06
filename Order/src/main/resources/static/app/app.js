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

})

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
    //TODO init empty
    $scope.account = {
        id: null,
        name: "Firste",
        mail: "aismaelinc@gmail.come"
    }

    $scope.call = {orderId:"0",accountId:"0"}
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
            $scope.account.mail+"/";
        $http.get($scope.path + $scope.pathExt).then(function () {
            $scope.pathExt = $scope.data.account.one.name.path + "/" +
                $scope.account.name;
            $http.get($scope.path + $scope.pathExt).then(function (data) {
                $scope.url.data = "orderChoosePage.html"
                $scope.account.id=data.data.id;
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

orderApp.controller('orderChoosePageCtrl', function ($scope, $http) {
if(!$scope.account.id){
    $scope.path = $scope.data.account.path +
        $scope.data.account.one.path
    $scope.pathExt = $scope.data.account.one.mail.path + "/" +
        $scope.account.mail+"/";
    $http.get($scope.path + $scope.pathExt).then(function () {
        $scope.pathExt = $scope.data.account.one.name.path + "/" +
            $scope.account.name;
        $http.get($scope.path + $scope.pathExt).then(function (data) {
            $scope.account.id=data.data.id;
        }, function () {
            $scope.url.data = "accountPage.html"
            window.alert("login fail");
        })
    }, function () {
        $scope.url.data = "accountPage.html"
        window.alert("login fail");
    })
}
})

orderApp.controller('favoritePageCtrl', function ($scope, $http) {
    $scope.myItems;
    $http.get($scope.data.favorite.path+
        $scope.data.favorite.all.path+
        $scope.data.favorite.all.account.path+"/"+
        $scope.account.id
    ).then(function (data) {
        //window.alert("Favorites found for this Account");
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
            function (data) {
                $scope.url.data = "extPage.html"
                console.log("pppppppppppppppppppppppp");
                console.log(data);
                $scope.call.orderId=data.data.id;
                $scope.call.accountId=$scope.account.id;
                window.alert("order  success");

            }, function () {

                window.alert("order  fail");
            })
    }
})

orderApp.controller('orderPageCtrl', function ($scope, $http) {
    $scope.myItems;
    $scope.mySelectedItems = [];
    $scope.favorite ={
        name:"",
        count:0,
        id:null,
        accountId: null,
        itemSetStubDtos:[]
    }
    $scope.order ={
        id:null,
        posted:false,
        date:new Date(),
        accountId: null,
        itemSetStubDtos:[]
    }
    $scope.itemSetStubDto={
        count:0,
        itemID:0
    }

    $scope.makeFavorite=function () {
        $scope.favorite.accountId=$scope.account.id
        $scope.mySelectedItems.forEach(function (item) {
            console.log(item);
            var itemSetStubDto={
                count:item.count,
                itemID:item.id
            };
            $scope.favorite.itemSetStubDtos.push(itemSetStubDto);
        })
        $scope.path = $scope.data.favorite.path +
            $scope.data.favorite.one.path;
        console.log( $scope.favorite);
        $http.post($scope.path, $scope.favorite).then(
            function (data) {window.alert("favorite made  success");
            console.log(data);
            },function () {window.alert("favorite made  error");})
    }

    $scope.orderNow=function () {
        $scope.order.accountId=$scope.account.id
        $scope.order.date=new Date();
        $scope.mySelectedItems.forEach(function (item) {
            var itemSetStubDto={
                count:item.count,
                itemID:item.id
            };
            $scope.order.itemSetStubDtos.push(itemSetStubDto);
        })
        $scope.path = $scope.data.order.path +
            $scope.data.order.one.path;
        console.log( $scope.order);


        $http.post($scope.path, $scope.order).then(
            function (data) {window.alert("order made  success");
                console.log(data)
                $scope.call.orderId=data.data;
                $scope.call.accountId=$scope.account.id;
                $scope.url.data = "extPage.html"

            },function () {window.alert("order made  error");})
    }


    $scope.$watchCollection("mySelectedItems", function (data) {
        console.log(data)
    });
    $http.get($scope.data.item.path+
        $scope.data.order.all.path
    ).then(function (data) {
        //window.alert("Favorites found for this Account");
        $scope.myItems=data.data;
    },function () {
        window.alert("no Items found");
    })
    $scope.input=1;
})

orderApp.filter('trustUrl', ['$sce', function ($sce) {
    return function(url) {
        return $sce.trustAsResourceUrl(url);
    };
}])

orderApp.controller('extPageCtrl', function ($scope, $http,$sce) {
    $scope.my={my: null}
    $scope.jumpToBilling=function () {
        console.log("tick")
        $http.get("/call",{timeout: 30000}).then(function (data) {
            console.log("****")
            console.log(data)
            return $scope.my.my=$sce.trustAsResourceUrl("http://"+self.location.hostname+":"+data.data+"/?orderId="+$scope.call.orderId+"&&accountId="+$scope.call.accountId);
        },function (data) {
            console.log(data)
            console.log("error")
            return "xyz"
        })
    }
    $scope.jumpToBilling()
})
