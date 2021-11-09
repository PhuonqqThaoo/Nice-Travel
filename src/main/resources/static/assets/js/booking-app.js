const app = angular.module("booking-app", []);
app.controller("booking-ctrl", function($scope, $http) {
	/*hiển thị sản phẩm*/

	$scope.booking = {
		items: {},
		add(id) {
			this.items = {};
			$http.get(`/api/v1/travel/${id}`).then(resp => {
				this.items = resp.data;
				var json = JSON.stringify(angular.copy(this.items));
				sessionStorage.setItem("booking", json);
			})
		},
		loadFromLocalStorage() {
			var json = sessionStorage.getItem("booking");
			this.items = json ? JSON.parse(json) : {};
			console.log(this.items)
		}
	}
	$scope.booking.loadFromLocalStorage();

	$scope.plus = function() {
		let value = parseInt($('#adult').text());
		if (value < 10) {
			value++;
			$('#adult').text(value)
		}
	}

	$scope.minus = function() {
		let value = parseInt($('#adult').text());
		if (value <= 0) {
			value = 0;
			$('#adult').text(value)
		} else {
			value--;
			$('#adult').text(value)
		}
	}

})
