<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<LINK rel="Bookmark" href="/favicon.ico" >
<LINK rel="Shortcut Icon" href="/favicon.ico" />
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
<!-- <link rel="stylesheet" type="text/css" href="lib/bootstrap/css/bootstrap.min.css"> -->

<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>${sessionScope.session.title}-内容管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统管理 <span class="c-gray en">&gt;</span> 程序版本管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="text-l"> 
       <button type="submit" class="btn btn-primary radius hide" id="doQuery" name=""></button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray"> 
		<span class="l">
			<a href="javascript:;" id="popAddPage"  class="btn btn-primary radius size-S"><i class="Hui-iconfont">&#xe642;</i> 上传</a>
			<a href="javascript:;" id="deleteItem" class="btn btn-danger radius  size-S"><i class="Hui-iconfont">&#xe6e2;</i> 删除</a> 
		</span>

	</div>
	<table class="table table-border table-bordered table-bg  table-hover table-sort mt-10">
		<thead>
			<tr class="text-c">
				<th width="25"><input type="checkbox" name="" value=""></th>
				<th width="150">版本号</th>
				<th width="150">文件名称</th>
				<th width="150">文件类型</th>
				<th width="60">文件大小</th>
				<th width="100">创建人</th>
				<th width="120">上传时间</th>
<!-- 				<th width="60">操作</th> -->
			</tr>
		</thead>
		<tbody></tbody>
	</table>
	<div class="cl mt-10 hide" id="pageIndex"></div>
	<div class="Huialert Huialert-info mt-10"><i class="icon-remove"></i>
				备注：程序压缩包文件应用于终端设备程序升级。
			</div>
</div>

<!--确认删除-->
<div id="deleteConfirmModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content radius">
			<div class="modal-header">
				 <h4 id="myModalLabel">操作确认</h4>
				 <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
			</div>
			<div class="modal-body">
				<i class="Hui-iconfont">&#xe688;</i> 确认删除选中的项？
			</div>
			<div class="modal-footer">
				<button class="btn btn-primary" id="doDelConfirm">确定</button>
				<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
			</div>
		</div>
	</div>
</div>
<!--END确认删除-->

<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="static/h-ui.admin/js/H-ui.admin.js"></script>
<script type="text/javascript" src="static/h-ui.admin/js/page.js"></script>
<script type="text/javascript" src="static/h-ui.admin/js/cmn.js"></script>
<!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="lib/My97DatePicker/4.8/WdatePicker.js"></script> 
<script type="text/javascript" src="lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="lib/laypage/1.2/laypage.js"></script>

<script type="text/javascript">

$(function(){
	//上传页面
	$("#popAddPage").bind("click",function(){
		popPage('fileResource-upload.jsp?pk='+timestamp,'80%','80%','程序压缩包上传');
	});
	
	//删除
	$("#deleteItem").bind("click",function(){
		if($("tbody input[type='checkbox']:checked").length == 0){
			layer.msg('请选择要删除的项');
			return;
		}
		$("#deleteConfirmModal").modal('show');
	});
	
	//确认操作：确认删除按钮
	$("#doDelConfirm").click(function(){
		layer.load();// 加载状态GIF
		var items = "";
		$("tbody tr td input[type='checkbox']:checked").each(function(i){
			items += $(this).val() + ",";
	    });
		var result = doAction(1,'delFileResource.do', {'_method':'delete','items':items});
		layer.closeAll('loading');// 加载完成
		if (result == "1") {
			layer.msg('操作成功');
			createPage();
			$("#deleteConfirmModal").modal('hide');
			return;
		}
		layer.msg('操作失败['+result+']');
	});
	
	//查询按钮事件
	$("#doQuery").bind("click",function(){
		createPage();
	});
	
	createPage();
});
//数据分页,不可带参数
function createPage(){
	layer.load();
	$('tbody').html("<tr><td colspan="+$('thead th').length+">数据加载中...</td></tr>");
	$.ajax({
		type : "get",
		timeout : 5000,
		async : true,
		cache : false,
		global : false,
		url : "queryFileResource.do",
		data : {},
		dataType : "json",
		success : function(data) {
			layer.closeAll('loading');
			if(data.msg != "1"){
				layer.msg('数据加载异常['+data.msg+']');
				return;
			}
			var obj = data.list;
			if(obj.length == 0){
				$("tbody").html("<tr><td colspan="+$('thead th').length+">#查询无数据</td></tr>");
				return;
			}
			$("#totalCount").html(data.totalCount);
			$("#currentPage").html(data.currentPage);
			$("#totalPage").html(data.totalPage);
			$.pageIndex(data.currentPage,data.totalPage,'createPage');
			$("#pageIndex").removeClass('hide');
			var html = "";
			for(var i = 0; i< obj.length;i++){
				var size = (obj[i].fileResourceSize/1048576).toFixed(2);
				
				html += "<tr class=\"text-c\"><td><input type=\"checkbox\" value=\""+obj[i].id+"\" name=\"fileResource\"></td>";
				html += "<td>"+obj[i].fileVersion+"</td>";
				html += "<td>"+obj[i].fileName+"</td><td>"+obj[i].fileType+"</td>";
				html += "<td>"+obj[i].fileSize+"KB</td><td>"+obj[i].createUser+"</td><td>"+obj[i].createTime+"</td></tr>";
			}
			$("tbody").html(html); 
		},
		error : function(request, textStatus, errorThrown) {
				layer.closeAll('loading');//关闭进度转动动画
				layer.msg('数据加载超时，请刷新重试！');
		}

	});	
}
</script>
</body>
</html>