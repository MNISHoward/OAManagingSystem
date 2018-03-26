$(function () {
	init3();
})


function init3() {
	$('#profile').hide();
	$('#left-navbar').css("marginTop", "0px");
	
	$('#outbox-page-previous').css('display', 'none');
	$('#outbox-content').height($(window).height()-110);
	if($('.outbox-page-href').length > 1) {
		$('#outbox-nav .nav-pagination').css('display', "block");
		$('.outbox-page-href:contains(1)').addClass('active');
	}
	
	//收件人
	$('#outbox-body .outbox-detail .heading-sendPerson').each(function (index, value) {
		var name = $(this).text().match(/[\u4e00-\u9fa5]+/gi).join(',');
		$(this).text(name);
	})
}

/**
 * 详细内容
 * @param e
 * @returns
 */
$(document).on('click', '#outbox-nav .outbox-detail', function (e) {
	e.preventDefault();
	$('#outbox-content-edit').attr('oid', $(this).attr('oid'));
	$('#outbox-content-delete').attr('oid', $(this).attr('oid'));
	$('#outbox-content-person .content-person-name').html("收件人：" + $(this).find('.heading-sendPerson').text());
	$('#outbox-content-person .content-person-time').html("时间：" + $(this).find('.heading-sendTime').html());
	$('#outbox-content-title').html("主题：" + $(this).find('.heading-title').text());
	$('#outbox-content').html($(this).find('.list-group-item-content').html());
	$('#outbox-nav').slideUp('slow');
	$('#outbox-content-detail').slideDown('slow');
})

$('#outbox-content-back').click(function (e) {
	e.preventDefault();
	$('#outbox-nav').slideDown('slow');
	$('#outbox-content-detail').slideUp('slow');
})

$('#outbox-content-edit').click(function (e) {
	var $a = $('#outbox-body .outbox-detail[oid='+ $(this).attr('oid') +']');
	var JSON = {
		acceptDetail: $a.find('.list-group-item-sendPerson').html().trim(),
		content : $a.find('.list-group-item-content').html().trim(),
		title: $a.find('.heading-title').text().trim(),
		flag : 2 //判断当前是发件
	};
	$('#right-main').load(ctx + $('#left-navbar .leftnav-menu-a[mid=33]').attr('href'), JSON);
})

$('#outbox-content-delete').click(function (e) {
	e.preventDefault();
	dialog.confirmFun("确定要删除该邮件吗？", deleteOutAjax);
	var $this = $(this);
	function deleteOutAjax () {
		var JSON = {
			oid : $this.attr('oid')
		};
		var paramIn = {
				service : 'outboxService',
				method : 'deleteOutById',
				param : JSON,
				success : function (data) {
					if(data.rtnCode == ajax.rtnCode.SUCCESS) {
						dialog.successNo(data.param.message);
						$('#outbox-nav').slideDown('slow');
						$('#outbox-content-detail').slideUp('slow');
						$('#outbox-nav .outbox-detail[oid='+ $this.attr('oid') +']').remove();
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

$(document).on('click', '#outbox-page-previous a', function (e) {
	e.preventDefault();
	var activePage = $('.outbox-page-href.active');
	var page = activePage.find('a').text();
	
	function pagePreviousPage(data) {
		listOutboxs(data.param.outboxs);
		var prev = activePage.removeClass('active').prev().addClass('active');
		if(prev.prev().prop('id') == 'outbox-page-previous'){
			prev.prev().css('display', 'none');
		}
		if($('#outbox-page-behaviour').css('display') == 'none') {
			$('#outbox-page-behaviour').css('display', 'inline');
		}
	}
	
	pageOutbox(parseInt(page)-2, pagePreviousPage);
})

/**
 * 分页导航向后按钮
 * 
 * @param e
 * @returns
 */

$(document).on('click', '#outbox-page-behaviour a', function (e) {
	e.preventDefault();
	var activePage = $('.outbox-page-href.active');
	var page = activePage.find('a').text();
	function pageBehaviourPage(data) {
		listOutboxs(data.param.outboxs);
		var next = activePage.removeClass('active').next().addClass('active');
		if(next.next().prop('id') == 'outbox-page-behaviour'){
			next.next().css('display', 'none');
		}
		if($('#outbox-page-previous').css('display') == 'none') {
			$('#outbox-page-previous').css('display', 'inline');
		}
	}
	pageOutbox(parseInt(page), pageBehaviourPage);
})


/**
 * 分页普通按钮点击事件
 * @param e
 * @returns
 */
$(document).on('click', '.outbox-page-href', function (e) {
	e.preventDefault();
	var $this = $(this);
	var page = $(this).find('a').text();
	
	function pageHrefPage(data) {
		listOutboxs(data.param.outboxs);
		$('.outbox-page-href.active').removeClass('active');
		$this.addClass('active');
		if($this.prev().prop('id') == 'outbox-page-previous'){
			$this.prev().css('display', 'none');
		}else {
			if($('#outbox-page-previous').css('display') == 'none') {
				$('#outbox-page-previous').css('display', 'inline');
			}
		}
		if($this.next().prop('id') == 'outbox-page-behaviour'){
			$this.next().css('display', 'none');
		}else {
			if($('#outbox-page-behaviour').css('display') == 'none') {
				$('#outbox-page-behaviour').css('display', 'inline');
			}
		}
		
	}
	pageOutbox(parseInt(page) - 1, pageHrefPage);
})

/**
 * 
 * 前端分页函数，根据当前页数进行分页
 * 
 * @param page 当前页数
 * @param succFunc 分页查询成功后执行函数
 * @returns
 */
function pageOutbox(page, succFunc) {
	var JSON = {
			"page" : page
		};
		
	var paramIn = {
			service : 'outboxService',
			method : 'getOutboxPage',
			param : JSON,
			success : succFunc
		};
	ajax.query(paramIn);
}

/**
 * 列出所有的发件
 * @param notifications
 * @returns
 */
function listOutboxs(outboxs) {
	var userBody = $('#outbox-body');
	userBody.find('.outbox-detail').not(':first').remove();
	var $sendPerson, $title, $summary,$email,$content,$hasSee,$sendTime,$h4,$btn,$manybtn = new Array();
	$(outboxs).each(function (index, value) {
		$btn = $('<button type="button" oid="'+ value.id +'" class="outbox-detail list-group-item clearfix">');
		$sendPerson = $('<span class="heading-sendPerson" ></span>').text(value.acceptDetail.match(/[\u4e00-\u9fa5]+/gi).join(','));
		$title = $('<span class="heading-title" ></span>').text(value.title);
		$summary = $('<span class="heading-summary"></span>').text('-' + value.summary);
		$sendTime = $('<span class="heading-sendTime"></span>').text(value.sendTime.replace(/T/, " "));
		$content = $("<div class='list-group-item-content' style='display:none;' ></div>").html(value.content);
		$email = $('<div class="list-group-item-sendPerson" style="display:none;" ></div>').html(value.acceptDetail);
		$h4 = $("<h4 class='list-group-item-heading clearfix'></h4>");
		$h4.append([$sendPerson, $title, $summary,$sendTime]);
		$btn.append([$h4, $content,$email]);
		$manybtn.push($btn);
	})
	userBody.append($manybtn);
}


//@ sourceURL=outbox.js