$(function () {
	var ue = UE.getEditor('editor', {
		toolbars: [
		    ['fullscreen', 'source', 'undo', 'redo'],
		    ['bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc']
		]
	});
	
	ue.addListener('ready', (function () {
		ue.setContent(Util.unescapeHTML(content));
	}))
	
})

$('#updateNotBtn').click(function (e) {
	ue = UE.getEditor('editor');
	if(ue.hasContents()){ //此处以非空为例
		ue.sync();       //同步内容
		notiForm.submit();   //提交Form
		$('#updateNotBtn').prop('disabled', true);
	}else {
		dialog.error("内容不能为空");
	}
})