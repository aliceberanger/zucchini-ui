(function (angular) {
  'use strict';

  var AuthService = function ($http, UrlBuilder) {

    this.auth = function (name, password) {
      var request = {
        name: name,
        password: password
      };
      return $http.post(UrlBuilder.createApiUrl('/auth/authenticate'), request, {
        skipAuthorization: true
      }).then(function (response) {
        return response.data.token;
      });
    };

  };

  var UserService = function ($window, jwtHelper) {

    var storageKey = 'jwtToken';

    this.listeners = [];

    this.setToken = function (token) {
      $window.localStorage.setItem(storageKey, token);

      this.listeners.forEach(function (listener) {
        listener('tokenUpdated');
      }.bind(this));
    };

    this.getToken = function () {
      return $window.localStorage.getItem(storageKey);
    };

    this.removeToken = function () {
      $window.localStorage.removeItem(storageKey);

      this.listeners.forEach(function (listener) {
        listener('tokenRemoved');
      });
    };

    this.getInfos = function () {
      var token = this.getToken();
      if (token) {
        return jwtHelper.decodeToken(token);
      }
    };

    this.registerListener = function (listener) {
      this.listeners.push(listener);
    };

    this.removeListener = function (listener) {
      _.remove(this.listeners, function (l) {
        return l === listener;
      });
    };

  };

  angular.module('zucchini-ui-frontend')
    .controller('UserInfoCtrl', function (UserService, $scope) {

      this.user = UserService.getInfos();

      this.logout = function () {
        UserService.removeToken();
      };

      var eventListener = function (event) {
        switch (event) {
          case 'tokenUpdated':
            this.user = UserService.getInfos();
            break;

          case 'tokenRemoved':
            this.user = null;
            break;

          default:
            // Nothing to do
            break;
        }
      }.bind(this);

      UserService.registerListener(eventListener);

      $scope.$on('$destroy', function () {
        UserService.removeListener(eventListener);
      });

    })
    .controller('AuthCtrl', function () {


    })
    .controller('AuthFormController', function (AuthService, UserService) {

      this.name = '';
      this.password = '';

      this.auth = function () {
        AuthService.auth(this.name, this.password)
          .then(function (token) {
            UserService.setToken(token);
          });
      }.bind(this);

    })
    .service('AuthService', AuthService)
    .service('UserService', UserService)
    .config(function ($routeProvider) {
      $routeProvider
        .when('/auth', {
          templateUrl: 'views/auth.html',
          controller: 'AuthCtrl',
          controllerAs: 'ctrl'
        });
    })
    .factory('authHttpInterceptor', function ($q, $location) {
        return {
            responseError: function responseError(rejection) {
                if (rejection.status === 401) {
                  $location.path('/auth');
                }
                return $q.reject(rejection);
            }
        };
    })
    .config(function($httpProvider) {
      // The authHttpInterceptor must be the first interceptor
      $httpProvider.interceptors.unshift('authHttpInterceptor');
    })
    .config (function ($httpProvider, jwtInterceptorProvider) {
      // Get JWT token from user service
      jwtInterceptorProvider.tokenGetter = ['UserService', function (UserService) {
        return UserService.getToken();
      }];

      // Register JWT interceptor
      $httpProvider.interceptors.push('jwtInterceptor');
    });

})(angular);
