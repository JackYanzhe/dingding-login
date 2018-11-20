<?xml version="1.0" encoding="utf-8" ?>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Insert title here</title>

<script type="text/javascript" src="js/jquery-3.2.1.js"></script>
 <script type="text/javascript">
        //在此拿到jsAPI权限验证配置所需要的信息
        var _config = <%= dingding.demo.AuthHelper.getConfig(request) %>;
    </script>
   <script type="text/javascript" src="http://g.alicdn.com/dingding/dingtalk-pc-api/2.3.1/index.js">
  
  
    <script type="text/javascript" >
		    DingTalkPC.config({
		        agentId : _config.agentid,
		        corpId : _config.corpId,
		        timeStamp : _config.timeStamp,
		        nonceStr : _config.nonceStr,
		        signature : _config.signature,
		        jsApiList : [ 'runtime.info', 'biz.contact.choose',
		            'device.notification.confirm', 'device.notification.alert',
		            'device.notification.prompt', 'biz.ding.post',
		            'biz.util.openLink' ]
		    });
		    DingTalkPC.ready(function() {
		    	
		    	    DingTalkPC.runtime.permission.requestAuthCode({
		    	        corpId : _config.corpId,
		    	        onSuccess : function(info) {
		    				alert('authcode: ' + info.code);
		    	            $.ajax({
		    	                url : 'userinfo?code=' + info.code + '&corpid='
		    	                + _config.corpId,
		    	                type : 'GET',
		    	                success : function(data, status, xhr) {
		    	                    
		    	                    var info = JSON.parse(data);
		    	                    document.getElementById("userName").innerHTML = info.name;
		    	                    document.getElementById("userId").innerHTML = info.userid;
		    	                    document.getElementById("mobile").innerHTML = info.mobile;

		    	                    // 图片
		    	                    if(info.avatar.length != 0){
		    	                        var img = document.getElementById("userImg");
		    	                        img.src = info.avatar;
		    	                        img.height = '100';
		    	                        img.width = '100';
		    	                    }

		    	                },
		    	                error : function(xhr, errorType, error) {
		    	                    logger.e("yinyien:" + _config.corpId);
		    	                    alert(errorType + ', ' + error);
		    	                }
		    	            });

		    	        },
		    	        onFail : function(err) {
		    	            alert('fail: ' + JSON.stringify(err));
		    	        }
		    	    });
		    	});
    
       
    </script>

</head>
<body>
   <br /><br /><br />
   <a href="${pageContext.request.contextPath}/dingUser/login">点击登录</a>
   <br /><br /><br /><br /><br />
   <a href="${pageContext.request.contextPath}/dingUser/login2">点击登录到jeesite</a>
   <div align="center">
		<img id ="userImg" alt="头像" src="./nav/default.png">
	</div>
	<div align="center">
		<span>UserName:</span>
		<div id="userName" style="display:inline-block"></div>
	</div >
	<div align="center">
		<span>UserId:</span>
		<div id="userId" style="display:inline-block"></div>
	</div>
   <br>
<ul>
    <li>
        <div class="icon"><img src="list/num11.png" style="width: 25px; height: 25px"></div>
        <div class="text">企业接入指南</div>
    </li>
    <!-- <li>
        <div class="icon"><img src="list/heart2.png"></div>
        <div class="text">企业授权</div>
    </li>
    <li>
        <div class="icon"><img src="list/heart3.png"></div>
        <div class="text">企业解授权</div>
    </li> -->
    <li>
        <div class="icon"><img src="list/num2.png" style="width: 25px; height: 25px"></div>
        <div class="text">使用JSAPI</div>
    </li>
    <li>
        <div class="icon"><img src="list/num3.png" style="width: 25px; height: 25px"></div>
        <div class="text">List展示（当前仅支持Android）</div>
    </li>
    <li>
        <div class="icon"><img src="list/num4.png" style="width: 25px; height: 25px"></div>
        <div class="text">侧拉展现（当前仅支持Android）</div>
    </li>
    <li>
        <div class="icon"><img src="list/num5.png" style="width: 25px; height: 25px"></div>
        <div class="text">Tab页面（当前仅支持Android）</div>
    </li>
    <li>
        <div class="icon"><img src="list/num6.png" style="width: 25px; height: 25px"></div>
        <div class="text">企业通讯录</div>
    </li>
</ul>
<script type="text/javascript">
    window.addEventListener('load', function() {
        setTimeout(function(){
        }, 500);
    });

    function openLink(url){
        dd.biz.util.openLink({
            url:url,
            onSuccess : function(result) {
            },
            onFail : function(err) {
                alert(JSON.stringify(err));
            }
        });
    }

    var items = document.querySelectorAll('li');
    items[0].addEventListener('click',function(){
        openLink('http://ddtalk.github.io/dingTalkDoc/#企业接入指南');
    });
    items[1].addEventListener('click',function(){
        window.location='./nav/1.html';
    });
    items[2].addEventListener('click', function(){
        window.location = './list/list.html';
    });

    items[3].addEventListener('click',function(){
        window.location='./drawer/index.html';
    });
    items[4].addEventListener('click',function(){
        window.location='./tab/index.html';
    });
    items[5].addEventListener('click',function(){
        window.location='./contacts.jsp?corpid='+_config.corpId;
    });

</script>
   
  
</body>
</html>