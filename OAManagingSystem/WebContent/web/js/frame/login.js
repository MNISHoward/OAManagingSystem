$('#loginbtn').click(function (e) {
	e.preventDefault();
	var JSON = Util.formDataToJson(loginForm);
	var paramIn = {
		service : 'userLoginService',
		method : 'userlogin',
		param : JSON,
		success : function (data){
			if(data.rtnCode == ajax.rtnCode.SUCCESS) {
				dialog.success(data.param.message, ctx + "/index.do");
			}else {
				dialog.error(data.rtnMessage);
			}
		}
	};
	ajax.query(paramIn);
});

$('#regbtn').click(function (e) {
	e.preventDefault();
	var JSON = Util.formDataToJson(registerForm);
	var paramIn = {
			service : 'userRegisterService',
			method : 'userRegister',
			param : JSON,
			success : function (data){
				if(data.rtnCode == ajax.rtnCode.SUCCESS) {
					dialog.success(data.param.message, ctx + "/login.do");
				}else {
					dialog.error(data.rtnMessage);
				}
			}
		};
		ajax.query(paramIn);
});