var timestamp=new Date().getTime();
// 获取URL参数值
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	/*if (r != null)
		return decodeURI(r[2]);*/
	return r == null ? "" : decodeURI(r[2]);
}

function getQueryStringUrl(url,name){
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = url.match(reg);
	return r == null ? "" : decodeURI(r[2]);
}

