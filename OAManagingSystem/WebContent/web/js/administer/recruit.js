$(function () {
	$('#recruit-nav').height($(window).height()-120);
	$('.form_datetime').datetimepicker({
        language:  'zh-CN',
        format: 'yyyy-mm-dd hh:ii:ss',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		forceParse: 0,
        showMeridian: 1
    }).on('changeDate', function (e) {
    	var name = $(this).find('input').attr('name');
   	 	$(this).parents('form').bootstrapValidator('revalidateField', name);
   });
	
	$('#staffForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	name: {
            	message: '英文名称验证失败',
                validators: {
                    notEmpty: {
                        message: '英文名称不能为空'
                    },
                    regexp : {
                     	regexp:/^[a-zA-Z]+$/,
                     	message : '英文名称格式不正确'
                     }
                }
            },
            titleName :  {
            	message: '中文名称验证失败',
                validators: {
                	 notEmpty: {
                         message: '中文名称不能为空'
                     },
                     regexp : {
                     	regexp:/^[\u4e00-\u9fa5]+$/,
                     	message : '中文名称格式不正确'
                     }
                },
            },
            email: {
                message: '邮箱验证失败',
                validators: {
                    notEmpty: {
                        message: '邮箱不能为空'
                    },
                    emailAddress : {
                    	message: '邮箱格式不正确'
                    }
                }
            },
            birthday : {
            	message: '出生日期验证失败',
            	validators: {
            		notEmpty : {
            			message : '出生日期不能为空'
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
            address : {
            	message: '地址验证失败',
            	validators: {
            		notEmpty : {
            			message : '地址不能为空',
            		},
            	}
            },
            salary : {
            	message: '工资验证失败',
            	validators: {
            		notEmpty : {
            			message : '工资不能为空',
            		},
            	}
            },
            job : {
            	message: '岗位验证失败',
            	validators: {
            		notEmpty : {
            			message : '岗位不能为空',
            		},
            	}
            },
            departmentId : {
            	message: '部门验证失败',
            	validators: {
            		notEmpty : {
            			message : '部门不能为空',
            		},
            	}
            },
            sex : {
            	message: '性别验证失败',
            	validators: {
            		notEmpty : {
            			message : '性别不能为空',
            		},
            	}
            }
        },
        excluded: [':disabled'] 
    }).on('success.form.bv', function(e) {
        // 阻止默认事件提交
        e.preventDefault();
        $('#saveStaffBtn').prop('disabled', false);
    });
})

$('#saveStaffBtn').click(function (e) {
	e.preventDefault();
	if(Util.validator(staffForm)){
		dialog.confirmFun("确认保存员工的信息吗？", sendAjaxWithStaffs);
		function sendAjaxWithStaffs() {
			var JSON = Util.formDataToJson(staffForm);
			var paramIn = {
					service : 'staffService',
					method : 'insertNewStaff',
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
	}
})