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
<title>${sessionScope.session.title}-命令管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>系统管理<span class="c-gray en">&gt;</span>命令管理<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	 <div class="text-l">
	    &nbsp;&nbsp;
	            创建时间：
	    <input type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'datemin\')||\'%y-%M-%d\'}'})" id="datemin" class="input-text Wdate" style="width:120px;">
		&nbsp;&nbsp;
		接收状态：
		<span class="select-box w-100">
			<select class="select" id="rec_type">
				<option value="-1">请选择</option>
				<option value="0">未接收</option>
				<option value="1">已接收</option>
			</select>
		</span>
		&nbsp;&nbsp;
		执行状态：
		<span class="select-box w-100">
			<select class="select" id="run_type">
				<option value="-1">请选择</option>
				<option value="0">未执行</option>
				<option value="1">已执行</option>
			</select>
		</span>
		<br>
		<br>
		&nbsp;&nbsp;
		 命令名称:
		<input type="text" class="input-text" style="width:150px" placeholder="请输入命令名称" id="commandName" maxlength="11">
		&nbsp;&nbsp;
		终端编号：
		<input type="text" class="input-text" style="width:150px" placeholder="请输出终端编号" id="devNum" maxlength="30">
		&nbsp;&nbsp;<button type="submit" class="btn btn-primary radius" id="doQuery" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
	</div> 
	<div class="cl pt-5 pl-5 pr-5 bg-1 bk-gray mt-15"> 
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
				<th width="150">命令名称</th>
				<th width="150">终端编号</th>
				<th width="150">接收状态</th>
				<th width="100">执行时间</th>
				<th width="100">执行状态</th>
				<th width="100">执行结果</th>
				<th width="100">创建时间</th>
		<!-- 		<th width="60">操作</th> -->
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
	$("#doQuery").bind("click",function(){
		createPage();
	});
})
//数据分页,不可带参数
function createPage(){
	layer.load(2);
	$('tbody').html("<tr><td colspan="+$('thead th').length+">数据加载中...</td></tr>");
	//'everyPage' : 0,默认15条
	var reqParam = {'currentPage':currPage};
	reqParam['everyPage'] = $("#pageSize").val();//每页数量
	//获取参数
 	if($.trim($("#datemin").val()) != ""){
		reqParam['createTime'] = $.trim($("#datemin").val());
	}
 	if($.trim($("#rec_type").val()) != -1){
		reqParam['isSend'] = $.trim($("#rec_type").val());
	}
	if($.trim($("#run_type").val()) != -1){
		reqParam['status'] = $.trim($("#run_type").val());
	} 
	if($.trim($("#commandName").val()) != ""){
		reqParam['commandName'] = $.trim($("#commandName").val());
	} 
	if($.trim($("#devNum").val()) != ""){
		reqParam['deviceNum'] = $.trim($("#devNum").val());
	} 
	$.ajax({
		type : "get",
		timeout : 10000,
		async : true,
		cache : false,
		global : false,
		url : "queryTaskByPage.do",
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
				html += "<tr class=\"text-c\"><td><input type=\"checkbox\" value=\""+obj[i].id+"\" name=\"item\"></td>";
				html += "<td  class=\"text-c\">"+(i+1)+"</td>";
				html += "<td  class=\"text-c\">"+obj[i].commandName+"</td>";
				html += "<td  class=\"text-c\">"+obj[i].deviceNum+"</td>";
				html += "<td  class=\"text-c\">"+getIsSendICO(obj[i].isSend)+"</td>";
				html += "<td  class=\"text-c\">"+obj[i].executeTime+"</td>";
				html += "<td  class=\"text-c\">"+getStatusIOC(obj[i].status)+"</td>";
				html += "<td  class=\"text-c\">"+getIsResultIOC(obj[i].isResult)+"</td>";
				html += "<td  class=\"text-c\">"+obj[i].createTime+"</td></tr>";
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

function getIsResultIOC(isResult){
	if(isResult == "1"){
		return "<span class=\"label label-success radius\">成功</span>";
	}
	if(isResult == "0"){
		return "<span class=\"label label-danger radius\">失败</span>";
	}
}

function getStatusIOC(status){
	if(status == "1"){
		return "<span class=\"label label-success radius\">已执行</span>";
	}
	if(status == "0"){
		return "<span class=\"label label-warning radius\">未执行</span>";
	}
}

function getIsSendICO(isSend){
	if(isSend == "1"){
		return "<span class=\"label label-success radius\">已接收</span>";
	}
	if(isSend == "0"){
		return "<span class=\"label label-warning radius\">未接收</span>";
	}
}
</script>
</body>
</html>