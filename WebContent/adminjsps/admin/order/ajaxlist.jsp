<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>订单列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
	* {
		font-size: 11pt;
	}
	div {
		border: solid 2px rgb(78,78,78);
		width: 75px;
		height: 75px;
		text-align: center;
	}
	li {
		margin: 10px;
	}
</style>
<script type="text/javascript">
	function showDetail(oid){
		//创建异步对象
		var bu=document.getElementById("bu"+oid);
		var tab=document.getElementById("tab"+oid);
		if(bu.value=="订单详情"){
			var xhr=createXMLHttpRequest();
			//设置监听
			xhr.onreadystatechange=function(){
				if(xhr.readyState==4){
					if(xhr.status==200){
						var data=xhr.responseText;
						var json= eval("("+data+")");
						for(var i=0;i<json.length;i++){
							//tab.innerHTML += "<tr><td <img scr='${ pageContext.request.contextPath}/"+json[i].book.image+"' width='65' height='70'/></td><td>"+json[i].book.bname+"</td><td>"+json[i].book.price+"</td><td>"+json[i].book.author+"</td><td>"+json[i].count+"</td><td>"+json[i].subtotal+"</td></tr>"
							tab.innerHTML += "<tr><td><img src='${pageContext.request.contextPath}/"+json[i].book.image+"' width='65' height='70'></td><td>"+json[i].book.bname+"</td><td>"+json[i].book.price+"</td><td>"+json[i].book.author+"</td><td>"+json[i].count+"</td><td>"+json[i].subtotal+"</td></tr>";
						}
					}
					
				}
			}
		//	xhr.open("GET","${ pageContext.request.contextPath}/adminOrderServlet?method=showDetail&oid=${ order.oid }",true);
		//打开连接	
		xhr.open("GET","${ pageContext.request.contextPath}/adminOrderServlet?method=showDetail&oid="+oid,true);
		//发送数据
		xhr.send(null);
		bu.value="关闭";
		}else{
			tab.innerHTML="";
			bu.value="订单详情";
		}
		
	};
	function createXMLHttpRequest() {
		var xmlHttp;
		try { // Firefox, Opera 8.0+, Safari
			xmlHttp = new XMLHttpRequest();
		} catch (e) {
			try {// Internet Explorer
				xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (e) {
				try {
					xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (e) {
				}
			}
		}

		return xmlHttp;
	}	

</script>
  </head>
  
  <body style="background: rgb(254,238,189);">
<h1>我的订单</h1>

<table border="1" width="100%" cellspacing="0" background="black">
<c:forEach items="${list }" var="order">
	<tr bgcolor="rgb(78,78,78)" bordercolor="rgb(78,78,78)" style="color: white;">
		<td colspan="6">
			订单：${order.oid }　成交时间：${order.ordertime }　金额：<font color="red"><b>${order.total }</b></font>
			<c:if test="${order.state==1 }">未付款</c:if>
			<c:if test="${order.state==2 }">已付款,<a href="${ pageContext.request.contextPath }/adminOrderServlet?method=updateState&oid=${order.oid }">发货</a></c:if>
			<c:if test="${order.state==3 }">未确认收货</c:if>
			<c:if test="${order.state==4 }">订单结束</c:if>
			<input id="bu${order.oid }" type="button" value="订单详情" onclick="showDetail('${order.oid }')"/>	
	</tr>
	<tr>
		<td colspan="6" >
			<table id="tab${order.oid }" border="1" width="100%">
			</table>
		</td>
		
	</tr>
 </c:forEach>
 
</table>
  </body>
</html>
