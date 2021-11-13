const app = angular.module("booking-app", []);
app.controller("booking-ctrl", function($scope, $http) {
	/*hiển thị sản phẩm*/

	$scope.load = {
		items: {},
		add(id) {
			$http.get(`/api/v1/travel/${id}`).then(resp => {
				this.items = resp.data;
				var json = JSON.stringify(angular.copy(this.items));
				sessionStorage.setItem("booking", json);
				console.log(this.items)
			})
		},
		get amout (){
			return (this.items.price * this.qtyAdult) + ((this.items.price - (this.items.price * 0.1)) * this.qtyChildren) + ((this.items.price - (this.items.price * 0.3)) * this.qtySmallchildren) + ((this.items.price - (this.items.price * 0.5)) * this.qtyBaby);
		},
		loadFromLocalStorage() {
			var json = sessionStorage.getItem("booking");
			this.items = json ? JSON.parse(json) : {};
			console.log(this.items)
		},
		get qtyAdult(){
			return parseInt($('#adult').text());
		},
		get qtyChildren(){
			return parseInt($('#children').text());
		},
		get qtySmallchildren(){
			return parseInt($('#smallchildren').text());
		},
		get qtyBaby(){
			return parseInt($('#baby').text());
		}
	}
	$scope.load.loadFromLocalStorage();
	
	/*lưu đơn hàng API*/
	$scope.booking = {
		createdDate : new Date(),
		address: $('#address').text(),
		phone: $('#phone').text(),
		get totalPrice() {
			return  $scope.load.amout;
		},
		payBoolean : false,
		isDeleted : false,
		accountId : {id : $("#accountId").text()},
		get bookingDetails(){
			return {
				travelId : {id : $scope.load.items.id},
				price : $scope.load.amout,
			}
		},
		purchaser(){
			var booking = angular.copy(this);
			//thực hiện đặt hàng
			$http.post("/api/v1/booking", booking).then(resp => {
				alert("Đặt hàng thành công")
				location.href = "/"; 
			}).catch(error => {
				alert("Đặt hàng lỗi!")
				console.log(error)
			})
		}
	}
	
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
