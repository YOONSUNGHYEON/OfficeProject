$("#downloadStandardProduct").click(function() {
	let productArray = [];
	let productDataObj = {};
	for (let i = 1; i <= $('#standardProductTable tbody tr').length; i++) {
		let tempQuery = "#standardProductTbody > tr:nth-child(" + i + ")>";
		productDataObj.sCategoryName = $(tempQuery + "td:nth-child(1)").text();
		productDataObj.sName = $(tempQuery + "td:nth-child(2)").text();
		productDataObj.nLowestPrice = $(tempQuery + "td:nth-child(5)").text();
		productDataObj.nLowestPrice = $(tempQuery + "td:nth-child(6)").text();
		productDataObj.nMobileLowestPrice = $(tempQuery + "td:nth-child(7)").text();
		productDataObj.nAveragePrice = $(tempQuery + "td:nth-child(8)").text();
		productDataObj.nCooperationCompayCount = $(tempQuery + "td:nth-child(9)").text();
		productArray.push(productDataObj);
		productDataObj = {};
	}
	let jsonData = JSON.stringify(productArray);
	download(jsonData , "mapper.php?method=downloadStandardProduct");
	console.log(jsonData);
});
$("#downloadCooperationProduct").click(function() {
	let productArray = [];
	let productDataObj = {};
	for (let i = 1; i <= $('#standardProductTable tbody tr').length; i++) {
		let tempQuery = "#cooperationProductTbody > tr:nth-child(" + i + ")>";
		productDataObj.sCooperationCompanyName = $(tempQuery + "td:nth-child(2)").text();
		productDataObj.sCategoryName = $(tempQuery + "td:nth-child(3)").text();
		productDataObj.sName = $(tempQuery + "td:nth-child(4)").text();
		productDataObj.sURL = $(tempQuery + "td:nth-child(6) > a").attr("href");
		productDataObj.nPrice = $(tempQuery + "td:nth-child(7)").text();
		productDataObj.nMobilePrice = $(tempQuery + "td:nth-child(8)").text();
		productDataObj.dtInputDate = $(tempQuery + "td:nth-child(9)").text();
		productArray.push(productDataObj);
		productDataObj = {};
	}
	let jsonData = JSON.stringify(productArray);
	download(jsonData , "http://192.168.0.53/DanawaOfficeProject/mapper.php?method=downloadCooperationProduct");
	console.log(jsonData);
});
function download(jsonData, url) {
	$.ajax({
		url: url,
		type: "POST",
		dataType: "json",
		data: {
			productArrObj: jsonData
		}, beforeSend: function() {
			$("#progressStatus").show();
		}, complete: function() {
			$("#progressStatus").hide();
		}, success: function(downloadResponse) {
			if (downloadResponse['code'] == 200) {
				alert("다운로드 성공했습니다.\n(저장 경로 : " + downloadResponse['path'] + " )");
			} else if (downloadResponse['code'] == 400) {
				alert("다운로드 실패했습니다.\n(실패 원인 : " + downloadResponse['error'] + ")");
			}
		}
	})
}