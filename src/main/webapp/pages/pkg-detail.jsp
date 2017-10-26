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
<title>${sessionScope.session.title}-包裹基本信息详情查询</title>
</head>
<body> 
<article class="page-container">
	<form class="form form-horizontal" id="form-edite">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red"></span>设备编号：</label>
			<div class="formControls col-xs-8 col-sm-5">
				<input type="text" class="input-text w300 disabled"  value=""  id="devNum" name="">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red"></span>长(mm)：</label> 
			<div class="formControls col-xs-8 col-sm-5">
				<input type="text" class="input-text w300 disabled" autocomplete="off" value="" id="pkglength" name="pkglength" >
			</div> 
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red"></span>宽(mm)：</label>
			<div class="formControls col-xs-8 col-sm-5">
				<input type="text" class="input-text w300 disabled" value=""  id="pkgwidth" name="" >
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red"></span>高(mm)：</label>
			<div class="formControls col-xs-8 col-sm-5">
				<input type="text" class="input-text w300 disabled" value=""  id="pkgheight" name="">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red"></span>重量(g)：</label>
			<div class="formControls col-xs-8 col-sm-5">
				<input type="text" class="input-text w300 disabled" value=""  id="pkgweight" name="" >
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red"></span>收费金额：</label>
			<div class="formControls col-xs-8 col-sm-5">
				<input type="text" class="input-text w300 disabled" value="" id="pkgMany" name="">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red"></span>揽收时间：</label>
			<div class="formControls col-xs-8 col-sm-5">
				<input type="text" class="input-text w300 disabled" value=""  id="createtime" name="">
			</div>
		</div>
	</form>
</article>


<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="static/h-ui.admin/js/H-ui.admin.js"></script>
<script type="text/javascript" src="static/h-ui.admin/js/bd-js.js"></script>

<script type="text/javascript" src="lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="lib/jquery.validation/1.14.0/messages_zh.js"></script> 
<script type="text/javascript">
$(function(){
	var index = parent.layer.getFrameIndex(window.name);
	if(index == null || getQueryString("item") == ""){//防止直接URL打开页面
		$(".page-container").html("<font color=red><i class=\"Hui-iconfont\">&#xe60e;</i></font> 操作不允许！");
		return;
	}
	$("#userID").val(getQueryString("item"));
	
	$.ajaxSettings.async = true;//是否异步
	$.get("getPkgInfoBypckNum.do",{"pkgNum":getQueryString("item")},function(data){
		if(data.msg == "1"){
			var obj = data.list;
			if(obj == null){
				return;
			}
			$("#devNum").val(obj.orderInfo.pkgNum);
			$("#pkgwidth").val(obj.width);
			$("#pkglength").val(obj.length);
			$("#pkgheight").val(obj.height);
			$("#pkgweight").val(obj.weight);
			$("#pkgMany").val(obj.paymentAmount);
			$("#createtime").val(obj.createDate);
		}else{
			layer.msg("数据加载失败#"+data.info);
		}
	},"json");
	
	
/* 	
	$("#form-edite").validate({
		rules:{
			userName:{
				required:true,
				minlength:2,
				maxlength:16
			},
			loginName:{
				required:true,
				minlength:4,
				maxlength:16
			},
			cellphone:{
				required:true,
				isPhone:true
			},
			
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			$(form).ajaxSubmit({
				type: 'post',
				url: "editeUser.do" ,
				success: function(data){
					if(data.msg == 1){
						parent.layer.msg('保存成功',{icon:1,time:1000});
						
						parent.$('#query-data').click();
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
	}); */
});
</script> 

</body>
</html>