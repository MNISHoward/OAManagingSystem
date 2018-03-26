$(function () {
	init2();
	
	var ue = UE.getEditor('editor', {
		toolbars: [
		    ['source', 'undo', 'redo'],
		    ['bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc']
		]
		,zIndex : 1200
		,autoHeightEnabled:false
		,scaleEnabled:false
	});
	
})


function init2() {
	$('#company-nav .company-intro, #company-nav .company-detail').height(($(window).height() - 80));
}

$('#company-nav .glyphicon-pencil').click(function (e) {
	$('.intro-modal-lg').modal('show');
})



//@ sourceURL=intro.js