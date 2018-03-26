$(function () {
	init();
	$.ajaxSetup ({   //取消IE对页面的缓存
        cache: false 
    });
})

/**
 * 初始化事件
 * @returns
 */
function init() {
	var requestParam = Util.getRequest();
	$('#top-navbar #navbar-collapse a').each(function (index, value) {
		var $rid = $(this).attr('rid');
		if($rid == requestParam['rid']) {
			$(this).parent().addClass('active');
		}
	})
	
	$('#announcement .page-body').html(Util.unescapeHTML(content));
	
	$('#notification-content').hide();
}

/**
 * 用户设置退出登录界面
 * @param e
 * @returns
 */
$('#profile').on('click',function (e) {
	e.preventDefault();
	$('#right-main').load(ctx + '/web/jsp/frame/profile.jsp');
})

/**
 * 左侧菜单栏点击
 * @param e
 * @returns
 */
$('.leftnav-menu-a').click(function (e) {
	e.preventDefault();
	var $this = $(this);
	$this.addClass('disabled');
	e.preventDefault();
	var url = $this.attr('href');
	if(url !== undefined && (window.asure == undefined || window.asure != false)) {
		$('#right-main').load(ctx + url, null, function(data) {
			if(data.indexOf('rtnCode') != -1) {
				data = JSON.parse(data);
			}
			if(data.rtnCode == ajax.rtnCode.NOLOGIN) {
				dialog.success(data.rtnMessage, window.location.href);
			}else {
				$this.removeClass('disabled');
				window.asure = true;
			}
		});
	}
	window.asure = false;
	
});

/**
 * 公司邮箱
 * @param e
 * @returns
 */
$('#top-navbar #navbar-collapse a[rid=2]').click(function (e) {
	e.preventDefault();
	$href = $(this).attr('href');
	if($href.indexOf(ctx) == -1)
		$(this).attr('href', ctx + $href);
	window.open($(this).attr('href'),"emailWin","titlebar=no,toolbar=no, location=0, directories=no, status=no, menubar=no, scrollbars=no, resizable=0, copyhistory=no, width=1000, height=500, top=100, left=100")
})


/**
 * 顶部菜单
 * @param e
 * @returns
 */
$('#top-navbar #navbar-collapse a').not('[rid=2]').click(function (e) {
	$href = $(this).attr('href');
	if($href.indexOf(ctx) == -1)
		$(this).attr('href', ctx + $href);
	return true;
})

/**
 * 通知按钮
 * @param e
 * @returns
 */
$('#notification-btn').click(function (e) {
	e.preventDefault();
	$('#notificationModal').modal('show');
	
	function notificationPage(data) {
		if(data.rtnCode == ajax.rtnCode.SUCCESS) {
			var totalPages = data.param.totalPages;
			window.notifications = data.param.notifications;
			if(totalPages > 0) {
				//只有总页数大于1时才显示分页
				if(totalPages == 1) {
					listNotification(data.param.notifications);
				}else {
					listNotification(data.param.notifications);
					$('#notification .page-footer').css('display', 'block');
					$('#notification').find('input[name=page]').attr("maxpage", data.param.totalPages);
				}
			}
		}else {
			dialog.error(data.rtnMessage);
		}
	}
	
	pageNotification(0, notificationPage);
})
/**
 * 通知向前
 * @param e
 * @returns
 */
$('#top-page-previous a').click(function (e) {
	e.preventDefault();
	var page = $('#notification').find('input[name=page]').attr("nowpage");
	function notificationPreviousPage(data) {
		if(data.rtnCode == ajax.rtnCode.SUCCESS) {
			window.notifications = data.param.notifications;
			listNotification(data.param.notifications);
			$('#notification').find('input[name=page]').attr("nowpage", parseInt(page) - 1);
			if(parseInt(page) - 1 == 0) {
				$('#top-page-previous').css('display', 'none');
			}
			if($('#top-page-behaviour').css('display') == 'none') {
				$('#top-page-behaviour').css('display', 'inline');
			}
		}else {
			dialog.error(data.rtnMessage);
		}
	}
	
	pageNotification(parseInt(page) - 1, notificationPreviousPage);
})

/**
 * 通知向后
 * @param e
 * @returns
 */
$('#top-page-behaviour a').click(function (e) {
	e.preventDefault();
	var page = $('#notification').find('input[name=page]').attr("nowpage");
	function notificationBehaviourPage(data) {
		if(data.rtnCode == ajax.rtnCode.SUCCESS) {
			window.notifications = data.param.notifications;
			listNotification(data.param.notifications);
			$('#notification').find('input[name=page]').attr("nowpage", parseInt(page) + 1);
			var maxPage = $('#notification').find('input[name=page]').attr("maxpage");
			if(parseInt(page) + 2 == parseInt(maxPage)) {
				$('#top-page-behaviour').css('display', 'none');
			}
			if($('#top-page-previous').css('display') == 'none') {
				$('#top-page-previous').css('display', 'inline');
			}
		}else {
			dialog.error(data.rtnMessage);
		}
	}
	
	pageNotification(parseInt(page) + 1, notificationBehaviourPage);
})

/**
 * 
 * 前端分页函数，根据当前页数进行分页
 * 
 * @param page 当前页数
 * @param succFunc 分页查询成功后执行函数
 * @returns
 */
function pageNotification(page, succFunc) {
	var JSON = {
			"page" : page
		}
		
	var paramIn = {
			service : 'notificationService',
			method : 'getNotificationWithUserId',
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
function listNotification(notifications) {
	var userBody = $('#notification').find(".list-group");
	userBody.empty();
	var $titleName, $hasSee, $manyLi = new Array();
	$(notifications).each(function (index, value) {
		$titleName = $("<span class='noti-title'></span>").text(value.title);
		$hasSee = value.hasSee;
		$btn = $('<button class="btn btn-default detail" nid=' + value.id + ' >详情</button>');
		if($hasSee == 0) {
			$li = $("<li class='list-group-item hasSee' ></li>");
			$li.append([$titleName, $('<span class="noti-new">new</span>'), $btn]);
		}else {
			$li = $("<li class='list-group-item' ></li>");
			$li.append([$titleName, $btn]);
		}
		$manyLi.push($li);
	})
	userBody.append($manyLi);
}
/**
 * 详情的按钮
 * @param e
 * @returns
 */
$(document).on('click', '#notification .detail', function (e) {
	e.preventDefault();
	var nid = $(this).attr("nid");
	if($(this).parent().hasClass('hasSee')){
		updateHasSee(nid);
		$(this).parent().removeClass('hasSee');
		$(this).parent().find('.noti-new').empty().remove();
		var num = $('#notification-btn').find('.notifying-num');
		if(parseInt(num.text()) - 1 == 0) {
			num.hide();
		}else {
			num.text(num.text() - 1);
		}
	}
	getContent(parseInt(nid));
	$('#deleteNoti').attr('nid', nid);
	$('#notification').slideUp('slow');
	$('#notification-content').slideDown('slow');
	function getContent(nid) {
		var content;
		$(window.notifications).each(function (index, value) {
			if(value.id == nid) {
				content = value.content;
			}
		})
		$('#notification-content').find('.page-body').html(content);
	}
	
	function updateHasSee(nid) {
		var JSON = {
				"nid" : nid
			}
			
		var paramIn = {
				service : 'notificationService',
				method : 'updateNotificationById',
				param : JSON,
				success : function() {}
			};
		ajax.query(paramIn);
	}
})
/**
 * 返回按钮
 * @param e
 * @returns
 */
$("#notification-content").find('.glyphicon-chevron-left').click(function (e) {
	$('#notification').slideDown('slow')
	$('#notification-content').slideUp('slow');
})


$('#deleteNoti').click(function (e) {
	e.preventDefault();
	var nid = $(this).attr('nid');
	var JSON = {
			"nid" : nid
		}
	var paramIn = {
			service : 'notificationService',
			method : 'deleteNotificationById',
			param : JSON,
			success : function() {
				$('#notification').slideDown('slow')
				$('#notification-content').slideUp('slow');
				$('#notification').find('button[nid='+nid+']').parent().empty().remove();
			}
		};
	ajax.query(paramIn);
})
//@ sourceURL=script.js