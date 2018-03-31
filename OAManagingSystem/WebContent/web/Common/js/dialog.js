var dialog = {
		success : function (message, url) {
			layer.open({
				title : '成功提示',
				content : message,
				yes : function () {
					window.location = url;
				},
				success : function(layero, index){
					this.enterEsc = function(event){
			        if(event.keyCode === 13){
			          window.location = url;
			          layer.close(index);
			          return false; //阻止系统默认回车事件
			        }
			      };
			      $(document).on('keydown', this.enterEsc);	//监听键盘事件，关闭层
			    }
			    ,end: function(){
			      $(document).off('keydown', this.enterEsc);	//解除键盘关闭事件
			    }
			});
		},
		successNo : function (message) {
			layer.open({
				title : '成功提示',
				content : message,
				success : function(layero, index){
					this.enterEsc = function(event){
			        if(event.keyCode === 13){
			          layer.close(index);
			          return false; //阻止系统默认回车事件
			        }
			      };
			      $(document).on('keydown', this.enterEsc);	//监听键盘事件，关闭层
			    }
			    ,end: function(){
			      $(document).off('keydown', this.enterEsc);	//解除键盘关闭事件
			    }
			});
		},
		error : function (message) {
			layer.open({
				title : '失败提示',
				content : message,
				skin : 'demo-class',
				yes : function (index, layero) {
					layer.close(index);
				},
				success : function(layero, index){
					this.enterEsc = function(event){
			        if(event.keyCode === 13){
			          layer.close(index);
			          return false; //阻止系统默认回车事件
			        }
			      };
			      $(document).on('keydown', this.enterEsc);	//监听键盘事件，关闭层
			    }
			    ,end: function(){
			      $(document).off('keydown', this.enterEsc);	//解除键盘关闭事件
			    }
			})
		},
		confirm: function (message, url) {
			layer.open({
				title : '确认提示',
				btn : ['确认', '取消'],
				content : message,
				yes : function (index, layero) {
					window.location = url;
					layer.close(index);
				},
				btn2 : function (index, layero) {
					layer.close(index);
				},
				success : function(layero, index){
					this.enterEsc = function(event){
			        if(event.keyCode === 13){
			          return false; //阻止系统默认回车事件
			        }
			      };
			      $(document).on('keydown', this.enterEsc);	//监听键盘事件，关闭层
			    }
			    ,end: function(){
			      $(document).off('keydown', this.enterEsc);	//解除键盘关闭事件
			    }
			});
		},
		confirmFun: function (message, successfunc) {
			layer.open({
				title : '确认提示',
				btn : ['确认', '取消'],
				content : message,
				yes : function (index, layero) {
					successfunc();
					layer.close(index);
				},
				btn2 : function (index, layero) {
					layer.close(index);
				},
				success : function(layero, index){
					this.enterEsc = function(event){
			        if(event.keyCode === 13){
			          return false; //阻止系统默认回车事件
			        }
			      };
			      $(document).on('keydown', this.enterEsc);	//监听键盘事件，关闭层
			    }
			    ,end: function(){
			      $(document).off('keydown', this.enterEsc);	//解除键盘关闭事件
			    }
			});
		}
}