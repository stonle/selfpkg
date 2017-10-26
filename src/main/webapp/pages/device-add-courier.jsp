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
<title>${sessionScope.session.title}-分配快递员</title>
</head>
<body> 
<div class="page-container">
	 <div class="text-l">姓名：
		<input type="text" class="input-text" style="width:150px" placeholder="输入姓名" id="couname" maxlength="20">
		&nbsp;&nbsp;
		手机号:
		<input type="text" class="input-text" style="width:150px" placeholder="输入手机号" id="couphone" maxlength="11">
		&nbsp;&nbsp;
		公司：
		<input type="text" class="input-text" style="width:150px" placeholder="输入公司" id="copname" maxlength="30">
		&nbsp;&nbsp;<button type="submit" class="btn btn-primary radius" id="doQuery" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
	</div> 
	<div class="cl pt-5 pl-5 pr-5 bg-1 bk-gray mt-15"> 
	      <!-- 快递员通过手机app端注册，暂时保留 -->
    	<span class="l">
			<!-- <a href="javascript:;" onclick="data_add('添加用户','courier-add.jsp','800','500')" class="btn btn-primary radius size-S"><i class="Hui-iconfont">&#xe600;</i> 添加快递员</a> -->
		    <a href="javascript:;" id="saveDate" class="btn btn-primary radius size-S"><i class="Hui-iconfont">&#xe632;</i> 确认按钮</a> 
		</span> 
		<span class="r">每页&nbsp;<span class="select-box w-60">
			<select class="select" id="pageSize">
				<option value="10">10</option>
				<option value="15">15</option>
				<option value="20">20</option>
				<option value="20">25</option>
				<option value="20">30</option>
			</select>
		</span> 共有数据：<strong id="totalCount"></strong> 项</span> 
		<!-- <span class="r">共有数据：<strong id="totalCount">0</strong> 条</span> -->
	 </div>
	<table class="table table-border table-bordered table-bg">
		<thead>
			<!-- <tr>
				<th scope="col" colspan="9">列表</th>
			</tr> -->
			<tr class="text-c">
				<th width="25"><input type="checkbox" name="" value=""></th>
				<th width="40">序号</th>
				<th width="150">姓名</th>
				<th width="150">电话号码</th>
				<th width="150">身份证号</th>
				<th width="100">创建时间</th>
				<th width="100">登录时间</th>
				<th width="100">公司名称</th>
		<!-- 		<th width="60">操作</th> -->
			</tr>
		</thead>
		<tbody></tbody>
	</table>
	<div class="cl mt-10 hide" id="pageIndex"></div>
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
	//默认自动加载数据
	createPage();
	
	
	
	//查询按钮事件
	$("#doQuery").bind("click",function(){
		createPage();
	});
	
	 //确认操作
	$("#saveDate").click(function(){
		if($("tbody tr td input[type='checkbox']:checked").length == 0){
			layer.msg('请选择要分配的快递员');
			return;
		}
		if($("tbody tr td input[type='checkbox']:checked").length>1){
			layer.msg('只能选择一个');
			return;
		}
		var courierId = "";
		$("tbody tr td input[type='checkbox']:checked").each(function(){
			courierId = $(this).val();
		});
		
		 $.ajax({
				type : "post",//数据提交类型
				timeout : 5000,//超时时间：单位ms
				async : true,//异步开启
				cache : false,//关闭缓存
				url : "addDeviceCouier.do",
				dataType : "json",
				data: {"_method":"put","devNum":getQueryString("item"),"courierId":courierId},
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

//数据分页,不可带参数
function createPage(){
	layer.load(2);
	$('tbody').html("<tr><td colspan="+$('thead th').length+">数据加载中...</td></tr>");
	//'everyPage' : 0,默认15条
	var reqParam = {'currentPage':currPage};
	reqParam['everyPage'] = $("#pageSize").val();//每页数量
 	if($.trim($("#couname").val()) != ""){
		reqParam['couName'] = $.trim($("#couname").val());
	}
	if($.trim($("#couphone").val()) != ""){
		reqParam['couPhone'] = $.trim($("#couphone").val());
	} 
	if($.trim($("#copname").val()) != ""){
		reqParam['copName'] = $.trim($("#copname").val());
	} 
	$.ajax({
		type : "get",
		timeout : 10000,
		async : true,
		cache : false,
		global : false,
		url : "queryCourierByPage.do",
		data :reqParam,
		dataType : "json",
		success : function(data) {
			layer.closeAll('loading');
			if(data.msg != "1"){
				$('tbody').html("<tr><td colspan="+$('thead th').length+">数据加载失败["+data.info+"]</td></tr>"); 
				layer.msg('数据加载失败['+data.msg+']');
				return;
			}
			var obj = data.list;
			if(obj.length == 0){
				$("tbody").html("<tr><td colspan="+$('thead th').length+">#查询无数据#</td></tr>");
				return;
			}
			var html = "";
			$("#totalCount").html(data.totalCount);
			$("#currentPage").html(data.currentPage);
			$("#totalPage").html(data.totalPage);
			$.pageIndex(data.currentPage,data.totalPage,'createPage');
			$("#pageIndex").removeClass('hide');
			for(var i = 0; i< obj.length;i++){
				html += "<tr class=\"text-c\"><td><input type=\"checkbox\" value=\""+obj[i].couId+"\" name=\"item\"></td>";
				html += "<td  class=\"text-c\">"+(i+1)+"</td>";
				html += "<td  class=\"text-c\">"+obj[i].couName+"</td>";
				html += "<td  class=\"text-c\">"+obj[i].couPhone+"</td>";
				html += "<td  class=\"text-c\">"+obj[i].couCard+"</td>";
				html += "<td  class=\"text-c\">"+obj[i].createTime+"</td>";
				html += "<td  class=\"text-c\">"+obj[i].lastLoginTime+"</td>";
				html += "<td  class=\"text-c\">"+obj[i].companyInfo.copName+"</td>";
				/* html += "<td  class=\"text-c\"> <a style=\"text-decoration:none\" class=\"ml-5\" onClick=\"data_edit('编辑用户','company-edite.jsp?item="+obj[i].copId+"','800','500')\" href=\"javascript:;\" title=\"编辑\"><i class=\"Hui-iconfont\">&#xe6df;</i></a></td></tr>"; */
			}
			$("tbody").html(html);  
			
		},
		error : function() {
			layer.closeAll('loading');
			layer.msg('数据加载请求异常');
		}
	});
}
</script> 

</body>
</html>