$(function () {
	$('#asset-nav').height($(window).height()-120)
	$('#assetForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	titleName: {
            	message: '资产名称验证失败',
                validators: {
                    notEmpty: {
                        message: '资产名称不能为空'
                    },
                }
            },
            model: {
                message: '型号验证失败',
                validators: {
                    notEmpty: {
                        message: '型号不能为空'
                    },
                }
            },
            price :  {
            	message: '价格验证失败',
                validators: {
                    notEmpty: {
                        message: '价格不能为空'
                    },
                    numeric: {message: '价格只能输入数字'}
                },
            },
            company : {
            	message: '制造商验证失败',
            	validators: {
            		notEmpty : {
            			message : '制造商不能为空',
            		},
            	}
            },
            repairPhone : {
            	message: '维修号码验证失败',
            	validators: {
            		notEmpty : {
            			message : '维修号码不能为空',
            		},
            		regexp : {
            			message : '维修号码格式不正确',
            			regexp : /^(0?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8})|(400|800)([0-9\\-]{7,10})|(([0-9]{4}|[0-9]{3})(-| )?)?([0-9]{7,8})((-| |转)*([0-9]{1,4}))?$/,
            		}
            	}
            },
            invoices : {
            	message: '发票税号验证失败',
            	validators: {
            		notEmpty : {
            			message : '发票税号不能为空',
            		},
            	}
            },
            phonenumber : {
            	message: '手机号码验证失败',
            	validators: {
            		notEmpty : {
            			message : '手机号码不能为空',
            		},
            		regexp : {
                    	regexp:/^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/,
                    	message : '手机号码格式不正确'
                    }
            	}
            },
        },
    }).on('success.form.bv', function(e) {
        // 阻止默认事件提交
        e.preventDefault();
        $('#saveAssetBtn').prop('disabled', false);
    });
})

$('#assetTypeId').change(function (e) {
	if($(this).val() == 9) {
		$('#phonenumber').prop('disabled', false);
	}else {
		$('#phonenumber').val("");
		$('#phonenumber').prop('disabled', true);
	}
})

$('#saveAssetBtn').click(function (e) {
	e.preventDefault();
	if(Util.validator(assetForm)){
		dialog.confirmFun("确认保存资产的信息吗？", sendAjaxWithAssets);
		function sendAjaxWithAssets() {
			var JSON = Util.formDataToJson(assetForm);
			var paramIn = {
					service : 'assetService',
					method : 'saveAsset',
					param : JSON,
					success : function (data){
						if(data.rtnCode == ajax.rtnCode.SUCCESS) {
							dialog.success(data.param.message,location.href);
						}else {
							dialog.error(data.rtnMessage);
						}
					}
				};
			ajax.query(paramIn);
		}
	}
})