$("#pageIndex").html("<ul class=\"page\"><li id=\"first\">首页</li><li id=\"prve\">上一页</li><li class=\"disabled\"><font id=\"currentPage\"></font>/<font id=\"totalPage\"></font></li><li id=\"next\"></li><li id=\"last\"></li></ul>");
var currPage = 1;

//每页显示项数绑定改变事件
$("#pageSize").bind("change",function(){
	currentPage = 1;
	$("#doQuery").click();
})

/********************通用*********************/
//分页按钮+事件控制
$.extend({pageIndex:function(nowPage,pageCount,functionName){
	var func=eval(functionName);
	if(nowPage == 1){
		$("#first").addClass("disabled");
		$("#first").html("首页");
		$("#prve").addClass("disabled");
		$("#prve").html("上一页");
	}else{
		$("#first").removeClass("disabled");
		$("#first").html("<a href=\"javascript:;\" title=\"首页\">首页</a>");
		$("#prve").removeClass("disabled");
		$("#prve").html("<a href=\"javascript:;\" title=\"上一页\">上一页</a>");
		$("#first a").bind("click",(function(){
			currPage = 1;
			new func();
		}));
		$("#prve a").bind("click",(function(){
			currPage = currPage - 1;
			new func();
		}));
		 
	}
	if(nowPage == pageCount){
		$("#next").addClass("disabled");
		$("#next").html("下一页");
		$("#last").addClass("disabled");
		$("#last").html("末页");
	}else{
		$("#next").removeClass("disabled");
		$("#next").html("<a href=\"javascript:;\" title=\"下一页\">下一页</a>");
		$("#next a").bind("click",(function(){
			currPage = currPage + 1;
			new func();
		}));
		if(pageCount < 100){
			$("#last").removeClass("disabled");
			$("#last").html("<a href=\"javascript:;\" title=\"末页\">末页</a>");
			
			$("#last a").bind("click",(function(){
				currPage = pageCount;
				new func();
			}));
		}else{
			$("#last").addClass("hide");
		}
		
	}
  }
});