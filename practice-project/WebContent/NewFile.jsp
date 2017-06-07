<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script>
function myFunction() {
	var a = 34234;
	$("#text1").val(a);
}

$( document ).ready(function() {

});
</script>
<title>Insert title here</title>
</head>
<body>
<input type="text" id="text1" value="ss">
<button type="button" id="btn" onclick="myFunction()">Click Me!</button>
</body>
</html>