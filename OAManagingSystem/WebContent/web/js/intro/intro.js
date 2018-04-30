$(function () {
	init2();
	
	var ue = UE.getEditor('editor', {
		toolbars: [
		    ['source', 'undo', 'redo'],
		    ['bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc']
		]
		,zIndex : 1200
		,autoHeightEnabled:false
		,scaleEnabled:false
	});
	
	$('#introForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	name: {
            	message: '公司名称验证失败',
                validators: {
                    notEmpty: {
                        message: '公司名称不能为空'
                    },
                }
            },
            phone: {
            	message: '公司电话验证失败',
                validators: {
                    notEmpty: {
                        message: '公司电话不能为空'
                    },
                    regexp : {
            			message : '维修号码格式不正确',
            			regexp : /^(0?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8})|(400|800)([0-9\\-]{7,10})|(([0-9]{4}|[0-9]{3})(-| )?)?([0-9]{7,8})((-| |转)*([0-9]{1,4}))?$/,
            		}
                }
            },
            domain: {
            	message: '公司域名验证失败',
                validators: {
                    notEmpty: {
                        message: '公司域名不能为空'
                    },
                    regexp: {
                    	message: '公司域名格式不正确',
                    	regexp: /^[a-zA-Z]+$/
                    }
                }
            },
            address :  {
            	message: '公司地址验证失败',
                validators: {
                	 notEmpty: {
                         message: '公司地址不能为空'
                     },
                },
            },
        },
        excluded: [':disabled'] 
    });
	
})


function init2() {
	$('#company-nav .company-intro, #company-nav .company-detail').height(($(window).height() - 80));
}

$('#company-nav .glyphicon-pencil').click(function (e) {
	$('.intro-modal-lg').modal('show');
})

$('.modal').on("hidden.bs.modal", function () {
	$('#introForm').data('bootstrapValidator').resetField('name');
	$('#introForm').data('bootstrapValidator').resetField('phone');
	$('#introForm').data('bootstrapValidator').resetField('domain');
	$('#introForm').data('bootstrapValidator').resetField('address');
})

//@ sourceURL=intro.js