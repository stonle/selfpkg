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
<link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.min.css" />2
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
	<input type="hidden" name="copId" id="copId"/>
	<input type="hidden" name="_method" id="_method"/>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>公司名称：</label>
		<div class="formControls col-xs-8 col-sm-5">
			<input type="text" class="input-text w300"  value="" placeholder="公司姓名" id="copName" name="copName">
		</div>
	</div>
<!-- 	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>登录账号：</label> 
		<div class="formControls col-xs-8 col-sm-5">
			<input type="text" class="input-text w300" autocomplete="off" value="" placeholder="登录账号" id="loginname" name="loginName" readonly>
		</div> 
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>手机号：</label>
		<div class="formControls col-xs-8 col-sm-5">
			<input type="text" class="input-text w300" value="" placeholder="手机号" id="cellphone" name="cellphone" maxlength=11>
		</div>
	</div> -->
	
	
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">备注信息：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<textarea  id="remark" name="remark" cols="" rows="" class="textarea w300"  placeholder="输入100字以内..." dragonfly="true" onKeyUp="$.Huitextarealength(this,100)"></textarea>
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
	$("#copId").val(getQueryString("item"));
	$("#_method").val("PUT");
	
	$.ajaxSettings.async = true;//是否异步
	$.get("getCompanyById.do",{"copId":getQueryString("item")},function(data){
		if(data.msg == "1"){
			var obj = data.list;
			$("#copName").val(obj.copName);
	/* 		$("#loginname").val(obj.loginName);
			$("#cellphone").val(obj.cellphone); */
			$("#remark").val(obj.remark);
		}else{
			layer.msg("数据加载失败#"+data.info);
		}
	//	console.log(JSON.stringify(obj))

	},"json");
	
	
	
	$("#form-edite").validate({
		rules:{
			copName:{
				required:true,
				minlength:2,
				maxlength:16
			}
		/* 	loginName:{
				required:true,
				minlength:4,
				maxlength:16
			},
			cellphone:{
				required:true,
				isPhone:true
			}, */
			
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			$(form).ajaxSubmit({
				type: 'post',
				url: "editeCompany.do" ,
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
</script> 

</body>
</html>