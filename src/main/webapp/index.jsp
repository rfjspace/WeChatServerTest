<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WeChatWebTest</title>
</head>
<body>
	<form action="/WeChatServerTest/WeChatMainServlet" method="post">
		<label for="wcinput">微信客户端：</label> <input id="wcinput"
			name="wcmessages" type="text"> <br> <input type="submit"
			value="发送"></input>
	</form>
	<br>
	<form action="/WeChatServerTest/WeChatMainServlet" method="get">
		<label for="signature">signature：</label> <input id="signature"
			name="signature" type="text"> <br> <label
			for="timestamp">timestamp：</label> <input id="timestamp"
			name="timestamp" type="text"> <br> <label for="nonce">nonce：</label>
		<input id="nonce" name="nonce" type="text"> <br> <label
			for="echostr">echostr：</label> <input id="echostr" name="echostr"
			type="text"> <br> <input type="submit" value="发送"></input>
	</form>
</body>
</html>