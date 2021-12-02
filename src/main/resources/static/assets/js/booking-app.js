const app = angular.module("booking-app", []);
app.controller("booking-ctrl", function($scope, $http) {
	/*hiển thị sản phẩm*/

	$scope.load = {
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
	
	/*lưu đơn hàng API*/
	$scope.booking = {
		createdDate : new Date(),
		get address() {
			return  document.getElementById("address").value;
		},
		get phone() {
			return  document.getElementById("phone").value;
		},
		get totalPrice() {
			return  Number($('#total').text());
		},
		payBoolean : false,
		isDeleted : false,
		accountId : {id : Number($("#accountId").text())},
		get bookingDetails(){
			return {
				travelId : {id : Number($('#travelId').text())},
				price : Number($('#total').text()),
				quantity : ($scope.load.qtyAdult + $scope.load.qtyChildren + $scope.load.qtySmallchildren + $scope.load.qtyBaby)
			}
		},
		purchaser(){
			var booking = angular.copy(this);
			console.log(booking)
			//thực hiện đặt hàng
			$http.post("/api/v2/booking", booking).then(resp => {
				alert("Đặt hàng thành công")
				location.href = "/"; 
			}).catch(error => {
				alert("Đặt hàng lỗi!")
				console.log(error)
			})
		}
	}
	
	
	var qtynew = parseInt($('#qtynew').text());
	var totalqty = parseInt($('#adult').text()) + parseInt($('#children').text()) + parseInt($('#smallchildren').text()) + parseInt($('#baby').text());
	
	console.log(qtynew)
	// btn tăng giảm số lượng
	$scope.adultPlus = function() {
		if(totalqty == qtynew){
			return;
		}
		qtynew--;
		$('#adult').text(parseInt($('#adult').text()) + 1)
		console.log(qtynew)
	}
	$scope.adultMinus = function() {
		if (parseInt($('#adult').text()) <= 1) {
			$('#adult').text(1);
			alert("Số lượng tối thiểu là 1");
		} else {
			qtynew++;
			$('#adult').text(parseInt($('#adult').text()) - 1)
			console.log(qtynew)
		}
	}
	
	$scope.childrenPlus = function() {
		if(totalqty == qtynew){
			return;
		}
		qtynew--;
		$('#children').text(parseInt($('#children').text()) + 1)
		console.log(qtynew)
	}
	$scope.childrenMinus = function() {
		if (parseInt($('#children').text()) <= 0) {
			$('#children').text(0)
		} else {
			qtynew++;
			$('#children').text(parseInt($('#children').text()) - 1)
			console.log(qtynew)
		}
	}

	$scope.smallchildrenPlus = function() {
		if(totalqty == qtynew){
			return;
		}
		qtynew--;
		$('#smallchildren').text(parseInt($('#smallchildren').text()) + 1)
		console.log(qtynew)
	}
	$scope.smallchildrenMinus = function() {
		if (parseInt($('#smallchildren').text()) <= 0) {
			$('#smallchildren').text(0)
		} else {
			qtynew++;
			$('#smallchildren').text(parseInt($('#smallchildren').text()) - 1)
			console.log(qtynew)
		}
	}

	$scope.babyPlus = function() {
		if(totalqty == qtynew){
			return;
		}
		qtynew--;
		$('#baby').text(parseInt($('#baby').text()) + 1)
		console.log(qtynew)
	}
	$scope.babyMinus = function() {
		
		if (parseInt($('#baby').text()) <= 0) {
			$('#baby').text(0)
		} else {
			qtynew++;
			$('#baby').text(parseInt($('#baby').text()) - 1)
			console.log(qtynew)
		}
	}

})
