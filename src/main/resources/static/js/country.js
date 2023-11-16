$(document).ready(function() {
	$("#detailContains").css("display", "none");

	// 処理区分は新規の場合
	$("#selCreate").on('click', function() {
		// clear all input
		$(':input', '#frmDetail')
			.not(':button, :submit, :reset, :hidden')
			.val('');
		// show the detailContains
		$("#detailContains").css("display", "block");
		// hide the queryContainer
		$("#queryContainer").css("display", "none");
		// show the addBtn
		$("#addBtn").css("display", "block");
		// hide the updateBtn
		$("#updateBtn").css("display", "none");
		// hide the deleteBtn
		$("#deleteBtn").css("display", "none");
		// show the inputCountryCd
		$("#inputCountryCd").css("display", "block");
		// show the mstcountrycd
		$("#mstcountrycd").css("display", "block");
		$("#mstcountrycd").val('');
		$("#mstcountrynanme").val('');
	});

	// 処理区分は変更の場合
	$("#selUpdate").on('click', function() {
		// show the queryContainer
		$("#queryContainer").css("display", "block");
		// hide the detailContains
		$("#detailContains").css("display", "none");
		// hide the addBtn
		$("#addBtn").css("display", "none");
		// show the updateBtn
		$("#updateBtn").css("display", "block");
		// hide the deleteBtn
		$("#deleteBtn").css("display", "none");
		$("#queryInput").val('');
		$("#mstcountrycd").val('');
		$("#mstcountrynanme").val('');
		$("#queryInput").prop("disabled", false);
		// set the form action for update
		$("#frmDetail").attr("action", "/UpdateCountry");
	});

	// 処理区分は削除の場合
	$("#selDelete").on('click', function() {
		// show the queryContainer
		$("#queryContainer").css("display", "block");
		// hide the detailContains
		$("#detailContains").css("display", "none");
		// hide the addBtn
		$("#addBtn").css("display", "none");
		// hide the updateBtn
		$("#updateBtn").css("display", "none");
		// show the deleteBtn
		$("#deleteBtn").css("display", "block");
		$("#queryInput").val('');
		$("#mstcountrycd").val('');
		$("#mstcountrynanme").val('');
		$("#queryInput").prop("disabled", false);
		// set the form action for update
		$("#frmDetail").attr("action", "/UpdateCountry");
	});

	// 検索の場合
	$("#queryBtn").on('click', function() {
		// use ajax to post data to controller
		// recived the data from controller with json
		// show the data in the detailContains
		$("#queryInput").prop("disabled", false);
		$.ajax({
			type: "POST",
			url: "/country/getCountry",        //  <- controller function name
			data: $("#frmSearch").serialize(),
			dataType: 'json',
			success: function(data) {
				$("#detailContains").css("display", "block");
				$("#inputCountryCd").css("display", "none");
				$("#mstcountrycd").css("display", "none");
				$("#queryInput").prop("disabled", true);
				// show the data in the detailContains
				$("#mstcountrycd").val(data.mstcountrycd);
				$("#mstcountrynanme").val(data.mstcountrynanme);
			},
			error: function(e) {
				alert(e.responseJSON.message);
			}
		});
	});


	// 新規の場合
	$("#addBtn").on('click', function() {
		$.ajax({
			type: "POST",
			url: "/country/add",
			data: $("#frmDetail").serialize(),
			dataType: 'json',
			success: function() {
				$("#mstcountrycd").val('');
				$("#mstcountrynanme").val('');
				alert('success');
			},
			error: function(e) {
				alert(e.responseJSON.message);
			}
		});
	});

	// 変更の場合
	$("#updateBtn").on('click', function() {
		$.ajax({
			type: "POST",
			url: "/country/update",
			data: $("#frmDetail").serialize(),
			dataType: 'json',
			success: function() {
				alert('success');
			},
			error: function(e) {
				alert(e.responseJSON.message);
			}
		});
	});

	// 削除の場合
	$("#deleteBtn").on('click', function() {
		$.ajax({
			type: "POST",
			url: "/country/delete",
			data: $("#frmDetail").serialize(),
			dataType: 'json',
			success: function() {
				// hide the detailContains
				$("#detailContains").css("display", "none");
				alert('successqq');
			},
			error: function(e) {
				alert(e.responseJSON.message);
			}
		});
	});

	// 戻るの場合
	$("#returnBtn").on('click', function() {
		// hide the detailContains
		$("#queryInput").prop("disabled", false);
		$("#detailContains").css("display", "none");
	});

	// クリアの場合
	$("#clearBtn").on('click', function() {
		// show the queryContainer
		// $("#queryContainer").css("display", "block");
		// hide the detailContains
		$("#queryInput").prop("disabled", false);
		$("#queryInput").val('');
		$("#detailContains").css("display", "none");

	});


});