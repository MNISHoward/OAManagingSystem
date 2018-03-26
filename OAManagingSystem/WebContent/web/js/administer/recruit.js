$(function () {
	$('.form_datetime').datetimepicker({
        language:  'zh-CN',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		forceParse: 0,
        showMeridian: 1
    });
})

$('#saveStaffBtn').click(function (e) {
	e.preventDefault();
	dialog.confirmFun("确认保存员工的信息吗？", sendAjaxWithStaffs);
	function sendAjaxWithStaffs() {
		var JSON = Util.formDataToJson(staffForm);
		var paramIn = {
				service : 'staffService',
				method : 'insertNewStaff',
				param : JSON,
				success : function (data){
					if(data.rtnCode == ajax.rtnCode.SUCCESS) {
						dialog.successNo(data.param.message);
					}else {
						dialog.error(data.rtnMessage);
					}
				}
			};
		ajax.query(paramIn);
	}
})