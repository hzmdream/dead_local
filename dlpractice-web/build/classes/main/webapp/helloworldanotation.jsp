<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>HelloWorldAnotationServlet</title>
</head>
<body>
<br/>
<br/>
<a href="./helloworldanotation?id=112&names=2222,22223,4444">测试处理get请求的方法</a>
<br/>
<br/>
<hr/>
<br/>
<form action="./helloworldanotation" method="post">
	<input type="text" name="id"/><br/><br/>
	<input type="checkbox" name="names" value="apple"/>apple
	<input type="checkbox" name="names" value="huawei"/>huawei
	<input type="checkbox" name="names" value="xiaomi"/>xiaomi<br/><br/>
	<input type="submit" value="测试处理post请求的方法"/>
</form>
<br/>
<br/>
<hr/>
</body>
</html>