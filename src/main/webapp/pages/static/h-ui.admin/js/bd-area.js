var _obj_province = $("#province");
var _obj_city = $("#city");
var _obj_district = $("#district");
//var _obj_dof = $("#deliveryOffice");
var _obj_address = $("#address");
// 写入Cookie
/*
 * $.cookie('province', $("#province").html(), { expires : 1, path : '/' });
 */

/**
 * 自动加载省份信息
 */
$.ajax({
	type : "get",
	timeout : 5000,
	async : true,
	cache : true,
	global : false,
	url : "listAllProvinceToJson.do",
	dataType : "json",
	success : function(data) {
		if (data.msg != "1") {
			layer.msg('数据请求被拒绝！');
			return;
		}
		var obj = data.list;
		_obj_province.html("<option value=\"\">省份</option>");
		for ( var i = 0; i < obj.length; i++) {
			_obj_province.append("<option value=\"" + obj[i].provinceID + "\">"
					+ obj[i].provinceName + "</optinon>");
		}
	},
	error : function() {
		layer.msg('数据加载超时，请刷新重试！');
	}
});
//加载城市数据
function loadCityData(){
	if (!_obj_district.parent().hasClass('hide')) {
			
		_obj_district.parent().addClass('hide');
	}
	if (!_obj_address.hasClass('hide')) {
		_obj_address.addClass('hide');
	}
	_obj_city.val("");
	_obj_district.val("");
	_obj_address.val("");
	if (_obj_province.val() == "") {
		if (!_obj_city.parent().hasClass('hide')) {
			_obj_city.parent().addClass('hide');
		}
		return;
	}
	_obj_city.parent().removeClass('hide');
	// 请求城市数据
	$.ajax({
		type : "get",
		timeout : 5000,
		async : false,
		cache : true,
		global : false,
		url : "listCityByProvinceToJson.do",
		data : {
			'provinceID' : _obj_province.val()
		},
		dataType : "json",
		success : function(data) {
			if (data.msg != "1") {
				layer.msg('数据请求被拒绝！');
				return;
			}
			var obj = data.list;
			_obj_city.html("<option value=\"\">城市</option>");
			for ( var i = 0; i < obj.length; i++) {
				_obj_city.append("<option value=\"" + obj[i].cityID + "\">"
						+ obj[i].cityName + "</optinon>");
			} 
		},
		error : function() {
			layer.msg('数据加载超时，请刷新重试！');
		}
	});
}
/**
 * 选择省份触发事件
 */
_obj_province.bind("change", function() {
	loadCityData(_obj_province);
})




/**
 * 加载区域数据
 */
function loadDistrictData(){
	if (!_obj_address.hasClass('hide')) {
		_obj_address.addClass('hide');
	}
	_obj_district.val("");
	_obj_address.val("");
	if (_obj_city.val() == "") {
		if (!_obj_district.parent().hasClass('hide')) {
			_obj_district.parent().addClass('hide');
		}
		return;
	}
	_obj_district.parent().removeClass('hide');
	// 请求区县数据
	$.ajax({
		type : "get",
		timeout : 5000,
		async : false,
		cache : true,
		global : false,
		url : "listDistrictByCityToJson.do",
		data : {
			'cityID' : _obj_city.val()
		},
		dataType : "json",
		success : function(data) {
			if (data.msg != "1") {
				layer.msg('数据请求被拒绝！');
				return;
			}
			var obj = data.list;
			_obj_district.html("<option value=\"\">区县</option>");
			for ( var i = 0; i < obj.length; i++) {
				_obj_district.append("<option value=\"" + obj[i].districtID + "\">"
						+ obj[i].districtName + "</optinon>");
			}
		},
		error : function() {
			layer.msg('数据加载超时，请刷新重试！');
		}
	});
	
}

/**
 * 选择城市触发事件
 */
_obj_city.bind("change", function() {
	
	loadDistrictData();
})

/**
 * 选择区县触发事件
 */
function loadAddress(){
	if(_obj_district.val() != ""){
		_obj_address.removeClass('hide');
	}else{
		_obj_address.addClass('hide');
		_obj_address.val("");
	}
}

_obj_district.bind("change", function() {
	loadAddress();
})



