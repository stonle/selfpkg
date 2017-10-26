<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<LINK rel="Bookmark" href="favicon.ico" >
<LINK rel="Shortcut Icon" href="favicon.ico" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<script type="text/javascript" src="lib/PIE-2.0beta1/PIE_IE678.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/style.css" />
<style type="text/css">
   .form-label{text-align: right !important;}
</style>
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>${sessionScope.session.title}-添加设备信息</title>
<meta name="keywords" content="">
<meta name="description" content="">
</head>
<body>
<article class="page-container">
	<form class="form form-horizontal" id="form">
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">&nbsp;&nbsp;&nbsp;&nbsp;*</span>主程序版本号：</label>
		<div class="formControls col-xs-8 col-sm-5">
			<input type="text" class="input-text w300" value="" id="selfpkg" name="selfpkg"  maxlength="30" placeholder="主程序版本号">
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
<!--请在下方写此页面业务相关的脚本--> 
<script type="text/javascript">
$(function(){
	var index = parent.layer.getFrameIndex(window.name); //获取当前子窗口索引	
	if(index == null || getQueryString("item") == ""){//防止直接URL打开页面
		$(".page-container").html("<font color=red><i class=\"Hui-iconfont\">&#xe60e;</i></font> 操作不允许！");
		return;
	}
	//初始化表单验证
	$("#form").validate({
        submitHandler:function(form){//提交表单时调用,替代默认提交表单的动作
       	 	layer.load(2);//进度转动动画
            
       	 	var selfpkg = $("#selfpkg").val();
       	 	var deviceIdStr = getQueryString("item");
            $.ajax({
				type : "post",//数据提交类型
				timeout : 5000,//超时时间：单位ms
				async : false,//异步开启
				cache : false,//关闭缓存
				url : "upTerGrade.do",
				dataType : "json",
				data: {
					"version":selfpkg,
					"deviceNums":deviceIdStr
				},
				success : function(data) {
					layer.closeAll('loading');//关闭进度转动动画
					if (data.msg != "1") {
						layer.msg('操作失败['+data.info+']');
						return;
					}
					parent.layer.msg('終端正在升级中,请稍等片刻！');//父窗口弹出提示
					parent.layer.close(index);//关闭当前窗口
				},
				error : function(request, textStatus, errorThrown) {
						layer.closeAll('loading');//关闭进度转动动画
						layer.msg('数据加载超时，请刷新重试！');
				}
			});
        }, 
        invalidHandler: function(form, validator) {  //验证不通过时调用
       		layer.msg('请填写完整');
       		return false; 
        }   
    });
})

</script> 
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>