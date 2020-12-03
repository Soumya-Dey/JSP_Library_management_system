<%-- 
    Document   : SuccessGreeting
    Created on : Aug 4, 2013, 3:29:43 PM
    Author     : arya
--%>
<%@page import="soumya.lms.model.CheckOut"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Library Management System: Success Greeting Page</title>
    </head>
   
 <body bgcolor='white'>
<!-- Page Heading -->
<table border='1' cellpadding='5' cellspacing='0' width='400'>
<tr bgcolor='#CCCCFF' align='center' valign='center' height='20'>
<td><h3>Library Management System: Success Greeting Page</h3></td>
</tr>
</table>
<%	List<CheckOut> checkout = (ArrayList<CheckOut>)request.getAttribute("checkedOutItems");
 %>

<p> You have successfully logged in!!!</p>

<u>Books currently borrowed by you:</u><br/><br/>	
<table width='100%' border='1'>
<thead align='center'>
<th>Transaction Id</th>
<th>User Name</th>
<th>Book Id</th>
<th>Return Date</th>
</thead>
<%
for(CheckOut checkedOutItem: checkout) {
%>	
	<tr align='center'>
		<td><%=checkedOutItem.getTransactionId()%></td>
		<td><%=checkedOutItem.getUserName()%></td>
		<td><%=checkedOutItem.getBookId()%></td>
                <td><%=checkedOutItem.getReturnDate()%></td>
	</tr>
<%
}
%>
</table>
<br/><br/><br/><br/>
<center>Click <a href='index.jsp'>here</a> to logout</center><br/>

</body>
</html>

