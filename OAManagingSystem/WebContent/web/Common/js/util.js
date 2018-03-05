var Util = {
		//form表单的所有数值转换为json字符串
		formDataToJson : function(id) {
            var o = {};
            var a = $(id).serializeArray();
            $.each(a, function() {
                if (o[this.name]) {
                    if (!o[this.name].push) {
                        o[this.name] = [ o[this.name] ];
                    }
                    o[this.name].push(this.value || '');
                } else {
                    o[this.name] = this.value || '';
                }
            });
            return o;
        },
        //获取url传参
        getRequest : function () {   
    	   var url = location.search; //获取url中"?"符后的字串   
    	   var theRequest = new Object();   
    	   if (url.indexOf("?") != -1) {   
    	      var str = url.substr(1);   
    	      strs = str.split("&");   
    	      for(var i = 0; i < strs.length; i ++) {   
    	         theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);   
    	      }   
    	   }   
    	   return theRequest;   
    	}   
}