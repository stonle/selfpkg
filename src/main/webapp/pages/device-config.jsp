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
<title>${sessionScope.session.title}-终端配置</title>
</head>
<body> 
<div class="page-container">
	<div class="cl pt-5 pl-5 pr-5 bg-1 bk-gray mt-15"> 
	      <!-- 快递员通过手机app端注册，暂时保留 -->
    	<span class="l">
			<a href="javascript:;" id="add_config"    class="btn btn-primary radius size-S"><i class="Hui-iconfont">&#xe600;</i>添加</a> 
		    <a href="javascript:;" id="delete_config" class="btn btn-danger radius size-S"><i class="Hui-iconfont">&#xe6e2;</i>取消</a> 
		    <a href="javascript:;" id="save_config_date" class="btn btn-primary radius size-S"><i class="Hui-iconfont">&#xe632;</i> 确认按钮</a> 
		</span> 
	 </div>
	<table class="table table-border table-bordered table-bg">
		<thead>
			<tr class="text-c">
			    <th width="25"><input type="checkbox" name="" value=""></th>
				<th width="50">序号</th>
				<th width="150">配置文件key</th>
				<th width="150">配置文件value</th>
				<th width="150">类型</th>
			</tr>
		</thead>
		<tbody></tbody>
	</table>
</div>


<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="static/h-ui.admin/js/H-ui.admin.js"></script>
<!--引入js代码 -->
<script type="text/javascript" src="static/h-ui.admin/js/bd-area.js"></script>
<script type="text/javascript" src="static/h-ui.admin/js/bd-js.js"></script> 
<script type="text/javascript" src="static/h-ui.admin/js/page.js"></script>

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
	//查询按钮事件
	$("#add_config").bind("click",function(){
		//获取tbody下的tr
		var i = $("tbody").children().length;
		var html="";
		var slectd = "<select class=\"select\" id=\"pageSize\"><option value=\"\">请选择</option><option value=\"0\">新增</option><option value=\"1\">修改</option><option value=\"2\">删除</option></select>"
		html += "<tr class=\"text-c\"><td><input type=\"checkbox\" value=\""+(i+1)+"\" name=\"item\"></td>";
		html += "<td  class=\"text-c\">"+(i+1)+"</td>";
		html += "<td  class=\"text-c\"><input type=\"text\" name=\"\" value=\"\"></td>";
		html += "<td  class=\"text-c\"><input type=\"text\" name=\"\" value=\"\"></td>";
		html += "<td  class=\"text-c\">"+slectd+"</td></tr>";
		$("tbody").append(html);
	});
	
	$("#delete_config").bind("click",function(){
		if($("tr td input[type='checkbox']:checked").length == 0){
			layer.msg('请选择要取消的配置');
			return;
		}
		$("tbody tr td input[type='checkbox']:checked").each(function(){
			$(this).parent().parent().remove();
		});
		//移除过后重排序下，修改序号
		var i =1;
		$("tbody tr td input[type='checkbox']").each(function(){
			$(this).val(i);
			i++;
		});
		var o = 1;
		$("tbody tr").each(function(){
			$(this).find("td:eq(1)").html(o);
			o++;
		});
	});
	
	 //确认操作
	$("#save_config_date").click(function(){
		var objArr = [];
		var devNum = getQueryString("item");
		var configFlag = false;
		//获取表格的说有数据
		 $("tbody tr").each(function(){
			 var obj = new Object();
			 obj.configKey = $(this).find("td:eq(2)").find("input").val();
			 obj.configValue = $(this).find("td:eq(3)").find("input").val();
			 var configType = $(this).find("td:eq(4)").find("select").val();
			 if(configType =="" || configType ==null){
				 configFlag = true;
			 }
			 obj.configType = configType;
			 obj.deviceNum = devNum;
			 objArr.push(obj);
		 });
         if(configFlag){
        	 layer.msg('类型选择不能为空！...');
        	 return;
         }
		 $.ajax({
				type : "post",//数据提交类型
				timeout : 5000,//超时时间：单位ms
				async : true,//异步开启
				cache : false,//关闭缓存
				url : "addDeviceConfig.do",
				contentType : 'application/json;charset=utf-8', //设置请求头信息
				dataType : "json",
				data: JSON.stringify(objArr),
				//data：$.$.toJSON(objArr),
				success : function(data) {
					layer.closeAll('loading');//关闭进度转动动画
					if (data.msg != "1") {
						layer.msg('操作失败['+data.msg+']'+data.info+'');
						return;
					}else{
						parent.$('#doQuery').click();
						parent.layer.msg('操作成功');//父窗口弹出提示
						parent.layer.close(index);//关闭当前窗口
					
					}	
				},
				error : function() {
					layer.closeAll('loading');
					layer.msg('数据通信失败...');
				}
			});
	});
})
</script> 

</body>
</html>