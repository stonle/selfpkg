var timestamp=new Date().getTime();
// 获取URL参数值
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	/*if (r != null)
		return decodeURI(r[2]);*/
	return r == null ? "" : decodeURI(r[2]);
}


function getItemTypeName(obj,value){
	var name = "";
	for(var o = 0 ;o< obj.length;o++){
		if(obj[o].value == value){
			name = obj[o].name;
			break;
		}
		
	}
	return name;
}

// Modal按钮事件操作 :主要用于异步删除或修改项目
function doAction(action, url, reqParam) {
	var result = "";
	switch (action) {
	case 1:// 确认操作
		$.ajaxSettings.async = false;// 是否异步
		$.post(url, reqParam, function(data) {
			result =  data.msg;
		}, "json");
		break;
	case -1:// 取消操作
		break;
	}
	return result;
	
}

/**
 * 获取区域信息
 * */
function getAreaInfo( areaID, areaType){
	var areaInfo = "";
	//加载角色信息
	$.ajaxSetup({ cache: false });
	$.ajaxSettings.async = false;//是否异步
	$.get("getAreaInfo.do",{"areaID":areaID,'areaType':areaType},function(data){
		if(data.msg == "1"){
			areaInfo =  data.list;
		}
	},"json");
	return areaInfo;
}

/**
 * 弹出新页面
 */
function popPage(pageUrl, pageWith, pageheight, pageTitle) {
	layer.open({
		title : pageTitle,
		type : 2,
		offset: '40px',
		area : [ pageWith, pageheight],
		fix : false, // 不固定
		maxmin : true,
		content : pageUrl,
		iframe :{
			scrolling : 'no'
		}
	});
}
/**
 * 查询用户是否拥有页面按钮所对应的权限
 * 
 */
function checkButton(url){
	var result = "";
	$.ajaxSettings.async = false;// 是否异步
	$.get(url, function(data) {
		result =  data;
	}, "json");
	return result;
}
//格式化空值null
function formatNull(value){
	return value != null ? value : '';
} 
//子父级级联操作,父选中，则所有的子选中
function checkedLevelOne(element){
	var checked = $(element).prop('checked');
	var id = $(element).attr('id');
	$("tbody tr td input").each(function(i){ 
		if($(this).hasClass(id)){
			$(this).prop('checked', checked);
			return;
		}
    });
}

function inputFloatType(obj) {
	// 先把非数字的都替换掉，除了数字和.
	obj.value = obj.value.replace(/[^\d.]/g, "");
	// 必须保证第一个为数字而不是.
	obj.value = obj.value.replace(/^\./g, "");
	// 保证只有出现一个.而没有多个.
	obj.value = obj.value.replace(/\.{2,}/g, ".");
	// 保证.只出现一次，而不能出现两次以上
	obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$",
			".");
}

function inputIntType(obj) {
	obj.value = obj.value.replace(/\D/g, '')
}

//子父级菜单级联操作，当子菜单未选中，则父菜单未选中 
function checkedByChild(element){
	if (!$(element).prop('checked')) {
		var id = $(element).attr("class");
		$("tbody tr td input[id='"+id+"']").each(function(i){
	    	$(this).prop('checked', false);
	    });
	}
}
Array.prototype.indexOf = function(el){//查找元素在数组中的索引
	 for (var i=0,n=this.length; i<n; i++){
		 if (this[i] === el){
		 	return i;
		 }
	 }
	 return -1;
 }
