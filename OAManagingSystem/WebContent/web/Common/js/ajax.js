function sendAjax(options){
	    //获取参数
	    if(options){
	        //服务名称
	        var service = options['service'];
	        //方法名称
	        var method = options['method'];
	        //参数
	        var param = options['param'];
	        //成功回调方法
	        var success = options['success'];
	        //失败回调方法
	        var error = options['error'];
	        //回调方法
	        var complete = options['complete'];
	        //请求路径
	        var url = options['url'];
	        if(!url){
	            url = ctx+'/ajax/ajax.do';
	        }
	        var timeout = options['timeout'];
	        if(!timeout){
	            timeout = 60*1000;
	        }
	        //完全自定义入参
	        var data = options['data'];
	    }else{
	        throw new Error('ajax入参为空');
	    }

	    //校验参数
	    if(!service){
	        throw new Error('service为空');
	    }
	    if(!method){
	        throw new Error('method为空');
	    }

	    //构造参数
	    var jsonInParam = {
	        'service': service,
	        'method': method,
	        'param': param
	    };

	    if(!data){
	        data = jsonInParam
	    }

	    return $.ajax({
	        url: url,
	        data: JSON.stringify(data),
	        type: 'post',
	        dataType: 'json',
	        headers: {
	            Accept:"application/json",
	            "Content-Type":"application/json"
	        },
	        processData: false,
	        cache: false,
	        timeout: timeout,
	        success: function(data){
	            if(success){
	                success(data);
	            }
	        },
	        error: function(XMLHttpRequest, textStatus, errorThrown){
	            console.log('error');
	            console.log(arguments);
	            if(error){
	                error(XMLHttpRequest, textStatus, errorThrown);
	            }
	        },
	        complete: function(XMLHttpRequest, textStatus){
	            if(complete){
	                complete(XMLHttpRequest, textStatus);
	            }
	        }
	    });
	}
	
var ajax = {
	query : function (options) {
		return sendAjax(options);
	},
	rtnCode: {
        SUCCESS: '1',
        ERROR: '-1',//失败,多为意料外的异常
        WARNNING: '2',//提示，多为校验异常,意料中
        NOLOGIN: '3'//未登录
    },
}
