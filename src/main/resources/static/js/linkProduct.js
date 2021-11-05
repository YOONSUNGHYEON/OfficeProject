window.onload = function() {
	findCategoryList();
}

/*카테고리 목록 가져오기*/
function findCategoryList() {
	$.ajax({
		type: 'GET',
		url: '/categorys',
		dataType: "json",
		success: function(categoryListResponse) {
			if (categoryListResponse['code'] == '200') {
				categorySelect = makeSelect(categoryListResponse['list']);
				document.getElementById('categorySelect').innerHTML += categorySelect;
			} else if (cooperationCompanyListResponse['code'] == '400') {
				alert("");
			} else if (cooperationCompanyListResponse['code'] == '500') {
				alert("서버 상에 문제가 있어 카테고리를 불러오는데 실패 했습니다.");
			}
		}
	});
}

/* option 만들기 */
function makeSelect(dataList) {
	let dataSelect = '';
	$.each(dataList, function(index, data) {
		dataSelect += '<option value="';
		dataSelect += data['seq'] + '">';
		dataSelect += data['name'] + '</option>';
	});
	return dataSelect;
}

function searchStandardProducts() {
	let categorySeq = document.getElementById('categorySelect').value;
	let linkRadio = document.querySelector('input[name="link"]:checked').value;
	let page = 1;
	$.ajax({
		type: 'GET',
		url: '/standardProducts?page=' + page + '&categorySeq=' + categorySeq + '&linkOption=' + linkRadio,
		dataType: "json",
		success: function(standardProductListResponse) {
			console.log(standardProductListResponse);
			let standardProductThead = "";
			standardProductThead += '<th>카테고리</th>';
			standardProductThead += '<th>상품 명</th>';
			standardProductThead += '<th>ⓘ</th>';
			standardProductThead += '<th>ⓤ</th>';
			standardProductThead += '<th>통합 최저가</th>';
			standardProductThead += '<th>PC 최저가</th>';
			standardProductThead += '<th>모바일 최저가</th>';
			standardProductThead += '<th>평균가</th>';
			standardProductThead += '<th>업체</th>';
			standardProductThead += '<th>   </th>';

			$("#standardProductThead").html(standardProductThead);

			let standardProductTable = "";
			let standardProductList = standardProductListResponse['list']['content'];

			$.each(standardProductList, function(index, standardProduct) {
				standardProductTable += '<tr style="cursor:pointer;">';
				standardProductTable += '<td>' + standardProduct['categorySeq'] + '</td>';
				standardProductTable += '<td>' + standardProduct['name'] + '</td>';
				standardProductTable += '<td>ⓘ</td>';
				standardProductTable += '<td>ⓤ</td>';
				standardProductTable += '<td>' + standardProduct['lowestPrice'] + '</td>';
				standardProductTable += '<td>' + standardProduct['lowestPrice'] + '</td>';
				standardProductTable += '<td>' + standardProduct['mobileLowestPrice'] + '</td>';
				standardProductTable += '<td>' + standardProduct['averagePrice'] + '</td>';
				standardProductTable += '<td>' + standardProduct['cooperationComanyCount'] + '</td>';
				standardProductTable += '<td>' + '<input type="radio" name="standardProduct" value="' + standardProduct['seq'] + '"></td>';
				cooperationProductTable += '</tr>';
			});
			$("#standardProductTbody").html(standardProductTable);
			let paginationResponse = standardProductListResponse['list'];
			let pagination = "";
			//let preArrow = Number(tableData['aPageData']['nCurrentPage']) - Number(tableData['aPageData']['nBlockPage']);
			//let nextArrow = Number(tableData['aPageData']['nCurrentPage']) + Number(tableData['aPageData']['nBlockPage']);
			pagination += '<a class="arrow" onClick="" href="#a"><<</a>';
			pagination += '<a class="arrow" onClick="" href="javascript:void(0);">&lt;</a>';
			for (let i = 1; i <=paginationResponse['totalPages']; i++) {
				if (paginationResponse['pageNumber']+1 == i) {
					pagination += '<a class="active" onClick=""  href="javascript:void(0);">' + i + '</a>';
				} else {
					pagination += '<a onClick=""  href="javascript:void(0);">' + i + '</a>';
				}
			}
			$("#standardPagination").html(pagination);
		}
	});
}

function search() {
	searchStandardProducts();

}

