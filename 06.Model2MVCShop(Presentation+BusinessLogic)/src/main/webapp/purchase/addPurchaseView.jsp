<%@ page contentType="text/html; charset=EUC-KR"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- <%@ page import="com.model2.mvc.service.domain.Purchase" %>
<%@ page import="com.model2.mvc.service.domain.User" %> --%>
 
<%-- <% Purchase purchase = (Purchase)request.getAttribute("purchase"); %>
<% User user = (User)session.getAttribute("user"); %> --%>

<html>
<head>
<title>Insert title here</title>
</head>

<body>

������ ���� ���Ű� �Ǿ����ϴ�.

<table border=1>
	<tr>
		<td>��ǰ��ȣ</td>
		<%-- <td><%=purchase.getPurchaseProd().getProdNo() %></td> --%>
		<td>${purchase.purchaseProd.prodNo}</td>
		<td></td>
	</tr>
	<tr>
		<td>�����ھ��̵�</td>
		<%-- <td><%=purchase.getBuyerId().getUserId() %></td> --%>
		<td>${purchase.buyerId.userId}</td>
		<td></td>
	</tr>
	<tr>
		<td>���Ź��</td>
		<%-- <td><%if(purchase.getPaymentOption().equals("1")){%> --%>
		<td><c:choose>
			<c:when test="${purchase.paymentOption eq '1'}">���ݰ���</c:when>
			<c:otherwise>ī�����</c:otherwise>
			</c:choose>
		</td>
		<td></td>
	</tr>
	<tr>
		<td>�������̸�</td>
		<%-- <td><%= purchase.getReceiverName()%></td> --%>
		<td>${purchase.receiverName}</td>
		<td></td>
	</tr>
	<tr>
		<td>�����ڿ���ó</td>
		<%-- <td><%= purchase.getReceiverPhone()%></td> --%>
		<td>${purchase.receiverPhone}</td>
		<td></td>
	</tr>
	<tr>
		<td>�������ּ�</td>
		<%-- <td><%= purchase.getDivyAddr()%></td> --%>
		<td>${purchase.divyAddr}</td>
		<td></td>
	</tr>
		<tr>
		<td>���ſ�û����</td>
		<%-- <td><%= purchase.getDivyRequest()%></td> --%>
		<td>${purchase.divyRequest}</td>
		<td></td>
	</tr>
	<tr>
		<td>����������</td>
		<%-- <td><%= purchase.getDivyDate()%></td> --%>
		<td>${purchase.divyDate}</td>
		<td></td>
	</tr>
</table>
</form>

</body>
</html>