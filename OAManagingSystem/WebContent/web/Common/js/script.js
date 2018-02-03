$('#profile').on('click',function (e) {
	e.preventDefault();
	$('#right-main').load(ctx + '/web/jsp/frame/profile.jsp');
})

$('.leftnav-menu-a').click(function (e) {
	e.preventDefault();
	var target = e.target;
	var $a = $(target);
	var url = $a.attr('href');
	if(url !== undefined) {
		$('#right-main').load(ctx + url);
	}
	
});
