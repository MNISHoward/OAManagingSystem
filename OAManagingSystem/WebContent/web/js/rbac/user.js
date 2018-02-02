$(function () {
	init();
})

function init() {
	$('#dept-nav li .list-group').css({
		display : "none",
		marginLeft : function () {
			var $marginLeft = $(this).parents('ul').css('marginLeft');
			console.log($marginLeft);
			var marginValue = $marginLeft.substring(0, $marginLeft.indexOf('px'));
			return (parseInt(marginValue) + 30) + 'px';
		}
	});
	
}

$('#dept-nav a.has-child').click(function (e) {
	e.preventDefault();
	//选择器
	var target = e.target;
	var $li = $(target).parents('li');
	var $a = $li.children('a');
	var $span = $li.find('.glyphicon');
	
	//选择其他部门时，将其他高亮的取消掉
	$('#dept-nav .active').removeClass('active');
	//$li.siblings().find('.glyphicon').removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-right');
	
	//选择时，设置箭头方向
	if($span.hasClass('glyphicon-chevron-right')) {
		$a.addClass('active');
		$span.removeClass('glyphicon-chevron-right').addClass('glyphicon-chevron-down');
	}else {
		$a.removeClass('active')
		$span.removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-right');
	}
	var $ul = $(target).parents('li').children('ul');
	$ul.slideToggle('fast');
});

$('#dept-nav').find('a').not('.has-child').click(function (e) {
	e.preventDefault();
	
	var target = e.target;
	$('#dept-nav .active').removeClass('active');
	$(target).addClass('active');
})