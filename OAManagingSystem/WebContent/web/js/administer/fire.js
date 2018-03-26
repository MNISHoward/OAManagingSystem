$(function () {
    $( "#staff-search" ).autocomplete({
      source: function( request, response ) {
    	  var staff;
    	  var JSON = {
				param : $('#staff-search').val(),
			}
    	  var paramIn = {
			service : 'staffService',
			method : 'findStaffwithIdAndName',
			param : JSON,
			success : function (data) {
				window.staffs= data.param.staffs;
				response($(data.param.staffs).map(function (index, value) {
					var obj = {};
					obj.label = '工号:' + value.id + ', 名字:' + value.titleName;
					obj.value = value.titleName;
					obj.id = value.id;
					return obj;
				}))
			}
    	  };
		  ajax.query(paramIn);
     },
     select: function( event, ui ) {
    	 //选中后更换菜单
    	 $(window.staffs).map(function (index, value) {
    		 if(value.id == ui.item.id){
    			 value.departmentId = value.department.id;
    			 $('#staffForm').setForm(value);
    		 }
    	 })
     } ,
     close: function(event, ui) {
    	 //菜单关闭，防止污染全局
    	 delete(window.staffs);
     }
    });
})

$('#fireStaffBtn').click(function (e) {
	var data = $(staffForm).serializeArray();
	var flag = false;
	var titleName, id, job;
	$(data).each(function (index, value) {
		if(value.value == undefined || value.value == "") {
			flag = true;
		}
		if(value.name == "titleName"){
			titleName = value.value;
		}
		if( value.name=="id") {
			id = value.value;
		}
		if(value.name=="job") {
			job = value.value;
		}
			
	})
	if(flag) {
		dialog.error("请先查询员工");
		return false;
	}
	
	dialog.confirmFun("确认解雇员工吗？<br /> 该员工的工号:"+id+"<br />名字："+titleName+"<br />岗位:"+job+"<br />请三思!!!数据可能无法恢复！！！", fireStaff);
	function fireStaff() {
		var JSON = {
			'id' : $('#id').val()
		};
		var paramIn = {
				service : 'staffService',
				method : 'deleteStaffById',
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

//@ sourceURL=fire.js