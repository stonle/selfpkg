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
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/style.css" />
<link rel="stylesheet" type="text/css" href="lib/bootstrap-fileinput/css/fileinput.css" />

<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>${sessionScope.session.title}-文件上传</title>
<style>
.page-container{margin:20px}
.row{padding-bottom: 5px;}
</style>
</head>
<body>

<!-- <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 内容管理 <span class="c-gray en">&gt;</span> 内容制作 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
 --><div class="page-container">
 	<form enctype="multipart/form-data">
 		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>主程序版本号：</label>
			<div class="formControls col-xs-8 col-sm-3">
				<input type="text" id="versionNo" class="input-text required w310"  value=""  name="versionNo"  maxlength="30" placeholder="主程序版本号">
			</div>
			<label class="form-label"><span class="c-red" id="error-code"></span></label>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"> &nbsp;&nbsp;备注信息：</label>
			<div class="formControls col-xs-8 col-sm-8">
			    <textarea name="remark" id="remark" cols="" rows="" class="textarea w310"  placeholder="输入100字以内..." dragonfly="true" onKeyUp="$.Huitextarealength(this,100)"></textarea>
			    <p class="textarea-numberbar w300"><em class="textarea-length">0</em>/100</p>
			</div>
		</div>
        <input id="file-0" class="file" name="file" type="file" multiple data-min-file-count="1">      
     </form>
  </div>
  

<!--END 确认-->
<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script>  
<script type="text/javascript" src="lib/layer/2.4/layer.js"></script> 
<script type="text/javascript" src="lib/bootstrap-modal/2.2.4/bootstrap-modalmanager.js"></script>
<script type="text/javascript" src="lib/bootstrap-modal/2.2.4/bootstrap-modal.js"></script>  
<script type="text/javascript" src="static/h-ui.admin/js/cmn.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js" type="text/javascript"></script>
<script type="text/javascript" src="lib/bootstrap-fileinput/js/fileinput.min.js"></script>
<script type="text/javascript" src="lib/bootstrap-fileinput/js/fileinput_locale_zh.js"></script>

<script type="text/javascript">
	var index = parent.layer.getFrameIndex(window.name); //获取当前子窗口索引	
	fodderType = function() {
		return $("#versionNo").val();
	};
	$("#versionNo").bind("blur",function(){
		 $.ajax({
				type: 'get',
				url: 'checkSysFileInfoVersion.do',
				data:{"versionNo" : fodderType()},
				dataType: 'json',
				success: function(data){	
					if(data.msg == "0"){
						$("#error-code").append(data.info);
					}else{
						$("#error-code").empty();
					}
				},
				error:function(data) {
					layer.msg('操作失败#'+data.msg,{icon:5,time:3000});
				},
			});		 
	});

  $('#file-0').fileinput({
        language: 'zh',
        uploadUrl: 'uploadFileResource.do',
        allowedFileExtensions : ['tar.gz','apk'],
  /*       allowedFileTypes: ['video', 'flash','object'], */
        //文件类型的内容缩略图并且只显示 previewIcon  而非小图，你可以设置这个字段的值为null，空，或者false。
        allowedPreviewTypes:null,
        maxFileCount:5,
        maxFileSize:1024*5000,//KB
        uploadExtraData: function(previewId, index) {   
				        	var obj = {};
        					var versionNo = fodderType();
        					if(!versionNo){
					       		layer.msg("请输入主程序版本号");
					       		return;
        					}
				    	　　	obj.versionNo = versionNo;
				    	　　	obj.remark = $("#remark").val();
				    	　　	return obj;
				    	　} 
  }).on("fileuploaded", function (event, data, previewId, index) {
       if(data.response.msg != "1"){
       		layer.msg(data.response.error);
       		return;
       }
       parent.layer.msg(data.response.info+"上传成功");//父窗口弹出提示
	   parent.createPage();
	   layer.close(index);//关闭当前窗口
  });
</script>
</body>
</html>