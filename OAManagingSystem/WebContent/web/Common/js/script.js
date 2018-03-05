$(function () {
	init();
})

/**
 * 初始化事件
 * @returns
 */
function init() {
	var requestParam = Util.getRequest();
	$('#top-navbar #navbar-collapse a').each(function (index, value) {
		var $href = $(this).attr('href');
		if($href.indexOf(requestParam['rid']) != -1) {
			$(this).parent().addClass('active');
		}
	})
}


$('#profile').on('click',function (e) {
	e.preventDefault();
	$('#right-main').load(ctx + '/web/jsp/frame/profile.jsp');
})

$('.leftnav-menu-a').click(function (e) {
	e.preventDefault();
	var target = e.target;
	if(window.timeoutId){clearTimeout(window.timeoutId);}
	window.timeoutId=setTimeout(function () {
		var $a = $(target);
		var url = $a.attr('href');
		if(url !== undefined) {
			$('#right-main').load(ctx + url);
		}
		window.timeoutId = null;
	}, 500)
	
});

$('#top-navbar #navbar-collapse a').click(function (e) {
	$href = $(this).attr('href');
	if($href.indexOf(ctx) == -1)
		$(this).attr('href', ctx + $href);
	return true;
})

//@ sourceURL=script.js