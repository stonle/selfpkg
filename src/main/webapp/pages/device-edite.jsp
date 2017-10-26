<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5shiv.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/style.css" />
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>${sessionScope.session.title}-编辑用户</title>
</head>
<body> 
<article class="page-container">
	<form class="form form-horizontal" id="form-edite">
	<input type="hidden" name="_method" id="_method"/>
	<input type="hidden" name="devNum" id="devId"/>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>设备名称：</label>
		<div class="formControls col-xs-8 col-sm-5">
			<input type="text" class="input-text w300"  value="" placeholder="设备名称" id="devName" name="devName">
		</div>
	</div>
    <div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>地址</label>
		<div class="formControls col-xs-8 col-sm-9">
		 <span class="select-box w150">
			<select class="select required" name="province.provinceID" size="1" id="province">
				<option value="">请选择</option>
			</select>
		</span> 
		<span class="select-box w150 hide">
			<select class="select required" name="city.cityID" size="1" id="city">
				<option value="">请选择</option>
			</select>
		</span> 
		<span class="select-box w150 hide" >
			<select class="select required" name="district.districtID" size="1" id="district">
				<option value="">请选择</option>
			</select>
		</span> 
		</div>
	</div>
    
    <div class="row cl">
        <label class="form-label col-xs-4 col-sm-3"><span class="c-red"></span></label>
		<div class="formControls col-xs-8 col-sm-5">
			<input type="text" class="input-text w200"  placeholder="详细地址" id="devAddr" name="devAddr">
		</div>
	</div> 
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>所属公司</label>
		<div class="formControls col-xs-8 col-sm-9">
		 <span class="select-box w150">
			<select class="select required" name="companyInfo.copId" size="1" id="copId">
				<option value="">请选择</option>
			</select>
		</div>
	</div>
	
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">备注信息：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<textarea name="remark" cols="" rows="" class="textarea w300"  placeholder="输入100字以内..." dragonfly="true" onKeyUp="$.Huitextarealength(this,100)"></textarea>
			<p class="textarea-numberbar w300"><em class="textarea-length">0</em>/100</p>
		</div>
	</div>
	<div class="row cl">
		<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
			<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;保存&nbsp;&nbsp;">
		</div>
	</div>
	</form>
</article>


<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="static/h-ui.admin/js/H-ui.admin.js"></script>
<script type="text/javascript" src="static/h-ui.admin/js/bd-js.js"></script> 
 <!--引入js代码 -->
<script type="text/javascript" src="static/h-ui.admin/js/bd-area-new.js"></script>

<script type="text/javascript" src="lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="lib/jquery.validation/1.14.0/messages_zh.js"></script> 
<script type="text/javascript">
var _obj_company = $("#copId");
$(function(){
	selectCompany();
	var index = parent.layer.getFrameIndex(window.name);
	if(index == null || getQueryString("item") == ""){//防止直接URL打开页面
		$(".page-container").html("<font color=red><i class=\"Hui-iconfont\">&#xe60e;</i></font> 操作不允许！");
		return;
	}
	$("#devId").val(getQueryString("item"));
	$("#_method").val("put");
	$.ajaxSettings.async = true;//是否异步
	$.get("getDeviceById.do",{"devId":getQueryString("item")},function(data){
		if(data.msg == "1"){
			var obj = data.list;
			$("#devName").val(obj.devName);
			$("textarea[name='remark']").val(obj.remark);
			$("#devAddr").val(obj.devAddr);
			//下拉框赋值
			loadProvinceDate(obj.province.provinceID,obj.city.cityID,obj.district.districtID);
		    //公司赋值
			_obj_company.val(obj.companyInfo.copId);
		}else{
			layer.msg("数据加载失败#"+data.info);
		}

	},"json");
		
	$("#form-edite").validate({
		 rules:{
			 devName:{
				required:true,
				minlength:2,
				maxlength:16
			}	
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			$(form).ajaxSubmit({
				type: 'post',
				url: "editeDevice.do" ,
				success: function(data){
					if(data.msg == 1){
						parent.layer.msg('保存成功',{icon:1,time:1000});
						
						parent.$('#doQuery').click();
						parent.layer.close(index);
					}else{
						layer.msg('保存失败#'+data.info,{icon:5,time:3000});
						return;
					}
					
				},
                error: function(XmlHttpRequest, textStatus, errorThrown){
					layer.msg('error!',{icon:5,time:3000});
				}
			});
			
		}
	});
});

function selectCompany(){
	
	/**
	 *自动加载公司信息
	 */
	$.ajax({
			type : "get",
			timeout : 5000,
			async : false,
			cache : false,
			global : false,
			url : "applistCompany.do",
			dataType : "json",
			success : function(data) {
				if (data.msg != "1") {
					layer.msg('数据请求被拒绝！');
					return;
				}
				var obj = data.list;
				_obj_company.html("<option value=\"\">公司</option>");
				for ( var i = 0; i < obj.length; i++) {
					_obj_company.append("<option value=\"" + obj[i].copId + "\">"
							+ obj[i].copName + "</optinon>");
				}
			},
			error : function() {
				layer.msg('数据加载超时，请刷新重试！');
			}
	});
}
</script> 

</body>
</html>
