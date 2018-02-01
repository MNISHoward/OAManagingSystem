$('#profile').click(function (e) {
	e.preventDefault();
	$('#right-main').load(ctx + '/web/jsp/frame/profile.jsp');
})