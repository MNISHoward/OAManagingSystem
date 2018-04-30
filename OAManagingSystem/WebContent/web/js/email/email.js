$(function () {
	init2();
})


function init2() {
	$('#profile').hide();
	$('#left-navbar').css("marginTop", "0px");
	
	$('#email-page-previous').css('display', 'none');
	$('#email-content').height($(window).height()-110);
	$('#left-navbar .leftnav-menu-a[mid=34]').find('.icon-text').text($('#left-navbar .leftnav-menu-a[mid=34]').find('.icon-text').text() + "(" + inboxCount + ")");
	if($('.email-page-href').length > 1) {
		$('#email-nav .nav-pagination').css('display', "block");
		$('.email-page-href:contains(1)').addClass('active');
	}
	
	$('#left-navbar .leftnav-menu-a[mid=34]').click(function (e) {
		e.preventDefault();
		window.location=window.location.href;
	});
	$('#left-navbar .leftnav-menu-a[mid=33]').unbind();
	$('#left-navbar .leftnav-menu-a[mid=33]').click(function (e) {
		var $this = $(this);
		$this.addClass('disabled');
		e.preventDefault();
		var url = $this.attr('href');
		if(url !== undefined && (window.asure == undefined || window.asure != false)) {
			$.get(ctx + url, {flag:0}, function(data) {
				if(data.indexOf('rtnCode') != -1) {
					data = JSON.parse(data);
				}
				if(data.rtnCode == ajax.rtnCode.NOLOGIN) {
					dialog.success(data.rtnMessage, window.location.href);
				}else{
					$('#right-main').html(data);
					$('.leftnav-menu-a').removeClass('disabled');
					window.asure = true;
				}
			});
		}
		window.asure = false;
	})
	
	window.intervalGetInbox = setInterval(function () {
		var JSON = {};
		var paramIn = {
				service : 'inboxService',
				method : 'intervalGetInbox',
				param : JSON,
				success : function(data) {
					if(data.rtnCode == ajax.rtnCode.SUCCESS) {
						var activePage = $('.email-page-href.active');
						var emailNav = $('#email-nav');
						if(data.param.hasChange) {
							if(emailNav.length > 0) {
								//如果当前在收件箱，同时是第一页的话， 就重新加载收件箱
								if((activePage.length > 0 && activePage.text() == '1') || $('.email-page-href').length <= 1)
									listInboxs(data.param.inboxs);
								//同时判断是否分页和之前不一样的时候，则再一页
								if($('.email-page-href').length < data.param.totalPages) {
									$('#email-nav .nav-pagination').css('display', "block");
									$('.email-page-href:contains(1)').addClass('active');
									$('#email-nav .nav-pagination #email-page-behaviour').before($("<li class='email-page-href' ><a href = '#'>" + data.param.totalPages + "</a></li>"));
								}
							}
							var text = $('#left-navbar .leftnav-menu-a[mid=34]').find('.icon-text').text();
							$('#left-navbar .leftnav-menu-a[mid=34]').find('.icon-text').text(text.substring(0, text.indexOf('(')) + "(" + data.param.inboxCount + ")");
						}
					}else {
						dialog.error(data.rtnMessage);
					}
				}
			};
		ajax.query(paramIn);
	}, 10000);
	
}

/**
 * 详细内容
 * @param e
 * @returns
 */
$(document).on('click', '#email-nav .inbox-detail', function (e) {
	e.preventDefault();
	if($(this).hasClass('new')) {
		$(this).removeClass('new');
		var JSON = {
				"iid" : $(this).attr('iid'),
			};
		var paramIn = {
				service : 'inboxService',
				method : 'updateInboxHasSee',
				param : JSON,
				success : function() {}
			};
		ajax.query(paramIn);
	}
	$('#email-content-edit').attr('iid', $(this).attr('iid'));
	$('#email-content-delete').attr('iid', $(this).attr('iid'));
	$('#email-content-person .content-person-name').html("发件人：" + $(this).find('.heading-sendPerson').text());
	$('#email-content-person .content-person-email').text("<" + $(this).find('.list-group-item-email').html() + ">");
	$('#email-content-person .content-person-time').html("时间：" + $(this).find('.heading-sendTime').html());
	$('#email-content-title').html("主题：" + $(this).find('.heading-title').text());
	$('#email-content').html($(this).find('.list-group-item-content').html());
	$('#email-nav').slideUp('slow');
	$('#email-content-detail').slideDown('slow');
})

$('#email-content-back').click(function (e) {
	e.preventDefault();
	$('#email-nav').slideDown('slow');
	$('#email-content-detail').slideUp('slow');
})

$('#email-content-edit').click(function (e) {
	var $a = $('#inbox-body .inbox-detail[iid='+ $(this).attr('iid') +']');
	var JSON = {
		content : $a.find('.list-group-item-content').html().trim(),
		title: $a.find('.heading-title').text().trim(),
		flag : 1 //判断当前是收件
	};
	$.get(ctx + $('#left-navbar .leftnav-menu-a[mid=33]').attr('href'), JSON, function(data) {
		$('#right-main').html(data);
	});
})


$('#email-content-delete').click(function (e) {
	e.preventDefault();
	dialog.confirmFun("确定要删除该邮件吗？", deleteInAjax);
	var $this = $(this);
	function deleteInAjax () {
		var JSON = {
			iid : $this.attr('iid')
		};
		var paramIn = {
				service : 'inboxService',
				method : 'deleteInById',
				param : JSON,
				success : function (data) {
					if(data.rtnCode == ajax.rtnCode.SUCCESS) {
						dialog.successNo(data.param.message);
						$('#email-nav').slideDown('slow');
						$('#email-content-detail').slideUp('slow');
						$('#email-nav .inbox-detail[iid='+ $this.attr('iid') +']').remove();
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

$(document).on('click', '#email-page-previous a', function (e) {
	e.preventDefault();
	var activePage = $('.email-page-href.active');
	var page = activePage.find('a').text();
	
	function pagePreviousPage(data) {
		listInboxs(data.param.inboxs);
		var prev = activePage.removeClass('active').prev().addClass('active');
		if(prev.prev().prop('id') == 'email-page-previous'){
			prev.prev().css('display', 'none');
		}
		if($('#email-page-behaviour').css('display') == 'none') {
			$('#email-page-behaviour').css('display', 'inline');
		}
	}
	
	pageInbox(parseInt(page)-2, pagePreviousPage);
})

/**
 * 分页导航向后按钮
 * 
 * @param e
 * @returns
 */

$(document).on('click', '#email-page-behaviour a', function (e) {
	e.preventDefault();
	var activePage = $('.email-page-href.active');
	var page = activePage.find('a').text();
	function pageBehaviourPage(data) {
		listInboxs(data.param.inboxs);
		var next = activePage.removeClass('active').next().addClass('active');
		if(next.next().prop('id') == 'email-page-behaviour'){
			next.next().css('display', 'none');
		}
		if($('#email-page-previous').css('display') == 'none') {
			$('#email-page-previous').css('display', 'inline');
		}
	}
	pageInbox(parseInt(page), pageBehaviourPage);
})


/**
 * 分页普通按钮点击事件
 * @param e
 * @returns
 */
$(document).on('click', '.email-page-href', function (e) {
	e.preventDefault();
	var $this = $(this);
	var page = $(this).find('a').text();
	
	function pageHrefPage(data) {
		listInboxs(data.param.inboxs);
		$('.email-page-href.active').removeClass('active');
		$this.addClass('active');
		if($this.prev().prop('id') == 'email-page-previous'){
			$this.prev().css('display', 'none');
		}else {
			if($('#email-page-previous').css('display') == 'none') {
				$('#email-page-previous').css('display', 'inline');
			}
		}
		if($this.next().prop('id') == 'email-page-behaviour'){
			$this.next().css('display', 'none');
		}else {
			if($('#email-page-behaviour').css('display') == 'none') {
				$('#email-page-behaviour').css('display', 'inline');
			}
		}
		
	}
	pageInbox(parseInt(page) - 1, pageHrefPage);
})

/**
 * 
 * 前端分页函数，根据当前页数进行分页
 * 
 * @param page 当前页数
 * @param succFunc 分页查询成功后执行函数
 * @returns
 */
function pageInbox(page, succFunc) {
	var JSON = {
			"page" : page
		};
		
	var paramIn = {
			service : 'inboxService',
			method : 'getInboxPage',
			param : JSON,
			success : succFunc
		};
	ajax.query(paramIn);
}

/**
 * 列出所有的收件
 * @param notifications
 * @returns
 */
function listInboxs(inboxs) {
	var userBody = $('#inbox-body');
	userBody.find('.inbox-detail').not(':first').remove();
	var $sendPerson, $title, $summary,$email,$content,$hasSee,$sendTime,$h4,$btn,$manybtn = new Array();
	$(inboxs).each(function (index, value) {
		if(value.hasSee == 0) {
			$btn = $('<button type="button" iid="'+ value.id +'" class="inbox-detail list-group-item clearfix new">');
		}else {
			$btn = $('<button type="button" iid="'+ value.id +'" class="inbox-detail list-group-item clearfix">');
		}
		$sendPerson = $('<span class="heading-sendPerson" ></span>').text(value.sendPerson.staff.titleName);
		$title = $('<span class="heading-title" ></span>').text(value.title);
		$summary = $('<span class="heading-summary"></span>').text('-' + value.summary);
		$sendTime = $('<span class="heading-sendTime"></span>').text(value.sendTime.replace(/T/, " "));
		$content = $("<div class='list-group-item-content' style='display:none;' ></div>").html(value.content);
		$email = $('<div class="list-group-item-email" style="display:none;" ></div>').text(value.sendPerson.staff.email);
		$h4 = $("<h4 class='list-group-item-heading clearfix'></h4>");
		$h4.append([$sendPerson, $title, $summary,$sendTime]);
		$btn.append([$h4, $content,$email]);
		$manybtn.push($btn);
	})
	userBody.append($manybtn);
}
