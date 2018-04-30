$(function () {
	$('#proForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            address: {
            	message: '地址验证失败',
                validators: {
                    notEmpty: {
                        message: '家庭住址不能为空'
                    },
                }
            },
            phone :  {
            	message: '手机号码验证失败',
                validators: {
                    notEmpty: {
                        message: '手机号码不能为空'
                    },
                    regexp : {
                    	regexp:/^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/,
                    	message : '手机号码格式不正确'
                    }
                },
            },
        }
    }).on('success.form.bv', function(e) {
        // 阻止默认事件提交
        e.preventDefault();
        $('#probtn').prop('disabled', false);
    });
	
	$('#passForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	oldPassword: {
                message: '旧密码验证失败',
                validators: {
                    notEmpty: {
                        message: '旧密码不能为空'
                    },
                }
            },
            newPassword: {
            	message: '新密码验证失败',
                validators: {
                    notEmpty: {
                        message: '新密码不能为空'
                    },
                    different: {
                        field: 'oldPassword',
                        message: '新密码和旧密码不能相同。'
                    }
                }
            },
            newRePassword :  {
            	message: '确认新密码验证失败',
                validators: {
                	 notEmpty: {
                         message: '确认新密码不能为空'
                     },
                	identical: {
                        field: 'newPassword',
                        message: '与新密码不一致'
                    },
                },
            },
        }
    }).on('success.form.bv', function(e) {
        // 阻止默认事件提交
        e.preventDefault();
        $('#passbtn').prop('disabled', false);
    });
})
$('#logoutbtn').click(function (e) {
	e.preventDefault();
	dialog.confirm("确定退出账号?", ctx + '/logout');
})

$('#probtn').click(function (e) {
	e.preventDefault();
	if(Util.validator(proForm)){
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
	}
})

$('#passbtn').click(function (e) {
	e.preventDefault();
	if(Util.validator(passForm)){
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
	}
})