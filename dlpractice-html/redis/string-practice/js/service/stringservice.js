//String-Practice-Service
app.factory('stringPracticeService', ['$http', function($http) {
	return {
		setKeyAndValue : function(key,value){
			return $http.get(webapp+'/redis/string/setstring?key='+key+'&value='+value);
		},
		getKeyAndValue : function(key,value){
			return $http.get(webapp+'/redis/string/getstring?key='+key);
		},
	};
}]);