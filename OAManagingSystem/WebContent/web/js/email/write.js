$(function () {
	var ue = UE.getEditor('editor', {
		toolbars: [[
            'undo', 'redo', '|',
            'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'pasteplain', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist',
            'fontfamily', 'fontsize', '|',
            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|',
            'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
            'simpleupload', 'insertimage', 'emotion', 'scrawl','attachment','insertcode','background', '|',
            'horizontal', 'date', 'time','wordimage', '|',
            'print', 'preview'
        ]]
		,zIndex : 0
		,autoHeightEnabled:false
		,scaleEnabled:false
	});
	
	$("#accpetPersonSearch").autocomplete({
	      source: function( request, response ) {
	    	  var JSON = {
					param : $('#accpetPersonSearch').val(),
				}
	    	  var paramIn = {
				service : 'userService',
				method : 'findStaffwithNameAndEmail',
				param : JSON,
				success : function (data) {
					response($(data.param.users).map(function (index, value) {
						var obj = {};
						obj.label = value.staff.titleName + "<" + value.staff.email + ">";
						obj.name = value.staff.titleName + "<" + value.staff.email + ">";
						obj.id = value.id;
						return obj;
					}))
				}
	    	  };
			  ajax.query(paramIn);
	     },
	     select: function( event, ui ) {
	    	 var span = $('<span class="accpetPersonSpan" uid=' + ui.item.id + ' ></span>').text(ui.item.name);
	    	 span.append($('<span class="accpetPersonTime" >&times;</span>'));
	    	 $('#accpetPersonDiv').append(span);
	    	 $('#accpetPersonSearch').val("");
	    	 return false;
	     },
		});
	
	
	$('#editor').width($(window).width()-200);
})


/**
 * 发送邮件
 * @param e
 * @returns
 */
$('#sendEmailBtn').click(function (e) {
	e.preventDefault();
	ue = UE.getEditor('editor');
	if(ue.hasContents()){ //此处以非空为例
		
		var span = $('#accpetPersonDiv').find('.accpetPersonSpan');
		if(span.length < 1) {
			dialog.error("先选择收件人");
			return false;
		}
		
		//获取收件人
		var val = "";
		span.each(function (index, value) {
			var uid = $(this).attr('uid');
			val += uid + ',';
		})
		val = val.substring(0, val.lastIndexOf(','));
		$('#accpetPersonId').val(val);
		
		
		//获取收件信息
		$('#acceptDetail').val($('#accpetPersonDiv').html().trim());
		
		var text = ue.getContentTxt();
		$('#summary').val(text);
		ue.sync();       //同步内容
		emailForm.submit();   //提交Form
		
		$('#sendEmailBtn').prop('disabled', true);
	}else {
		dialog.error("先输入内容");
		return false;
	}
	
})


$(document).on('click', '#accpetPersonDiv .accpetPersonTime' , function (e) {
	$(this).parents('.accpetPersonSpan').remove();
})

$('#saveDraftBtn').click(function (e) {
	e.preventDefault();
	ue = UE.getEditor('editor');
	//获取收件信息
	$('#acceptDetail').val($('#accpetPersonDiv').html().trim());
	
	var text = ue.getContentTxt();
	$('#summary').val(text);
	var JSON = Util.formDataToJson(emailForm);
	JSON.content=ue.getContent();
	  var paramIn = {
		service : 'draftboxService',
		method : 'saveDraftboxInSendEmail',
		param : JSON,
		success : function (data) {
			if(data.rtnCode == ajax.rtnCode.SUCCESS) {
				$('#did').val(data.param.did);
				dialog.successNo(data.param.message);
			}else {
				dialog.error(data.rtnMessage);
			}
		}
	  };
	  ajax.query(paramIn);
})


$('#closeBtn').click(function (e) {
	e.preventDefault();
	window.location = window.location.href;
})

//@ sourceURL=write.js