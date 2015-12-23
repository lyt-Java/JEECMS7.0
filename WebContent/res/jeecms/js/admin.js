Cms={};
Cms.lmenu = function(id) {
	var m = $('#' + id);
	m.addClass('lmenu');
	m.children().each(function() {
		$(this).children('a').bind('click', function() {
			$(this).parent().addClass("lmenu-focus");
			$(this).blur();
			var li = m.focusElement;
			if (li && li!=this) {
				$(li).parent().removeClass("lmenu-focus");
			}
			m.focusElement=this;
		});
	});
}
Cms.leftLi=function(){
	$("li").each(function(i){
		$(this).removeClass();
		if(i==0){
			$(this).addClass("leftCurr");
		}else{
			$(this).addClass("leftNol");
		}
		$(this).click( function () {
			$("li").each(function(){
				$(this).removeClass();
				$(this).addClass("leftNol");
			});
			$(this).removeClass();
			$(this).addClass("leftCurr");
		});
	});
}

Cms.selectWeiXinType=function selectWeiXinType(){
	var t=$("#sendType").val();
	if(t==3){
		$("#selectImg").show();
		$("#imageHelpSpan").show();
		var img=$("#selectImg").val();
		if(img==0){
			$("#uploadImgPath4").parent().parent().show();
		}
	}else{
		$("#selectImg").hide();
		$("#imageHelpSpan").hide();
		$("#uploadImgPath4").parent().parent().hide();
	}
}
Cms.selectWeiXinImg=function selectWeiXinImg(){
	if($("#selectImg").val()==0){
		var t=$("#sendType").val();
		if(t==3){
			$("#uploadImgPath4").parent().parent().show();
		}else{
			$("#uploadImgPath4").parent().parent().hide();
		}
	}else{
		$("#uploadImgPath4").parent().parent().hide();
	}
}