
$('#logoutbtn').click(function (e) {
	e.preventDefault();
	dialog.confirm("确定退出账号?", ctx + '/logout');
})

$('#probtn').click(function (e) {
	e.preventDefault();
	var JSON = Util.formDataToJson(proForm);
	var paramIn = {
		service : 'staffService',
		method : 'updateStaffEPA',
		param : JSON,
		success : function (data){
			if(data.rtnCode == ajax.rtnCode.SUCCESS) {
				dialog.successNo(data.param.message);
			}else {
				dialog.error(data.rtnMessage);
			}
		}
	};
	ajax.query(paramIn);
})

$('#passbtn').click(function (e) {
	e.preventDefault();
	var JSON = Util.formDataToJson(passForm);
	var paramIn = {
		service : 'userService',
		method : 'updateUserPass',
		param : JSON,
		success : function (data){
			if(data.rtnCode == ajax.rtnCode.SUCCESS) {
				dialog.successNo(data.param.message);
			}else {
				dialog.error(data.rtnMessage);
			}
		}
	};
	ajax.query(paramIn);
})