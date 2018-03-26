$(function () {
	$('#page-previous').css('display', 'none');
	if($('.page-href') != undefined || $('.page-href') != null) {
		$('#daily-nav .nav-pagination').css('display', "block");
		$('.page-href:contains(1)').addClass('active');
	}
	
	$('#daily-content-detail').hide();
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
			method : 'getDailyPage',
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
	var $titleName, $title, $updateTime,$content,$hasSee, $h4,$manya = new Array();
	$(dailys).each(function (index, value) {
		$title = $("<span class='heading-title' ></span>").html(value.title + "&nbsp;");
		$hasSee = value.hasSee;
		$titleName = $("<span class='heading-author' ></span>").text("作者：" + value.author.staff.titleName);
		$updateTime = $("<span class='heading-updateTime'></span>").append($('<small></small>').text("修改时间:"+ value.updateTime.replace(/T/, " ") ));
		$p = $("<p class='list-group-item-text'></p>").html(value.summary);
		$div = $("<div class='list-group-item-content' style='display:none;' ></div>").html(value.content);
		if($hasSee == 0) {
			$h4 = $("<h4 class='list-group-item-heading hasSee'></h4>");
		}else {
			$h4 = $("<h4 class='list-group-item-heading'></h4>");
		}
		$h4.append([$title, $titleName, $updateTime]);
		$a = $('<a href="#" did='+value.id+' class="daily-detail list-group-item"></a>').append([$h4, $p, $div]);
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
	if($(this).find('.list-group-item-heading').hasClass('hasSee')) {
		$(this).find('.list-group-item-heading').removeClass('hasSee');
		var JSON = {
				"did" : $(this).attr('did'),
			};
		var paramIn = {
				service : 'dailyService',
				method : 'updateDailyById',
				param : JSON,
				success : function() {}
			};
		ajax.query(paramIn);
	}
	$('#daily-content-title').html($(this).find('.heading-title').text());
	$('#daily-content-author small').html($(this).find('.heading-author').text());
	$('#daily-content').html($(this).find('.list-group-item-content').html());
	$('#daily-nav').slideUp('slow');
	$('#daily-content-detail').slideDown('slow');
})

$('#daily-content-back').click(function (e) {
	e.preventDefault();
	$('#daily-nav').slideDown('slow');
	$('#daily-content-detail').slideUp('slow');
})