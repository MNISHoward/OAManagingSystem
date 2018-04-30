$(function () {
    $( "#staff" ).autocomplete({
      source: function( request, response ) {
    	  var staff;
    	  var JSON = {
				param : $('#staff').val(),
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
    			 $('#saveAssginBtn').attr('staffId', value.id);
    			 $('#staffForm').setForm(value);
    		 }
    	 })
     } ,
     close: function(event, ui) {
    	 //菜单关闭，防止污染全局
    	 delete(window.staffs);
     }
    });
    
    
    $( "#asset" ).autocomplete({
        source: function( request, response ) {
      	  var staff;
      	  var JSON = {
  				param : $('#asset').val(),
  			}
      	  var paramIn = {
  			service : 'assetService',
  			method : 'findAssetwithIdAndModel',
  			param : JSON,
  			success : function (data) {
  				window.assets= data.param.assets;
  				response($(data.param.assets).map(function (index, value) {
  					var obj = {};
  					obj.label = '资产号:' + value.id + ', 资产名:' + value.titleName + ',型号：' + value.model;
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
      	 $(window.assets).map(function (index, value) {
      		 if(value.id == ui.item.id){
      			 value.assetType = value.assetType.titleName;
      			 $('#saveAssginBtn').attr('assetId', value.id);
      			 $('#assetForm').setForm(value);
      		 }
      	 })
       } ,
       close: function(event, ui) {
      	 //菜单关闭，防止污染全局
      	 delete(window.assets);
       }
      });
})

$('#saveAssginBtn').click(function (e) {
	e.preventDefault();
	var staffId = $(this).attr('staffId');
	var assetId = $(this).attr('assetId');
	if(assetId == undefined ) {
		dialog.error("请选择资产");
		return false;
	}
	if(staffId == undefined ) {
		dialog.error("请选择员工");
		return false;
	}
	dialog.confirmFun("确认保存资产的信息吗？", sendAjaxWithDistri);
	function sendAjaxWithDistri() {
		var JSON = {
			'staffId' : staffId,
			'assetId' : assetId
		};
		var paramIn = {
				service : 'distributionRecordService',
				method : 'saveDistributionRecord',
				param : JSON,
				success : function (data){
					if(data.rtnCode == ajax.rtnCode.SUCCESS) {
						dialog.success(data.param.message,location.href);
					}else {
						dialog.error(data.rtnMessage);
					}
				}
			};
		ajax.query(paramIn);
	}
})