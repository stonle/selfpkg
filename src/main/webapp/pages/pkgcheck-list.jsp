<%@page import="javax.print.attribute.standard.PrinterMessageFromOperator"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="com.ls.domain.User"%>
<%@ page language="java" import="com.ls.util.UserContextUtil"%>
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
<title>${sessionScope.session.title}-包裹视频校验管理</title>
</head>
<body>
 <%
    HttpSession s = request.getSession(); 
    User user = (User)s.getAttribute("user");
    String uid = user.getUserID();
  %>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>包裹管理<span class="c-gray en">&gt;</span> 包裹视频校验<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" id="query-data"><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<input id = "userId" style="display:none" value="<%=uid%>"/>
<div class="page-container">
<!-- 	<div class="cl pt-5 pl-5 pr-5 bg-1 bk-gray mt-15"> 
		<span class="l">
			<a href="javascript:;" onclick="data_add('添加用户','company-add.jsp','800','500')" class="btn btn-primary radius size-S"><i class="Hui-iconfont">&#xe600;</i> 添加快递公司</a>
			<a href="javascript:;" onclick="data_del()" class="btn btn-danger radius size-S"><i class="Hui-iconfont">&#xe6e2;</i> 删除快递公司</a> 
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
	 </div> -->
	<table class="table table-border table-bordered table-bg">
		<thead>
			<tr class="text-c">
				<!-- <th width="25"><input type="checkbox" name="" value=""></th> -->
				<th width="40">序号</th>
				<th width="100">设备编号</th>
				<th width="100">包裹编号</th>
				<!--已验的不显示-->
				<th width="100">包裹验视状态</th>
				<th width="100">包裹验视人</th>
				<th width="100">发起时间</th>
				<th width="50">操作</th>
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
	//获取 userId
	var userId = $("#userId").val();
	var webSocket = 
		new WebSocket('ws://localhost:8080/selfpkg/websocket/'+userId);

	webSocket.onerror = function(event) {
		onError(event)
	};

	webSocket.onopen = function(event) {
		onOpen(event);
	};
    
 
	webSocket.onmessage = function(event) {
		onMessage(event)
	};
	webSocket.onclose=function(event){   
		  console.log("Connections closed");
	}; 
 	//页面离开执行该方法
 	window.onunload=function(){
 	   if(webSocket.readyState == WebSocket.OPEN){
		    webSocket.close();
        }
	}; 
	var title = "",url = "",w ="",h="",pkgNum="",devNum="";
	function onMessage(event) {
		if(event.data == "ok"){
			url = url+"&pkgNum="+pkgNum+"&devNum="+devNum;
			layer_show(title,url,w,h);
			return;
		}
		var obj = JSON.parse(event.data);
		var html="";
		$("tbody").empty();
		for(var i = 0; i< obj.length;i++){
			var state = checkCurrentStatus(obj[i].type);
			html += "<tr><td  class=\"text-c\">"+(i+1)+"</td>";
			html += "<td  class=\"text-c\">"+obj[i].devNum+"</td>";
			html += "<td  class=\"text-c\">"+obj[i].pkgNum+"</td>";
			html += "<td  class=\"text-c\">"+state+"</td>";
			html += "<td  class=\"text-c\">"+obj[i].checkUser+"</td>";
			html += "<td  class=\"text-c\">"+obj[i].createDate+"</td>";
			html += "<td  class=\"text-c\"> <a style=\"text-decoration:none\" class=\"ml-5\" onClick=\"data_edit('视频验视','pkg-check-view.jsp?ckeckUrl="+obj[i].ckeckUrl+"','500','300','"+obj[i].pkgNum+"','"+obj[i].devNum+"')\" href=\"javascript:;\" title=\"编辑\"><i class=\"Hui-iconfont\">&#xe6df;</i></a></td></tr>";
		}
		$("tbody").html(html);  
	}

	function onOpen(event) {
	
	}

	function onError(event) {
		alert(event.data);
	}
    var flag;
	function start(pkgNum,devNum) {
		//获取包裹编号和终端号
		webSocket.send(pkgNum+"|"+devNum);
		return false;
	}
	function checkCurrentStatus(type){
		if(type == 2){
			return "<span class=\"label radius\">未接入</span>";
		}
		if(type == 3){
			return "<span class=\"label label-danger radius\">正在处理中</span>";
		}
	}
	/*用户-编辑*/
	function data_edit(titles,urls,ws,hs,pkgNums,devNums)
	{   
		pkgNum = pkgNums;
		devNum = devNums;
		url = urls; 
		title = titles;
		w = ws;
		h = hs;
		start(pkgNums,devNums);
	}

</script>
</body>
</html>