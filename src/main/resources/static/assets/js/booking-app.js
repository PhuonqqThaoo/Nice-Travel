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

	// btn tăng giảm số lượng
	$scope.adultPlus = function() {
		let value = parseInt($('#adult').text());
		if (value < 10) {
			value++;
			$('#adult').text(value)
		}
	}
	$scope.adultMinus = function() {
		let value = parseInt($('#adult').text());
		if (value <= 1) {
			value = 1;
			$('#adult').text(value);
			alert("Số lượng tối thiểu là 1");
		} else {
			value--;
			$('#adult').text(value)
		}
	}

	$scope.childrenPlus = function() {
		let value = parseInt($('#children').text());
		if (value < 10) {
			value++;
			$('#children').text(value)
		}
	}
	$scope.childrenMinus = function() {
		let value = parseInt($('#children').text());
		if (value <= 0) {
			value = 0;
			$('#children').text(value)
		} else {
			value--;
			$('#children').text(value)
		}
	}

	$scope.smallchildrenPlus = function() {
		let value = parseInt($('#smallchildren').text());
		if (value < 10) {
			value++;
			$('#smallchildren').text(value)
		}
	}
	$scope.smallchildrenMinus = function() {
		let value = parseInt($('#smallchildren').text());
		if (value <= 0) {
			value = 0;
			$('#smallchildren').text(value)
		} else {
			value--;
			$('#smallchildren').text(value)
		}
	}

	$scope.babyPlus = function() {
		let value = parseInt($('#baby').text());
		if (value < 10) {
			value++;
			$('#baby').text(value)
		}
	}
	$scope.babyMinus = function() {
		let value = parseInt($('#baby').text());
		if (value <= 0) {
			value = 0;
			$('#baby').text(value)
		} else {
			value--;
			$('#baby').text(value)
		}
	}

})
