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
<title>${sessionScope.session.title}-快递公司</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>快递公司管理<span class="c-gray en">&gt;</span> 快递公司管理<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" id="query-data"><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	 <form action="upload.do" method="post" enctype="multipart/form-data">  
        <input type="file" name="file" /> <input type="submit" value="Submit" />
    </form>  
    
    <a href="<%=request.getContextPath() %>/pages/downPhotoById.do">下载照片</a>
    
    </br>
    
    <a href="<%=request.getContextPath() %>/pages/downOdex.do">下载文件</a>
    
    
	<div class="cl pt-5 pl-5 pr-5 bg-1 bk-gray mt-15">
	   <div class="row">
	      <label for="fileToUpload">请选择需要上传的文件</label>
	      <input type="file" name="fileToUpload" id="fileToUpload" onchange="fileSelected();" multiple/>
	      <div id="fileFrame" style="max-height: 400px; overflow:auto;min-height: 100px;">
	    </div>
		<div class="row">
		     &nbsp; &nbsp; &nbsp;<button onclick="uploadFiles()">上传</button>
		    <button onclick="pauseUpload()">暂停</button>
		    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="progressNumber"></label>
		</div>
		<div id="msg" style="max-height: 400px; overflow:auto;min-height: 100px;">
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

	var msg = null;
	var paragraph = 1024*1024*0.5;  //每次分片传输文件的大小 1M
	var blob = null;//  分片数据的载体Blob对象
	var fileList = null; //传输的文件
	var uploadState = 0;  // 0: 无上传/取消， 1： 上传中， 2： 暂停
	//加载及执行
    $(function(){
    	 msg = document.getElementById("msg");
    })
     
    function uploadFiles(){
        //将上传状态设置成1
       uploadState = 1;
       if(fileList.files.length>0){
           for(var i = 0; i< fileList.files.length; i++){
               var file = fileList.files[i];
               uploadFileInit(file,i);
           }
       }else{
           msg.innerHTML = "请选择上传文件！";
       }
   }
	
    /**
     * 获取服务器文件大小，开始续传
     * @param file
     * @param i
     */
    function uploadFileInit(file,i){
        if(file){
            var startSize = 0;
            var endSize = 0;
            var date = file.lastModifiedDate;
            var lastModifyTime = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+"-"
            +date.getHours()+"-"+date.getMinutes()+"-"+date.getSeconds()
            //获取当前文件已经上传大小
            jQuery.post("getChunkedFileSize.do",
                    {"fileName":encodeURIComponent(file.name),"fileSize":file.size,"lastModifyTime":lastModifyTime,"chunkedFileSize":"chunkedFileSize"},
                    function(data){
                        if(data.lenght != -1){
                            endSize = Number(data);
                        }
                        uploadFile(file,startSize,endSize,i);
                
            });
             
        }
    }
    
    /**
     * 分片上传文件
     */
    function uploadFile(file,startSize,endSize,i) {
            var date = file.lastModifiedDate;
            var lastModifyTime = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+"-"
            +date.getHours()+"-"+date.getMinutes()+"-"+date.getSeconds()
            var reader = new FileReader();
            reader.onload = function loaded(evt) {
                // 构造 xmlHttpRequest 对象，发送文件 Binary 数据
                var xhr = new XMLHttpRequest(); 
                xhr.sendAsBinary = function(text){
                        var data = new ArrayBuffer(text.length);
                        var ui8a = new Uint8Array(data, 0);
                        for (var i = 0; i < text.length; i++) ui8a[i] = (text.charCodeAt(i) & 0xff);
                        this.send(ui8a);
                } 

                 xhr.onreadystatechange = function(){
                    if(xhr.readyState==4){
                        //表示服务器的相应代码是200；正确返回了数据   
                       if(xhr.status==200){ 
                           //纯文本数据的接受方法   
                           var message=xhr.responseText; 
                           message = Number(message);
                           uploadProgress(file,startSize,message,i);
                        } else{
                            msg.innerHTML = "上传出错，服务器相应错误！";
                        }  
                   }  
                };//创建回调方法
                xhr.open("POST", 
                        "appendUpload2Server.do?fileName=" + encodeURIComponent(file.name)+"&fileSize="+file.size+"&lastModifyTime="+lastModifyTime,
                        false); 
                xhr.overrideMimeType("application/octet-stream;charset=utf-8"); 
                xhr.sendAsBinary(evt.target.result); 
            };
            if(endSize < file.size){
                //处理文件发送（字节）
                startSize = endSize;
                if(paragraph > (file.size - endSize)){
                    endSize = file.size;
                }else{
                    endSize += paragraph ;
                }
                if (file.webkitSlice) {
                  //webkit浏览器
                    blob = file.webkitSlice(startSize, endSize);
                }else
                    blob = file.slice(startSize, endSize);
                reader.readAsBinaryString(blob);
            }else{
                document.getElementById('progressNumber'+i).innerHTML = "完成进度百分比" + '100%';
            }
       }
     
    //显示处理进程
    function uploadProgress(file,startSize,uploadLen,i) {
        var percentComplete = Math.round(uploadLen * 100 / file.size);
        document.getElementById('progressNumber'+i).innerHTML = "完成进度百分比"+percentComplete.toString() + '%';
        //续传
        if(uploadState == 1){
            uploadFile(file,startSize,uploadLen,i);
        }
    }
     
    /*
            暂停上传
    */
    function pauseUpload(){
        uploadState = 2;
    }

	/**
	 * 选择文件之后触发事件
	 */
	function fileSelected() {
	    fileList = document.getElementById('fileToUpload');
	    var length = fileList.files.length;
	    var frame = document.getElementById('fileFrame');
	        for(var i=0; i<length; i++){
	            file = fileList.files[i];
	            if(file){
	                var fileSize = 0;
	                if (file.size > 1024 * 1024)
	                    fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
	                else
	                    fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';
	                var nameDiv = document.createElement("div");
	                    nameDiv.setAttribute("id","fileName"+i);
	                    nameDiv.innerHTML='Name: ' + file.name;
	                var sizeDiv = document.createElement("div");
	                    sizeDiv.setAttribute("id","fileSize"+i);
	                    sizeDiv.innerHTML='fileSize: ' + fileSize;
	                var typeDiv = document.createElement("div");
	                    typeDiv.setAttribute("id","progressNumber"+i);
	                    typeDiv.innerHTML='';
	            }
	            frame.appendChild(nameDiv);
	            frame.appendChild(sizeDiv);
	            frame.appendChild(typeDiv);
	        }
	}
</script>
</body>
</html>