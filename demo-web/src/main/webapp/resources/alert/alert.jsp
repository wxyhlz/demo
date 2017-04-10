<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false"%>
<%@include file="/resources/common/common.jsp"%>
<!doctype html>
<html>
 <head>
  <title>Alert</title>
 </head>
 <body>
 	<div id="seconds" style="font-size:22px;color:red;">3</div>
 </body>
 
 <script>
 	var second = 2;
	var intervalid = setInterval("clock()", 1000);
	function clock() {
		if (second == 0) {
			window.location.href = "https://www.baidu.com/"; 
			clearInterval(intervalid);
		}
		$("#seconds").text(second--);
	}
 </script>
</html>