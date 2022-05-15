<%@page import="com.Payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payments</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.4.1.min.js"></script>
<script src="Components/product.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Payments</h1>

				<form id="formProduct" name="formProduct" method="post" action="Product.jsp">


					Customer name: <input id="customerName" name="customerName" type="text"
						class="form-control form-control-sm"> 
						
						<br>Description: <input id="description" name="description" type="text"
						class="form-control form-control-sm"> 
						
						<br> Amount: <input id="amount" name="amount" type="text"
						class="form-control form-control-sm"> 
						
						<br> <input
						id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidProjectIDSave" name="hidProjectIDSave" value="">
				</form>

				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>

				<br>
				<div id="divProjectGrid">
					<%
						Payment payment = new Payment();
						out.print(payment.getAllPayments());
					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
