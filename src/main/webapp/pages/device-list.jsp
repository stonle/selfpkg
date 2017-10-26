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
<title>${sessionScope.session.title}-终端管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 终端设备管理 <span class="c-gray en">&gt;</span> 终端管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="text-l"> 设备编号：
		<input type="text" class="input-text" style="width:120px" placeholder="设备编号" id="device_id" maxlength="20">
		&nbsp;&nbsp;
		设备名称:
		<input type="text" class="input-text" style="width:120px" placeholder="设备名称" id="device_name" maxlength="11">
		&nbsp;&nbsp;
		姓名:
		<input type="text" class="input-text" style="width:120px" placeholder="快递员姓名" id=courie_name" maxlength="11">
	<!-- 	&nbsp;&nbsp;
		创建时间:
		<input type="text" class="input-text" style="width:100px" placeholder="" id="create_date" maxlength="11"> -->
		&nbsp;&nbsp;<button type="submit" class="btn btn-primary radius" id="doQuery" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<a href="javascript:;" onclick="data_add('添加设备','device-add.jsp','800','500')" class="btn btn-primary radius size-S"><i class="Hui-iconfont">&#xe600;</i> 添加设备</a>
			<a href="javascript:;" onclick="data_edit('编辑设备','device-edite.jsp','800','500')" class="btn btn-primary radius size-S"><i class="Hui-iconfont">&#xe6df;</i> 编辑设备</a>
			<a href="javascript:;" onclick="data_config('终端配置','device-config.jsp','800','500')" permission="setConfig"  class="btn btn-primary radius size-S"><i class="Hui-iconfont">&#xe642;</i> 终端配置</a>
		    <a href="javascript:;" id="restart" permission="restart"  class="btn btn-primary radius size-S"><i class="Hui-iconfont">&#xe6f7;</i> 重启</a>
			<a href="javascript:;" id="upgrade" permission="upgrade"  class="btn btn-primary radius size-S margin-Top"><i class="Hui-iconfont">&#xe642;</i> 升级</a>
			<a href="javascript:;" id="addUrl" permission=""  class="btn btn-primary radius size-S"><i class="Hui-iconfont">&#xe600;</i>添加视频地址</a>
			<a href="javascript:;" onclick="data_edit_courier('分配快递员','device-add-courier.jsp','800','500')" class="btn btn-primary radius size-S"><i class="Hui-iconfont">&#xe6df;</i>分配快递员</a>
			<a href="javascript:;" onclick="data_del()" class="btn btn-danger radius size-S"><i class="Hui-iconfont">&#xe6e2;</i> 删除设备</a> 
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
				<th width="140">设备编号</th>
				<th width="140">设备名称</th>
				<th width="130">摄像头编号</th>
				<th width="130">云存储编号</th>
				<th width="160">地址</th>
				<th width="90">运行状态</th>
				<th width="90">创建人</th>
				<th width="130">创建时间</th>
				<th width="130">所属公司</th>
				<th width="100">快递员名称</th>
				<th width="150">备注信息</th>
			</tr>
		</thead>
		<tbody></tbody>
	</table>
	<div class="cl mt-10 hide" id="pageIndex"></div>
</div>


<!-- 终端重启设置 -->
<div id="modal-demo" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content radius">
			<div class="modal-header">
				<h3 class="modal-title">请选择重启的服</h3>
				<a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void();">×</a>
			</div>
			<div class="modal-body">
				 <select style="width:150px;" class="select required" id="selCmdType" size="1">
					<option value='0'>请选择</option>
					<option value='1'>重启软件</option>
					<option value='2'>重启系统</option>
				</select>
			</div>
			<div class="modal-footer">
				<button class="btn btn-primary" id="doRestartConfirm">确定</button>
				<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
			</div>
		</div>
	</div>
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

/*
	参数解释：
	title	标题
	url		请求的url
	id		需要操作的数据id
	w		弹出层宽度（缺省调默认值）
	h		弹出层高度（缺省调默认值）
*/
/*终端设备-增加*/
function data_add(title,url,w,h){
	layer_show(title,url,w,h);
}
/*终端设备-删除*/
function data_del(){
	var items = "";
	$("tbody tr td input[type='checkbox']:checked").each(function(i){
		items += $(this).val() + ",";
    });
	if(items == ""){
		layer.msg('请先选择要删除的项');
		return;
	}
	layer.confirm('确认要删除选择的项吗？',function(index){
		 $.ajax({
			type: 'POST',
			url: 'delDevice.do',
		    data:{'_method':'delete','items':items},
			dataType: 'json',
			success: function(data){	
				layer.msg('已删除',{icon:1,time:1000});
				if(data.msg == 1){
					layer.msg('已删除',{icon:1,time:1000});
					$('#doQuery').click();
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
function data_edit(title,url,w,h)
{   
	var items = [];
	$("tbody tr td input[type='checkbox']:checked").each(function(i){
		items.push($(this).val());
    });
	if(items.length==0){
		layer.msg('必须只能选择一个选项');
		return;
	}
	if(items.length>1 ){
		layer.msg('只能选择一个选项');
		return;
	}
	url = url+"?item="+items[0];
	layer_show(title,url,w,h);
}

function data_config(title,url,w,h){
	var items = "";
	var run_state = "";
	$("tbody tr td input[type='checkbox']:checked").each(function(i){
		items += ","+ $(this).val();
		run_state = $(this).parent('td').parent('tr').children('td').eq(7).text();
    });
	if(items==""){
		layer.msg('必须只能选择一个选项');
		return;
	}
	if(run_state != '正常'){
		layer.msg('请选择成功连接中的终端....');
		return;
	}
	items = items.substring(1,items.legnth);
	layer_show(title,url+"?item="+items,w,h);
}
/**
 * 为设备添加快递员
 */
function data_edit_courier(title,url,w,h){
	var items = [];
	$("tbody tr td input[type='checkbox']:checked").each(function(i){
		items.push($(this).val());
    });
	if(items.length==0){
		layer.msg('必须只能选择一个选项');
		return;
	}
	if(items.length>1 ){
		layer.msg('只能选择一个选项');
		return;
	}
	url = url+"?item="+items[0];
	layer_show(title,url,w,h);
}


//数据分页,不可带参数
function createPage(){
	layer.load(2);
	$('tbody').html("<tr><td colspan="+$('thead th').length+">数据加载中...</td></tr>");
	//'everyPage' : 0,默认15条
	var reqParam = {'currentPage':currPage};
	reqParam['everyPage'] = $("#pageSize").val();//每页数量
	if($.trim($("#device_id").val()) != ""){
		reqParam['devNum'] = $.trim($("#device_id").val());
	}
	if($.trim($("#device_name").val()) != ""){
		reqParam['devName'] = $.trim($("#device_name").val());
	}
	if($.trim($("#courie_name").val()) != ""){
		reqParam['couName'] = $.trim($("#courie_name").val());
	}
	$.ajax({
		type : "get",
		timeout : 10000,
		async : true,
		cache : false,
		global : false,
		url : "queryDeviceByPage.do",
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
				var status = checkCurrentStatus(obj[i].runStatus);
				html += "<tr class=\"text-c\"><td><input type=\"checkbox\" value=\""+obj[i].devNum+"\" name=\"item\"></td>";
				html += "<td  class=\"text-c\">"+(i+1)+"</td>";
				html += "<td  class=\"text-c\">"+obj[i].devNum+"</td>";
				html += "<td  class=\"text-c\">"+obj[i].devName+"</td>";
				html += "<td  class=\"text-c\">"+(obj[i].videoNum == null?"":obj[i].videoNum )+"</td>";
				html += "<td  class=\"text-c\">"+(obj[i].cloudNum ==null?"":obj[i].cloudNum)+"</td>";
				html += "<td  class=\"text-c\">"+obj[i].province.provinceName+obj[i].city.cityName+obj[i].district.districtName+obj[i].devAddr+"</td>";
				html += "<td  class=\"text-c\">"+status+"</td>";
				html += "<td  class=\"text-c\">"+obj[i].user.loginName+"</td>";
				html += "<td  class=\"text-c\">"+obj[i].createDate+"</td>";
				html += "<td  class=\"text-c\">"+(obj[i].companyInfo==null?'':obj[i].companyInfo.copName)+"</td>";
				html += "<td  class=\"text-c\">"+(obj[i].courierInfo==null?'':obj[i].courierInfo.couName)+"</td>";
				html += "<td  class=\"text-c\">"+obj[i].remark+"</td>";
			}
			$("tbody").html(html);  
			
		},
		error : function() {
			layer.closeAll('loading');
			layer.msg('数据加载请求异常');
		}
	});
	
}

function checkCurrentStatus(runState){
	if(runState == "未接入"){
		return "<span class=\"label radius\">未接入</span>";
	}
	if(runState == "正常"){
		return "<span class=\"label label-success radius\">正常</span>";
	}
	if(runState == "离线"){
		return "<span class=\"label label-danger radius\">离线</span>";
	}
}


//升级
$("#upgrade").bind("click",function(){
	if($("tbody input[type='checkbox']:checked").length == 0){
		layer.msg('请选择终端');
		return;
	} 
	var run_state = "";
	var deviceIdStr = "";
	$("tbody input[type='checkbox']:checked").each(function(i){
		 var row = $(this).parent('td').parent('tr');
		 run_state = row.children('td').eq(7).text();
		 deviceIdStr += "," + $(this).val();
	});
	if(run_state != '正常'){
		layer.msg('请选择成功连接中的终端....');
		return;
	}
	deviceIdStr = deviceIdStr.substring(1,deviceIdStr.legnth);
	layer_show('程序版本信息','param-dialog.jsp?item='+deviceIdStr,'700','450');
});

//添加视像头服务地址
$("#addUrl").bind("click",function(){
	if($("tbody input[type='checkbox']:checked").length == 0){
		layer.msg('请选择终端');
		return;
	} 
	if($("tbody input[type='checkbox']:checked").length > 1){
		layer.msg('只能选择一个终端');
		return;
	} 
	var deviceIdStr = "";
	$("tbody input[type='checkbox']:checked").each(function(i){
		 deviceIdStr = $(this).val();
	});
	layer_show('添加视频地址','video-dialog.jsp?item='+deviceIdStr,'700','450');
});

//重启
var deviceIdStr = "";
$("#restart").bind("click",function(){
	if($("tbody input[type='checkbox']:checked").length == 0){
		layer.msg('请选择终端');
		return;
	} 
	var run_state = "";
	deviceIdStr = "";
	$("tbody input[type='checkbox']:checked").each(function(i){
		 var row = $(this).parent('td').parent('tr');
		 run_state = row.children('td').eq(7).text();
		 deviceIdStr += "," + $(this).val();
	});
	
	if(run_state != '正常'){
		layer.msg('请选择成功连接中的终端....');
		return;
	}
	deviceIdStr = deviceIdStr.substring(1,deviceIdStr.legnth);
	$("#modal-demo").modal('show');
});

// 确定重启
$("#doRestartConfirm").click(function(){
	// 判断是否选择重启服务
	if($("#selCmdType option:selected").val()==0){
		layer.msg('请选择重启的服务');
		return;
	}
	var cmdType = $("#selCmdType option:selected").val();
	layer.load();
	$.ajax({
		type : "post",//数据提交类型
		timeout : 5000,//超时时间：单位ms
		async : true,//异步开启
		cache : false,//关闭缓存
		url : "restart.do",
		dataType : "json",
		data: {
			"deviceIdStr":deviceIdStr,
			"cmdType":cmdType
				},
		success : function(data) {
			layer.closeAll('loading');//关闭进度转动动画
			if (data.msg != "1") {
				layer.msg('重启失败:['+data.info+']');
				return;
			}else{
				$("#modal-demo").modal('hide');
				layer.msg('终端正在重啟中,请稍等2分钟！');
			}	
		},
		error : function() {
			layer.closeAll('loading');
			layer.msg('数据通信失败...');
		}
	});
});

</script>
</body>
</html>