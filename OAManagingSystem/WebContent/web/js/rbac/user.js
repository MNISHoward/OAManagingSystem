$(function () {
	init();
})

function init() {
	$('#dept-nav li .list-group').css({
		display : "none",
		marginLeft : function () {
			var $marginLeft = $(this).parents('ul').css('marginLeft');
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


$('#dept-nav').find('a').click(function (e){
	e.preventDefault();
	
	var target = e.target;
	var pid = $(target).attr("pid");
	
	var JSON = {
		"deptId" : pid,
	}
	
	var paramIn = {
			service : 'staffService',
			method : 'queryStaffByDeptAndPageable',
			param : JSON,
			success : function (data){
				if(data.rtnCode == ajax.rtnCode.SUCCESS) {
					listStaff(data.param.list);
				}else {
					dialog.error(data.rtnMessage);
				}
			}
		};
	ajax.query(paramIn);
}) 


function listStaff(staffs) {
	var userBody = $('#user-nav').find("tbody");
	var $titleName, $job, $sex;
	$(staffs).each(function (index, value2) {
		$(value2).each(function (index, value) {
			$titleName = $("<td></td>").text(value.titleName);
			$job = $("<td></td>").text(value.job);
				if(value.sex == 1)
					$sex = $("<td></td>").text("男");
				else 
					$sex = $("<td></td>").text("女");
		})
	})
	userBody.append($titleName);
	userBody.append($sex);
	userBody.append($job)
}