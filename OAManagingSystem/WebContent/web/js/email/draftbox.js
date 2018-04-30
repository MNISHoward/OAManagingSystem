$(function () {
	init3();
})


function init3() {
	$('#profile').hide();
	$('#left-navbar').css("marginTop", "0px");
	
	$('#draftbox-page-previous').css('display', 'none');
	$('#draftbox-content').height($(window).height()-110);
	if($('.draftbox-page-href').length > 1) {
		$('#draftbox-nav .nav-pagination').css('display', "block");
		$('.draftbox-page-href:contains(1)').addClass('active');
	}
	
	//收件人
	$('#draftbox-body .draftbox-detail .heading-sendPerson').each(function (index, value) {
		var name = $(this).text().match(/[\u4e00-\u9fa5]+/gi).join(',');
		$(this).text(name);
	})
}

/**
 * 详细内容
 * @param e
 * @returns
 */
$(document).on('click', '#draftbox-nav .draftbox-detail', function (e) {
	e.preventDefault();
	$('#draftbox-content-edit').attr('did', $(this).attr('did'));
	$('#draftbox-content-delete').attr('did', $(this).attr('did'));
	$('#draftbox-content-person .content-person-name').html("收件人：" + $(this).find('.heading-sendPerson').text());
	$('#draftbox-content-person .content-person-time').html("时间：" + $(this).find('.heading-sendTime').html());
	$('#draftbox-content-title').html("主题：" + $(this).find('.heading-title').text());
	$('#draftbox-content').html($(this).find('.list-group-item-content').html());
	$('#draftbox-nav').slideUp('slow');
	$('#draftbox-content-detail').slideDown('slow');
})

$('#draftbox-content-back').click(function (e) {
	e.preventDefault();
	$('#draftbox-nav').slideDown('slow');
	$('#draftbox-content-detail').slideUp('slow');
})

$('#draftbox-content-edit').click(function (e) {
	var $a = $('#draftbox-body .draftbox-detail[did='+ $(this).attr('did') +']');
	var JSON = {
		id : $(this).attr('did'),
		acceptDetail: $a.find('.list-group-item-sendPerson').html().trim(),
		content : $a.find('.list-group-item-content').html().trim(),
		title: $a.find('.list-group-item-title').text().trim(),
		flag : 3 //判断当前是草稿
	};
	$.get(ctx + $('#left-navbar .leftnav-menu-a[mid=33]').attr('href'), JSON, function(data) {
		$('#right-main').html(data);
	});
})

$('#draftbox-content-delete').click(function (e) {
	e.preventDefault();
	dialog.confirmFun("确定要删除该草稿吗？", deleteDraftAjax);
	var $this = $(this);
	function deleteDraftAjax () {
		var JSON = {
			did : $this.attr('did')
		};
		var paramIn = {
				service : 'draftboxService',
				method : 'deleteDraftById',
				param : JSON,
				success : function (data) {
					if(data.rtnCode == ajax.rtnCode.SUCCESS) {
						dialog.successNo(data.param.message);
						$('#draftbox-nav').slideDown('slow');
						$('#draftbox-content-detail').slideUp('slow');
						$('#draftbox-nav .draftbox-detail[did='+ $this.attr('did') +']').remove();
					}else {
						dialog.error(data.rtnMessage);
					}
				}
		  };
		ajax.query(paramIn);
	}
})


/**
 * 分页导航向前按钮
 * @param e
 * @returns
 */

$(document).on('click', '#draftbox-page-previous a', function (e) {
	e.preventDefault();
	var activePage = $('.draftbox-page-href.active');
	var page = activePage.find('a').text();
	
	function pagePreviousPage(data) {
		listDraftboxs(data.param.draftboxs);
		var prev = activePage.removeClass('active').prev().addClass('active');
		if(prev.prev().prop('id') == 'draftbox-page-previous'){
			prev.prev().css('display', 'none');
		}
		if($('#draftbox-page-behaviour').css('display') == 'none') {
			$('#draftbox-page-behaviour').css('display', 'inline');
		}
	}
	
	pageDraftbox(parseInt(page)-2, pagePreviousPage);
})

/**
 * 分页导航向后按钮
 * 
 * @param e
 * @returns
 */

$(document).on('click', '#draftbox-page-behaviour a', function (e) {
	e.preventDefault();
	var activePage = $('.draftbox-page-href.active');
	var page = activePage.find('a').text();
	function pageBehaviourPage(data) {
		listDraftboxs(data.param.draftboxs);
		var next = activePage.removeClass('active').next().addClass('active');
		if(next.next().prop('id') == 'draftbox-page-behaviour'){
			next.next().css('display', 'none');
		}
		if($('#draftbox-page-previous').css('display') == 'none') {
			$('#draftbox-page-previous').css('display', 'inline');
		}
	}
	pageDraftbox(parseInt(page), pageBehaviourPage);
})


/**
 * 分页普通按钮点击事件
 * @param e
 * @returns
 */
$(document).on('click', '.draftbox-page-href', function (e) {
	e.preventDefault();
	var $this = $(this);
	var page = $(this).find('a').text();
	
	function pageHrefPage(data) {
		listDraftboxs(data.param.draftboxs);
		$('.draftbox-page-href.active').removeClass('active');
		$this.addClass('active');
		if($this.prev().prop('id') == 'draftbox-page-previous'){
			$this.prev().css('display', 'none');
		}else {
			if($('#draftbox-page-previous').css('display') == 'none') {
				$('#draftbox-page-previous').css('display', 'inline');
			}
		}
		if($this.next().prop('id') == 'draftbox-page-behaviour'){
			$this.next().css('display', 'none');
		}else {
			if($('#draftbox-page-behaviour').css('display') == 'none') {
				$('#draftbox-page-behaviour').css('display', 'inline');
			}
		}
		
	}
	pageDraftbox(parseInt(page) - 1, pageHrefPage);
})

/**
 * 
 * 前端分页函数，根据当前页数进行分页
 * 
 * @param page 当前页数
 * @param succFunc 分页查询成功后执行函数
 * @returns
 */
function pageDraftbox(page, succFunc) {
	var JSON = {
			"page" : page
		};
		
	var paramIn = {
			service : 'draftboxService',
			method : 'getDraftboxPage',
			param : JSON,
			success : succFunc
		};
	ajax.query(paramIn);
}

/**
 * 列出所有的草稿
 * @param notifications
 * @returns
 */
function listDraftboxs(draftboxs) {
	var userBody = $('#draftbox-body');
	userBody.find('.draftbox-detail').not(':first').remove();
	var $sendPerson, $titlediv,$title, $summary,$email,$content,$hasSee,$sendTime,$h4,$btn,$manybtn = new Array();
	$(draftboxs).each(function (index, value) {
		$btn = $('<button type="button" oid="'+ value.id +'" class="draftbox-detail list-group-item clearfix">');
		if(value.acceptDetail != null && value.acceptDetail != ""){
			$sendPerson = $('<span class="heading-sendPerson" ></span>').text(value.acceptDetail.match(/[\u4e00-\u9fa5]+/gi).join(','));
		}else {
			$sendPerson = $('<span class="heading-sendPerson" ></span>').text('暂无');
		}
		if(value.title != null && value.title != "") {
			$title = $('<span class="heading-title" ></span>').text(value.title);
		}else {
			$title = $('<span class="heading-title" ></span>').text('暂无');
		}
		$summary = $('<span class="heading-summary"></span>').text('-' + value.summary);
		$sendTime = $('<span class="heading-sendTime"></span>').text(value.saveTime.replace(/T/, " "));
		$content = $("<div class='list-group-item-content' style='display:none;' ></div>").html(value.content);
		$email = $('<div class="list-group-item-sendPerson" style="display:none;" ></div>').html(value.acceptDetail);
		$titlediv = $('<div class="list-group-item-title" style="display:none;" ></div>').html(value.title);
		$h4 = $("<h4 class='list-group-item-heading clearfix'></h4>");
		$h4.append([$sendPerson, $title, $summary,$sendTime]);
		$btn.append([$h4, $content,$titlediv,$email]);
		$manybtn.push($btn);
	})
	userBody.append($manybtn);
}


//@ sourceURL=draftbox.js