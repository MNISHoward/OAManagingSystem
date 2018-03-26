$(function () {
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
      			 $('#deleteAssginBtn').attr('assetId', value.id);
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

$('#deleteAssginBtn').click(function (e) {
	e.preventDefault();
	var assetId = $(this).attr('assetId');
	if(assetId == undefined ) {
		dialog.error("请选择资产");
		return false;
	}
	dialog.confirmFun("确认删除资产的信息吗？", sendAjaxWithDistri);
	function sendAjaxWithDistri() {
		var JSON = {
			'assetId' : assetId
		};
		var paramIn = {
				service : 'assetService',
				method : 'deleteAsset',
				param : JSON,
				success : function (data){
					if(data.rtnCode == ajax.rtnCode.SUCCESS) {
						if(data.param.flag) {
							dialog.success(data.param.message,location.href);
						}else {
							dialog.successNo(data.param.message);
						}
					}else {
						dialog.error(data.rtnMessage);
					}
				}
			};
		ajax.query(paramIn);
	}
})