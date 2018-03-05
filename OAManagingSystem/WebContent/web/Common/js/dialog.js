var dialog = {
		success : function (message, url) {
			layer.open({
				title : '成功提示',
				content : message,
				yes : function () {
					window.location = url;
				}
			});
		},
		successNo : function (message) {
			layer.open({
				title : '成功提示',
				content : message,
			});
		},
		error : function (message) {
			layer.open({
				title : '失败提示',
				content : message,
				skin : 'demo-class',
				yes : function (index, layero) {
					layer.close(index);
				}
			})
		},
		confirm: function (message, url) {
			layer.open({
				title : '确认提示',
				btn : ['确认', '取消'],
				content : message,
				yes : function () {
					window.location = url;
				},
				btn2 : function (index, layero) {
					layer.close(index);
				}
			});
		},
		confirmFun: function (message, successfunc) {
			layer.open({
				title : '确认提示',
				btn : ['确认', '取消'],
				content : message,
				yes : successfunc,
				btn2 : function (index, layero) {
					layer.close(index);
				}
			});
		}
}