$(function () {
})

$('#assetTypeId').change(function (e) {
	if($(this).val() == 9) {
		$('#phonenumber').prop('disabled', false);
	}else {
		$('#phonenumber').val("");
		$('#phonenumber').prop('disabled', true);
	}
})

$('#saveAssetBtn').click(function (e) {
	e.preventDefault();
	dialog.confirmFun("确认保存资产的信息吗？", sendAjaxWithAssets);
	function sendAjaxWithAssets() {
		var JSON = Util.formDataToJson(assetForm);
		var paramIn = {
				service : 'assetService',
				method : 'saveAsset',
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