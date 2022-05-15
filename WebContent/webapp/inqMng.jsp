<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>inqMng Management</title>
</head>
<body>
<div class="container"><div class="row"><div class="col-6">

<h1>inqMng Management V10.1</h1>

<form id="forminqMng" name="forminqMng">
 customerName:
 <input id="customerName" name="customerName" type="text" class="form-control form-control-sm">
 
 <br> accountNo:
 <input id="accountNo" name="accountNo" type="text" class="form-control form-control-sm">
 
 <br> inqTitle:
 <input id="inqTitle" name="inqTitle" type="text" class="form-control form-control-sm">
 
 <br> inqDecs:
 <input id="inqDecs" name="inqDecs" type="text" class="form-control form-control-sm">

 <br>
 <input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
 
 <input type="hidden" id="hidItemIDSave" name="hidItemIDSave" value="">

</form>

<div id="alertSuccess" class="alert alert-success"></div>
<div id="alertError" class="alert alert-danger"></div>

<br>
<div id="divinqMngGrid">

 <%
 inqMng inqMngObj = new inqMng();
 out.print(inqMngObj.readinqMng());
 %>
 
</div></div> </div> </div> 

</body>
</html>