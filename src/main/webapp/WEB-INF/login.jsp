<%@ page contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<head>
</head>
<body style="font-size:30px">
	<form action="login.do" method="post">
		<fieldset>
			<legend>登录</legend>
		用户名:<input name="username"/>
		
		<span style="color:red;">${login_failed }</span><br/>
		密码:<input type="password" name="pwd"/><br/>
		
		验证码:<input name="number"/>
		<span style="color:red;">${number_error}</span><br/>
		
		<img id="img1" src="checkcode.do"/>
		
		<a href="javascript:;" onclick="document.getElementById('img1').src='checkcode.do?'+Math.random();">看不清，换一个</a><br/>
		<input type="submit" value="确定"/>
		
		
		
		</fieldset>
	</form>
</body>
</html>