﻿<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
<title>${sessionScope.session.title}</title>
</head>
<body>
<header class="navbar-wrapper">
	<div class="navbar navbar-fixed-top">
		<div class="container-fluid cl"> <a class="logo navbar-logo f-l mr-10 hidden-xs" href="#">${sessionScope.session.title}</a> <a class="logo navbar-logo-m f-l mr-10 visible-xs" href="#">${sessionScope.session.title}</a> 
			<span class="logo navbar-slogan f-l mr-10 hidden-xs">${sessionScope.session.version}</span> 
			<a aria-hidden="false" class="nav-toggle Hui-iconfont visible-xs" href="javascript:;">&#xe667;</a>
		 
			<nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
				<ul class="cl">
					<%-- <li>${sessionScope.session.username}</li> --%>
					<li class="dropDown dropDown_hover">
						<a href="#" class="dropDown_A">${sessionScope.session.username} <i class="Hui-iconfont">&#xe6d5;</i></a>
						<ul class="dropDown-menu menu radius box-shadow">
							<!-- <li><a href="javascript:;" onClick="myselfinfo()">个人信息</a></li>
							<li><a href="#">切换账户</a></li> -->
							<li><a href="../logout.do" title="退出登录">退出</a></li>
					</ul>  
				</li>
					<li id="Hui-msg"> <a href="#" title="消息"><span class="badge badge-danger">1</span><i class="Hui-iconfont" style="font-size:18px">&#xe68a;</i></a> </li>
					<li id="Hui-skin" class="dropDown right dropDown_hover"> <a href="javascript:;" class="dropDown_A" title="换肤"><i class="Hui-iconfont" style="font-size:18px">&#xe62a;</i></a>
						<ul class="dropDown-menu menu radius box-shadow">
							<li><a href="javascript:;" data-val="default" title="默认（黑色）">默认（黑色）</a></li>
							<li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
							<li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
							<li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
							<li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
							<li><a href="javascript:;" data-val="orange" title="橙色">橙色</a></li>
					</ul>
				</li>
			</ul>
		</nav>
	</div>
</div>
</header>
<aside class="Hui-aside">
	<div class="menu_dropdown bk_2">
	<dl id="menu-member">
		<dt><i class="Hui-iconfont">&#xe60d;</i> 会信息员管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
		<dd>
			<ul>
				<li><a data-href="member-list.jsp" data-title="会员管理" href="javascript:;">会员管理</a></li>	
<!-- 				<li><a data-href="send-list.jsp" data-title="寄件人信息管理" href="javascript:;">寄件地址管理</a></li>	
				<li><a data-href="recv-list.jsp" data-title="收件人信息管理" href="javascript:;">常用收件人信息</a></li>	 -->
		    </ul>
	    </dd>
	</dl>
	<dl id="menu-company">
			<dt><i class="Hui-iconfont">&#xe616;</i> 快递公司管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a data-href="company-list.jsp" data-title="快递公司管理" href="javascript:void(0)">快递公司管理</a></li>
					<li><a data-href="courier-list.jsp" data-title="快递员管理" href="javascript:void(0)">快递员管理</a></li>
			   </ul>
		</dd>
	</dl>
	<dl id="menu-article">
			<dt><i class="Hui-iconfont">&#xe616;</i> 终端设备管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a data-href="device-list.jsp" data-title="终端设备管理" href="javascript:void(0)">终端设备管理</a></li>
			</ul>
		</dd>
	</dl>
	<dl id="menu-product">
		<dt><i class="Hui-iconfont">&#xe620;</i> 包裹管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
		<dd>
			<ul>
		        <li><a data-href="pkgcheck-list.jsp" data-title="包裹视频校验管理" href="javascript:void(0)">包裹视频校验管理</a></li>
			    <li><a data-href="pkgentry-list.jsp" data-title="包裹信息管理" href="javascript:void(0);">包裹信息管理</a></li>	
			 </ul>
		</dd>
	</dl>
	<dl id="menu-system">
			<dt><i class="Hui-iconfont">&#xe62e;</i> 系统管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a data-href="user-list.jsp" data-title="用户管理" href="javascript:void(0)">用户管理</a></li>
					<li><a data-href="system-log.jsp" data-title="系统日志" href="javascript:void(0)">系统日志</a></li>
					<li><a data-href="version-list.jsp" data-title="程序版本管理" href="javascript:void(0)">程序版本管理</a></li>
				    <li><a data-href="command-list.jsp" data-title="命令管理" href="javascript:void(0)">命令管理</a></li>
			  </ul>
	       </dd>
	</dl>
</div>
</aside>
<div class="dislpayArrow hidden-xs"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<section class="Hui-article-box">
	<div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
		<div class="Hui-tabNav-wp">
			<ul id="min_title_list" class="acrossTab cl">
				<li class="active">
					<span title="我的桌面" data-href="welcome.jsp">我的桌面</span>
					<em></em></li>
		</ul>
	</div>
		<div class="Hui-tabNav-more btn-group"><a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a></div>
</div>
	<div id="iframe_box" class="Hui-article">
		<div class="show_iframe">
			<div style="display:none" class="loading"></div>
			<iframe scrolling="yes" frameborder="0" src="welcome.jsp"></iframe>
	</div>
</div>
</section>

<div class="contextMenu" id="Huiadminmenu">
	<ul>
		<li id="closethis">关闭当前 </li>
		<li id="closeall">关闭全部 </li>
</ul>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="static/h-ui.admin/js/H-ui.admin.js"></script>
<!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="lib/jquery.contextmenu/jquery.contextmenu.r2.js"></script>
<script type="text/javascript">
$(function(){

});


</script> 
</body>
</html>