 <%
 
 response.setHeader("Cache-Control","no-store");
 response.setHeader("Pragma","no-cache");
 %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>    
<%@ include file="./header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!--Start of Tawk.to Script-->
<!--Start of Tawk.to Script-->
<script type="text/javascript">
var Tawk_API=Tawk_API||{}, Tawk_LoadStart=new Date();
(function(){
var s1=document.createElement("script"),s0=document.getElementsByTagName("script")[0];
s1.async=true;
s1.src='https://embed.tawk.to/60507c62f7ce1827093088f0/1f0t6bohj';
s1.charset='UTF-8';
s1.setAttribute('crossorigin','*');
s0.parentNode.insertBefore(s1,s0);
})();
</script>
<!--End of Tawk.to Script-->
<!--End of Tawk.to Script-->
</head>
<body>
<div id="bodywrap">
	<div align="right">
		<tiles:insertAttribute name="top"/>
	</div>
	<div id="logo" align="center">
		<a href="main.do"><img src="resources/imgs/brisbane.png" width="200"/></a>
	</div>
	<div align="center">
		<tiles:insertAttribute name="side"/>
	</div>
	<div id="page" align="center">
		<tiles:insertAttribute name="content"/>
	</div>
	<div align="center">
		<tiles:insertAttribute name="footer"/>
	</div>

</div>

</body>

</html>