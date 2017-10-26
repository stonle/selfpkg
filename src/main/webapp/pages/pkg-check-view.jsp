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
<title>${sessionScope.session.title}-编辑用户</title>
</head>
<body> 
<article class="page-container">
  <table class="table">
	  <tr>
        <td class="va-c">视频链接地址：</td>
        <td><input type="button" class="btn btn-primary radius w200"  value="打开视频" id="open_video"></td>
      </tr>
      <tr>
        <td class="va-c">验视结果：</td>
        <td>
            <input type="button" class="btn btn-primary radius w100"  value="成功" id="sucess">
            <input type="button" class="btn btn-danger  radius w100"  value="失败" id="fail">
        </td>
      </tr>
  </table>
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
var index;
   $(function(){
	    index = parent.layer.getFrameIndex(window.name);
		if(index == null || getQueryString("ckeckUrl") == ""){//防止直接URL打开页面
			$(".page-container").html("<font color=red><i class=\"Hui-iconfont\">&#xe60e;</i></font> 操作不允许！");
			return;
		}
	    $("#sucess").bind("click",function(){
	    	var devNum = getQueryString("devNum");
		    var pkgNum = getQueryString("pkgNum");
		    saveResult(devNum,pkgNum,true);
	    });
        $("#fail").bind("click",function(){
        	var devNum = getQueryString("devNum");
    	    var pkgNum = getQueryString("pkgNum");
    	    saveResult(devNum,pkgNum,false);
	    });
        
        $("#open_video").bind("click",function(){
        	var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串  
        	console.log(navigator.appName);
            var isIE = userAgent.indexOf("Trident") > -1; //判断
        	if(isIE){
        		var url = window.location.href;
        		var sss = url.substring(url.indexOf("ckeckUrl=")+9,url.indexOf("&pkgNum"));
        		window.open(sss,'_blank','width=1200,height=800');
        	}else{
        		layer.msg('请选择IE浏览器！');
        	}
            
        });
   });
   
   
   function saveResult(devNum,pkgNum,flag){
	   var reqParam = {"devNum":devNum,"pkgNum":pkgNum,"flag":flag,"_method":"put"};
	   $.ajax({
			type : "post",
			timeout : 10000,
			async : true,
			cache : false,
			global : false,
			url : "saveResult.do",
			data : reqParam,
			dataType : "json",
			success : function(data) {
				if(data.msg == 1){
					layer.msg('已处理',{icon:1,time:1000});
					//删除表格中的数据
					parent.layer.close(index);
				}else{
					layer.msg('失败#'+data.info,{icon:5,time:3000});
					return;
				}
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