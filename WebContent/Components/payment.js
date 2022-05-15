$(document).ready(function() {
	$("#alertSuccess").hide();
	$("#alertError").hide();
});


$(document).on("click", "#btnSave", function(event) {
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	var status = validatePaymentForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}

	var type = ($("#id").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "PaymentAPI",
		type : type,
		data : $("#formPayment").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onPaymentSaveComplete(response.responseText, status);
		}
	});
});

function onPaymentSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();

			$("#payments").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}

	} else if (status == "error") {
		$("#alertError").text("An error occured.");
		$("#alertError").show();
	} else {
		$("#alertError").text("An error occured");
		$("#alertError").show();
	}

	$("#hidPaymentIDSave").val("");
	$("#formProduct")[0].reset();
}

$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#hidPaymentIDSave").val(
					$(this).closest("tr").find('#hidPaymentIDUpdate').val());
			$("#customerName").val($(this).closest("tr").find('td:eq(0)').text());
			$("#description").val($(this).closest("tr").find('td:eq(1)').text());
			$("#amount").val($(this).closest("tr").find('td:eq(2)').text());
		});


$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "PaymentAPI",
		type : "DELETE",
		data : "id=" + $(this).data("paymentid"),
		dataType : "text",
		complete : function(response, status) {
			onPaymentDeleteComplete(response.responseText, status);
		}
	});
});

function onPaymentDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") {

			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();

			$("#payments").html(resultSet.data);

		} else if (resultSet.status.trim() == "An error occured") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}

	} else if (status == "An error occured") {
		$("#alertError").text("An error occured.");
		$("#alertError").show();
	} else {
		$("#alertError").text("An error occured");
		$("#alertError").show();
	}
}


function validatePaymentForm() {
	// Name
	if ($("#customerName").val().trim() == "") {
		return "Insert Customer Name.";
	}

	// Description
	if ($("#description").val().trim() == "") {
		return "Insert Description.";
	}

	// Amount
	 var tmpAmount = $("#amount").val().trim();
	 if (!$.isNumeric(tmpAmount)) 
	 {
		 return "Insert Amount.";
	 }


	return true;
}