<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="Bookmark" href="/favicon.ico" >
<link rel="Shortcut Icon" href="/favicon.ico" />
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
<title>${sessionScope.session.title}-用户管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统管理 <span class="c-gray en">&gt;</span> 用户管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="text-l"> 用户姓名：
		<input type="text" class="input-text" style="width:150px" placeholder="输入姓名" id="username" maxlength="20">
		&nbsp;&nbsp;
		手机号:
		<input type="text" class="input-text" style="width:150px" placeholder="输入手机号" id="cellphone" maxlength="11">
		&nbsp;&nbsp;<button type="submit" class="btn btn-primary radius" id="query-data" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
	</div>
	<div class="cl pt-5 pl-5 pr-5 bg-1 bk-gray mt-15"> 
		<span class="l">
			<a href="javascript:;" onclick="data_add('添加用户','user-add.jsp','800','500')" class="btn btn-primary radius size-S"><i class="Hui-iconfont">&#xe600;</i> 添加用户</a>
			<a href="javascript:;" onclick="data_del()" class="btn btn-danger radius size-S"><i class="Hui-iconfont">&#xe6e2;</i> 删除用户</a> 
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
				<th width="150">登录名</th>
				<th width="90">手机</th>
				<th width="130">登录时间</th>
				<th width="130">创建时间</th>
				<th width="150">备注信息</th>
				<th width="40">操作</th>
			</tr>
		</thead>
		<tbody></tbody>
	</table>
	<div class="cl mt-10 hide" id="pageIndex"></div>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="static/h-ui.admin/js/H-ui.admin.js"></script>
<script type="text/javascript" src="static/h-ui.admin/js/page.js"></script>

<!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="lib/My97DatePicker/4.8/WdatePicker.js"></script> 
<script type="text/javascript" src="lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript">
$(function(){
	//默认自动加载数据
	createPage();
	//查询按钮事件
	$("#query-data").bind("click",function(){
		createPage();
	});
})

/*
	参数解释：
	title	标题
	url		请求的url
	id		需要操作的数据id
	w		弹出层宽度（缺省调默认值）
	h		弹出层高度（缺省调默认值）
*/
/*用户-增加*/
function data_add(title,url,w,h){
	layer_show(title,url,w,h);
}
/*用户-删除*/
function data_del(){
	var items = [];
	$("tbody tr td input[type='checkbox']:checked").each(function(i){
		items.push($(this).val());
    });
	if(items == ""){
		layer.msg('请先选择要删除的项');
		return;
	}
	layer.confirm('确认要删除选择的项吗？',function(index){
		 $.ajax({
			type: 'POST',
			url: 'delUser.do',
			data:{'items':items.join()},
			dataType: 'json',
			success: function(data){	
				layer.msg('已删除',{icon:1,time:1000});
				if(data.msg == 1){
					layer.msg('已删除',{icon:1,time:1000});
					$('#query-data').click();
				}else{
					layer.msg('失败#'+data.info,{icon:5,time:3000});
					return;
				}
			},
			error:function(data) {
				layer.msg('操作失败#'+data.msg,{icon:5,time:3000});
			},
		});		 
	});
}

/*用户-编辑*/
function data_edit(title,url,w,h){
	layer_show(title,url,w,h);
}

//数据分页,不可带参数
function createPage(){
	layer.load(2);
	$('tbody').html("<tr><td colspan="+$('thead th').length+">数据加载中...</td></tr>");
	//'everyPage' : 0,默认15条
	var reqParam = {'currentPage':currPage};
	reqParam['everyPage'] = $("#pageSize").val();//每页数量
	if($.trim($("#username").val()) != ""){
		reqParam['userName'] = $.trim($("#username").val());
	}
	
	if($.trim($("#cellphone").val()) != ""){
		reqParam['cellphone'] = $.trim($("#cellphone").val());
	}
	
	$.ajax({
		type : "get",
		timeout : 10000,
		async : true,
		cache : false,
		global : false,
		url : "queryUserByPage.do",
		data : reqParam,
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
				html += "<tr class=\"text-c\"><td><input type=\"checkbox\" value=\""+obj[i].userID+"\" name=\"item\"></td>";
				html += "<td  class=\"text-c\">"+(i+1)+"</td>";
				html += "<td  class=\"text-c\">"+obj[i].userName+"</td>";
				html += "<td  class=\"text-c\">"+obj[i].loginName+"</td>";
				html += "<td  class=\"text-c\">"+obj[i].cellphone+"</td>";
				html += "<td  class=\"text-c\">"+(obj[i].lastLoginDate == null ? '未登录':obj[i].lastLoginDate)+"</td>";
				html += "<td  class=\"text-c\">"+obj[i].createDate+"</td>";
				html += "<td  class=\"text-c\">"+obj[i].remark+"</td>";
				html += "<td  class=\"text-c\"> <a style=\"text-decoration:none\" class=\"ml-5\" onClick=\"data_edit('编辑用户','user-edite.jsp?item="+obj[i].userID+"','800','500')\" href=\"javascript:;\" title=\"编辑\"><i class=\"Hui-iconfont\">&#xe6df;</i></a></td></tr>";
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