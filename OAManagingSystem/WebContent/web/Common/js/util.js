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
    	},
    	/**
    	 * @function escapeHTML 转义html脚本 < > & " '
    	 * @param a -
    	 *            字符串
    	 */
    	escapeHTML: function(a){
    	    a = "" + a;
    	    return a.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/"/g, "&quot;").replace(/'/g, "&apos;");;
    	},
    	/**
    	 * @function unescapeHTML 还原html脚本 < > & " '
    	 * @param a -
    	 *            字符串
    	 */
    	unescapeHTML: function(a){
    	    a = "" + a;
    	    return a.replace(/&lt;/g, "<").replace(/&gt;/g, ">").replace(/&amp;/g, "&").replace(/&quot;/g, '"').replace(/&apos;/g, "'");
    	},
    	validator : function (form) {
    		var bootstrapValidator = $(form).data('bootstrapValidator');
            bootstrapValidator.validate();
            return bootstrapValidator.isValid();
    	}
}

Date.prototype.format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) 
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
    
}

$.fn.extend({
    //表单加载json对象数据
    setForm: function (jsonValue) {
        var obj = this;
        $.each(jsonValue, function (name, ival) {
            var $oinput = obj.find("input[name=" + name + "]");
            if ($oinput.attr("type") == "checkbox") {
                if (ival !== null) {
                    var checkboxObj = $("[name=" + name + "]");
                    var checkArray = ival.split(";");
                    for (var i = 0; i < checkboxObj.length; i++) {
                        for (var j = 0; j < checkArray.length; j++) {
                            if (checkboxObj[i].value == checkArray[j]) {
                                checkboxObj[i].click();
                            }
                        }
                    }
                }
            }
            else if ($oinput.attr("type") == "radio") {
                $oinput.each(function () {
                    var radioObj = $("[name=" + name + "]");
                    for (var i = 0; i < radioObj.length; i++) {
                        if (radioObj[i].value == ival) {
                            radioObj[i].click();
                        }
                    }
                });
            }
            else if ($oinput.attr("type") == "textarea") {
                obj.find("[name=" + name + "]").html(ival);
            }
            else {
                obj.find("[name=" + name + "]").val(ival);
            }
        })

    }
});
