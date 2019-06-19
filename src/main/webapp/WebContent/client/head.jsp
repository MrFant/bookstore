<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="domain.User" %>
<script type="text/javascript">
//退出确认框
function confirm_logout() {   
    var msg = "您确定要退出登录吗？";   
    return confirm(msg) === true;
} 
</script>
<div id="divhead">
	<table cellspacing="0" class="headtable">
		<tr>
			<td>
				<a href="${pageContext.request.contextPath }/index.jsp">
					<img src="${pageContext.request.contextPath}/WebContent/client/images/logo.png" width="200" height="60" border="0" />
				</a>
			</td>
			<td style="text-align:right">
				<img src="${pageContext.request.contextPath}/WebContent/client/images/cart.gif" width="26" height="23" style="margin-bottom:-4px" />
				   &nbsp;
				  <a href="${pageContext.request.contextPath}/WebContent/client/cart.jsp">购物车</a>
				| <a href="#">帮助中心</a> 
				| <a href="${pageContext.request.contextPath}/myAccount">我的帐户</a>



				<% 
				User user = (User) request.getSession().getAttribute("user");
				if(user == null){
				%>
				| <a href="${pageContext.request.contextPath}/WebContent/client/register.jsp">新用户注册</a>
				<% 	
				}else{
				%>
				| <a href="${pageContext.request.contextPath}/logout" onclick="return confirm_logout()">用户退出</a>
				<br><br><br>欢迎您： ${user.username}
				<% 	
				}
				%>
				<%--未分离视图和逻辑代码--%>



			</td>		
		</tr>
	</table>
</div>