$(document).ready(function() 
	{ 
		if ($("#alertSuccess").text().trim() == "") 
		 { 
		 $("#alertSuccess").hide(); 
		 } 
		 $("#alertError").hide(); 
	});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) 
	{ 
		 // Clear alerts---------------------
		 $("#alertSuccess").text(""); 
		 $("#alertSuccess").hide(); 
		 $("#alertError").text(""); 
		 $("#alertError").hide(); 
		 
		 // Form validation-------------------
		 var status = validatePaymentForm(); 
		 if (status != true) 
			 { 
				 $("#alertError").text(status); 
				 $("#alertError").show(); 
				 return; 
			 } 
		 
		 // If valid------------------------
		 var type = ($("#hidPaymentIDSave").val() == "") ? "POST" : "PUT"; 
		 
		 $.ajax( 
			 { 
				 url : "PaymentsAPI", 
				 type : type, 
				 data : $("#formPayment").serialize(), 
				 dataType : "text", 
				 complete : function(response, status) 
				 { 
					 onPaymentSaveComplete(response.responseText, status); 
				 } 
			 }
		 ); 
	});

function onPaymentSaveComplete(response, status) 
	{ 
		if (status == "success")
			{
				var resultSet = JSON.parse(response); 
				if (resultSet.status.trim() == "success") 
					{ 
						$("#alertSuccess").text("Successfully saved."); 
						$("#alertSuccess").show(); 
						$("#divPaymentGrid").html(resultSet.data); 
					} else if (resultSet.status.trim() == "error") 
					{ 
						 $("#alertError").text(resultSet.data); 
						 $("#alertError").show(); 
					}

			} else if (status == "error") 
			{ 
				$("#alertError").text("Error while saving."); 
				$("#alertError").show(); 
			} else
			{ 
				$("#alertError").text("Unknown error while saving.."); 
				$("#alertError").show(); 
			}
		
		$("#hidPaymentIDSave").val(""); 
		$("#formPayment")[0].reset();

	}

// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) 
	{ 
		 $("#hidPaymentIDSave").val($(this).data("paymentid")); 
		 $("#customerName").val($(this).closest("tr").find('td:eq(0)').text()); 
		 $("#billId").val($(this).closest("tr").find('td:eq(1)').text()); 
		 $("#cardNo").val($(this).closest("tr").find('td:eq(2)').text()); 
		 $("#cvv").val($(this).closest("tr").find('td:eq(3)').text()); 
		 $("#expiredDate").val($(this).closest("tr").find('td:eq(4)').text());
		 $("#payAmount").val($(this).closest("tr").find('td:eq(5)').text());
	}); 

//DELETE==========================================
$(document).on("click", ".btnRemove", function(event) 
	{ 
		 $.ajax( 
			 { 
				 url : "PaymentsAPI", 
				 type : "DELETE", 
				 data : "pID=" + $(this).data("paymentid"),
				 dataType : "text", 
				 complete : function(response, status) 
					 { 
					 	onPaymentDeleteComplete(response.responseText, status); 
					 } 
			 }
		 ); 
	}
);

function onPaymentDeleteComplete(response, status) 
	{ 
		if (status == "success") 
			{ 
				var resultSet = JSON.parse(response); 
				if (resultSet.status.trim() == "success") 
					{ 
						$("#alertSuccess").text("Successfully deleted."); 
						$("#alertSuccess").show(); 
						$("#divPaymentGrid").html(resultSet.data); 
					} else if (resultSet.status.trim() == "error") 
					{ 
						$("#alertError").text(resultSet.data); 
						$("#alertError").show(); 
					} 
			} else if (status == "error") 
				{ 
					$("#alertError").text("Error while deleting."); 
					$("#alertError").show(); 
				} else
					 { 
						 $("#alertError").text("Unknown error while deleting.."); 
						 $("#alertError").show(); 
					 } 
	}

// CLIENT-MODEL================================================================
function validatePaymentForm() 
	{ 
		// CUSTOMER NAME
		if ($("#customerName").val().trim() == "") 
		 { 
			return "Insert Customer Name."; 
		 } 
		
		// BILL ID
		if ($("#billId").val().trim() == "") 
		 { 
			return "Insert Bill ID."; 
		 } 
		
		// CARD NUMBER------------------------
		if ($("#cardNo").val().trim() == "") 
		 { 
			return "Insert Card Number."; 
		 }
		
		// CVV------------------------
		if ($("#cvv").val().trim() == "") 
		 { 
			return "Insert CVV."; 
		 }
		
		// EXPIRED DATE------------------------
		if ($("#expiredDate").val().trim() == "") 
		 { 
			return "Insert Expire Date."; 
		 }
		 
		// PAID AMOUNT-------------------------------
		if ($("#payAmount").val().trim() == "") 
		 { 
			return "Insert Paid Amount."; 
		 } 
		 
		// is numerical value
		var tmpPrice = $("#payAmount").val().trim(); 
		if (!$.isNumeric(tmpPrice)) 
		 { 
			return "Insert a numerical value for Paid Amount."; 
		 } 
		
		// convert to decimal price
		$("#payAmount").val(parseFloat(tmpPrice).toFixed(2)); 
		
		
		 return true; 
	}