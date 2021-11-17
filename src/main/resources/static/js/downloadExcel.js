$("#downloadStandardProduct").click(function(){
        if (confirm("수정 하시겠습니까?")) {
		/* 유효성 검사 */
		let formData = makeFormData();
		if (updateValidation(formData)) {
			$.ajax({
				url: "mapper.php?method=updateStandardProduct&seq=" + standardProductSeq,
				type: "POST",
				data: formData,
				cache: false,
				processData: false,
				contentType: false,
				dataType: "json",
				success: function(response) {
					console.log(response);
					if (response['code'] == 200) {
						alert("저장 성공했습니다.");
						//location.reload(true);
					}
				}
			})
		}
	}
 });

function download(productType) {
	let productArray = [];
	let productDataObj = {};
	//productType = 1 기준 상품
	//productType = 2 협력사 상품

	if (productType == 1) {
		for (let i = 1; i <= $('#standardProductTable tbody tr').length; i++) {
			let tempQuery = "#standardProductTbody > tr:nth-child(" + i + ")>";
			productDataObj.nStandardProductSeq = $(tempQuery + "td:nth-child(1)").text();
			productDataObj.sCategoryName = $(tempQuery + "td:nth-child(2)").text();
			productDataObj.sName = $(tempQuery + "td:nth-child(3)").text();
			productDataObj.nLowestPrice = $(tempQuery + "td:nth-child(4)").text();
			productDataObj.nMobileLowestPrice = $(tempQuery + "td:nth-child(5)").text();
			productDataObj.nAveragePrice = $(tempQuery + "td:nth-child(6)").text();
			productDataObj.nCooperationCompayCount = $(tempQuery + "td:nth-child(7)").text();
			productArray.push(productDataObj);
			productDataObj = {};
		}
	} else {
		for (let i = 1; i <= $('#cooperationProductTable tbody tr').length; i++) {
			let tempQuery = "#cooperationProductTbody > tr:nth-child(" + i + ")>";
			productDataObj.sCooperationCompanyName = $(tempQuery + "td:nth-child(1)").text();
			productDataObj.sCooperationCompanySeq = $(tempQuery + "td:nth-child(2)").text();
			productDataObj.sName = $(tempQuery + "td:nth-child(3)").text();
			productDataObj.sURL = $(tempQuery + "td:nth-child(4) > a").attr("href");
			productDataObj.nPrice = $(tempQuery + "td:nth-child(5)").text();
			productDataObj.nMobilePrice = $(tempQuery + "td:nth-child(6)").text();
			productDataObj.dtInputDate = $(tempQuery + "td:nth-child(7)").text();
			productArray.push(productDataObj);
			productDataObj = {};
		}
	}

	let jsonData = JSON.stringify(productArray);
	console.log(jsonData);
	/*$.ajax({
		url: "mapper.php?method=download",
		type: "POST",
		dataType: "json",
		data: {
			productType: productType,
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
	})*/
}