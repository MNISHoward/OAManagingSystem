$(function () {
    $('#loginForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            username: {
                message: '用户名验证失败',
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    },
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                }
            }
        }
    }).on('success.form.bv', function(e) {
        // 阻止默认事件提交
        e.preventDefault();
        var JSON = Util.formDataToJson(loginForm);
		var paramIn = {
			service : 'userLoginService',
			method : 'userlogin',
			param : JSON,
			success : function (data){
				if(data.rtnCode == ajax.rtnCode.SUCCESS) {
					dialog.success(data.param.message, ctx + "/index.do?rid=1");
				}else {
					dialog.error(data.rtnMessage);
				}
			}
		};
		ajax.query(paramIn);
    });
    
    $('#registerForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	reginame: {
                message: '用户名验证失败',
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    },
                }
            },
            regipass: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                }
            },
            repassword: {
                validators: {
                	notEmpty: {
                        message: '确认密码不能为空'
                    },
                	identical: {
                        field: 'regipass',
                        message: '两次输入的密码不相符'
                    }
                }
            }
        }
    }).on('success.form.bv', function(e) {
        // 阻止默认事件提交
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
});

$('#loginbtn').click(function (e) {
	e.preventDefault();
	if(Util.validator(loginForm)){
		var JSON = Util.formDataToJson(loginForm);
		var paramIn = {
			service : 'userLoginService',
			method : 'userlogin',
			param : JSON,
			success : function (data){
				if(data.rtnCode == ajax.rtnCode.SUCCESS) {
					dialog.success(data.param.message, ctx + "/index.do?rid=1");
				}else {
					dialog.error(data.rtnMessage);
				}
			}
		};
		ajax.query(paramIn);
	}
});

$('#regbtn').click(function (e) {
	e.preventDefault();
	if(Util.validator(registerForm)){
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
	}
});