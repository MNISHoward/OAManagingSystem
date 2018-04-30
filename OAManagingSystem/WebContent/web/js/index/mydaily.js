$(function () {
	$('#page-previous').css('display', 'none');
	if($('.page-href').length > 1) {
		$('#daily-nav .nav-pagination').css('display', "block");
		$('.page-href:contains(1)').addClass('active');
	}
	
	$('#daily-content-detail').hide();
	
	var ue = UE.getEditor('editor', {
		toolbars: [
		    ['fullscreen', 'source', 'undo', 'redo'],
		    ['bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc']
		]
		,zIndex : 0
		,autoHeightEnabled:false
		,scaleEnabled:false
	});
	
	$("#leader").autocomplete({
      source: function( request, response ) {
    	  var JSON = {
				param : $('#leader').val(),
			}
    	  var paramIn = {
			service : 'userService',
			method : 'findStaffwithIdAndName',
			param : JSON,
			success : function (data) {
				response($(data.param.users).map(function (index, value) {
					var obj = {};
					obj.label = '工号:' + value.staff.id + ', 名字:' + value.staff.titleName;
					obj.value = value.staff.titleName;
					obj.id = value.id;
					return obj;
				}))
			}
    	  };
		  ajax.query(paramIn);
     },
     select: function( event, ui ) {
    	 $('#dailyForm').attr('pid', ui.item.id);
     },
	});
	
	 $('#dailyForm').bootstrapValidator({
	        message: 'This value is not valid',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	title: {
	                message: '内容验证失败',
	                validators: {
	                    notEmpty: {
	                        message: '内容不能为空'
	                    },
	                }
	            },
	        },
	        excluded: [':disabled'] 
	    }).on('success.form.bv', function(e) {
	        // 阻止默认事件提交
	        e.preventDefault();
	        $('#saveDailyBtn').prop('disabled', false);
	    });
});

/**
 * 分页导航向前按钮
 * @param e
 * @returns
 */

$(document).on('click', '#page-previous a', function (e) {
	e.preventDefault();
	var activePage = $('.page-href.active');
	var page = activePage.find('a').text();
	
	function pagePreviousPage(data) {
		listDaily(data.param.dailys);
		var prev = activePage.removeClass('active').prev().addClass('active');
		if(prev.prev().prop('id') == 'page-previous'){
			prev.prev().css('display', 'none');
		}
		if($('#page-behaviour').css('display') == 'none') {
			$('#page-behaviour').css('display', 'inline');
		}
	}
	
	pageDaily(parseInt(page)-2, pagePreviousPage);
})

/**
 * 分页导航向后按钮
 * 
 * @param e
 * @returns
 */

$(document).on('click', '#page-behaviour a', function (e) {
	e.preventDefault();
	var activePage = $('.page-href.active');
	var page = activePage.find('a').text();
	function pageBehaviourPage(data) {
		listDaily(data.param.dailys);
		var next = activePage.removeClass('active').next().addClass('active');
		if(next.next().prop('id') == 'page-behaviour'){
			next.next().css('display', 'none');
		}
		if($('#page-previous').css('display') == 'none') {
			$('#page-previous').css('display', 'inline');
		}
	}
	pageDaily(parseInt(page), pageBehaviourPage);
})


/**
 * 分页普通按钮点击事件
 * @param e
 * @returns
 */
$(document).on('click', '.page-href', function (e) {
	e.preventDefault();
	var $this = $(this);
	var page = $(this).find('a').text();
	
	function pageHrefPage(data) {
		listDaily(data.param.dailys);
		$('.page-href.active').removeClass('active');
		$this.addClass('active');
		if($this.prev().prop('id') == 'page-previous'){
			$this.prev().css('display', 'none');
		}else {
			if($('#page-previous').css('display') == 'none') {
				$('#page-previous').css('display', 'inline');
			}
		}
		if($this.next().prop('id') == 'page-behaviour'){
			$this.next().css('display', 'none');
		}else {
			if($('#page-behaviour').css('display') == 'none') {
				$('#page-behaviour').css('display', 'inline');
			}
		}
		
	}
	pageDaily(parseInt(page) - 1, pageHrefPage);
})

/**
 * 
 * 前端分页函数，根据当前页数进行分页
 * 
 * @param page 当前页数
 * @param succFunc 分页查询成功后执行函数
 * @returns
 */
function pageDaily(page, succFunc) {
	var JSON = {
			"page" : page
		};
		
	var paramIn = {
			service : 'dailyService',
			method : 'getOwnDailyPage',
			param : JSON,
			success : succFunc
		};
	ajax.query(paramIn);
}

/**
 * 列出所有的通知
 * @param notifications
 * @returns
 */
function listDaily(dailys) {
	var userBody = $('#daily-body');
	userBody.empty();
	var $titleName, $title, $updateTime,$content,$hasSee, $h4,$a,$manya = new Array();
	$(dailys).each(function (index, value) {
		$title = $("<span class='heading-title' ></span>").html(value.title + "&nbsp;");
		$titleName = $("<span class='heading-author' ></span>").text("作者：" + value.author.staff.titleName);
		$updateTime = $("<span class='heading-updateTime'></span>").append($('<small></small>').text("修改时间:"+ value.updateTime.replace(/T/, " ") ));
		$p = $("<p class='list-group-item-text'></p>").html(value.summary);
		$div = $("<div class='list-group-item-content' style='display:none;' ></div>").html(value.content);
		$h4 = $("<h4 class='list-group-item-heading'></h4>");
		$h4.append([$title, $titleName, $updateTime]);
		$a = $('<a href="#" did='+value.id+' class="daily-detail list-group-item"></a>').append([$h4, $p,$div]);
		$manya.push($a);
	})
	userBody.append($manya);
}

/**
 * 详细内容
 * @param e
 * @returns
 */
$(document).on('click', '#daily-body .daily-detail', function (e) {
	e.preventDefault();
	$('#title').val($(this).find('.heading-title').text());
	$('#id').prop('disabled', false);
	$('#id').val(parseInt($(this).attr('did')));
	UE.getEditor('editor').setContent($(this).find('.list-group-item-content').html());
	$('#daily-nav').slideUp('slow');
	$('#daily-content-detail').slideDown('slow');
})
/**
 * 返回
 * @param e
 * @returns
 */
$('#daily-content-back').click(function (e) {
	e.preventDefault();
	$('#dailyForm').bootstrapValidator('resetForm', true);
	$('#daily-nav').slideDown('slow');
	$('#daily-content-detail').slideUp('slow');
});
/**
 * 撰写按钮
 * @param e
 * @returns
 */
$('#newDailyBtn').click(function (e) {
	e.preventDefault();
	$('#title').val("");
	$('#leader').val("");
	$('#id').val(-1);
	UE.getEditor('editor').setContent("");
	$('#daily-nav').slideUp('slow');
	$('#daily-content-detail').slideDown('slow');
});

/**
 * 保存和修改日报
 * @param e
 * @returns
 */
$('#saveDailyBtn').click(function (e) {
	e.preventDefault();
	ue = UE.getEditor('editor');
	if(ue.hasContents()){ //此处以非空为例
		if(Util.validator(dailyForm)){
			var text = ue.getContentTxt();
			var pid = $('#dailyForm').attr('pid');
			if(pid == undefined) {
				dialog.error("先选择上司");
				return false;
			}
			$('#leader').val(pid);
			$('#summary').val(text);
			$('#saveDailyBtn').prop('disabled', true);
			ue.sync();       //同步内容
			dailyForm.submit();   //提交Form
		}
	}else {
		dialog.error("内容不能为空");
	}
})

//@ sourceURL=mydaily.js