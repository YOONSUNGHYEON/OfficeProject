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

let sortOrder = "";

function searchProduct(sortSeq, orderSeq) {
	getStandardProducts(6, 2);
	getCooperationProducts(6, 2);
}

function link() {
	let standardProductSeq = document.querySelector('input[name="standardProduct"]:checked').value;
	let cooperationProductSeqArr = getCheckboxValue("cooperationProductSeq");
	let cooperationCompanySeqArr = getCheckboxValue("cooperationCompanySeq");
	$.ajax({
		url: "/cooperationProducts/link",
		type: "POST",
		data: JSON.stringify({
			"standardProductSeq": standardProductSeq,
			"cooperationProductSeqArr": cooperationProductSeqArr,
			"cooperationCompanySeqArr":cooperationCompanySeqArr
		}),
		contentType: 'application/json',
		dataType: "json",
		success: function(response) {
			location.reload(true);
		}
	})
}

function unLink() {
	console.log("unLink");
}
function getCheckboxValue(name) {
	// 선택된 목록 가져오기
	let arr = [];
	const query = 'input[name="' + name + '"]';
	const selectedEls = document.querySelectorAll(query);
	// 선택된 목록에서 value 찾기
	let result = '';
	selectedEls.forEach((el) => {
		arr.push(el.value);
	});

	// 출력
	console.log(arr);
	return arr;
}
function getStandardProducts(sortSeq, orderSeq) {
	let categorySeq = document.getElementById('categorySelect').value;
	let linkOption = document.querySelector('input[name="link"]:checked').value;
	let page = 1;
	if (sortSeq + ':' + orderSeq == "6:2" || sortOrder == "6:2") {
		sortOrder = sortSeq + ":" + orderSeq;
	} else {
		sortOrder = makesortOrderQuery(sortOrder, sortSeq, orderSeq);
	}
	$.ajax({
		type: 'GET',
		url: '/standardProducts?&page=' + page + '&linkOption=' + linkOption + '&categorySeq=' + categorySeq + '&sortOrder=' + sortOrder,
		dataType: "json",
		success: function(standardProductListResponse) {

			let standardProductThead = "";
			standardProductThead += '<th>카테고리</th>';
			standardProductThead += '<th><a onClick="sort(1, 1);" href="javascript:void(0);">상품 명</a></th>';
			standardProductThead += '<th>ⓘ</th>';
			standardProductThead += '<th>ⓤ</th>';
			standardProductThead += '<th><a onClick="sort(1, 1);" href="javascript:void(0);">통합 최저가</a></th>';
			standardProductThead += '<th><a onClick="sort(1, 1);" href="javascript:void(0);">PC 최저가</a></th>';
			standardProductThead += '<th><a onClick="sort(1, 1);" href="javascript:void(0);">모바일 최저가</a></th>';
			standardProductThead += '<th>평균가</th>';
			standardProductThead += '<th><a onClick="sort(1, 1);" href="javascript:void(0);">업체</a></th>';
			standardProductThead += '<th>   </th>';

			$("#standardProductThead").html(standardProductThead);

			let standardProductTable = "";
			let standardProductList = standardProductListResponse['page']['content'];
			console.log(standardProductList);
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
			let pagination = makePagination(standardProductListResponse['page']);
			$("#standardPagination").html(pagination);

		}
	});
}

function getCooperationProducts(sortSeq, orderSeq) {
	let categorySeq = document.getElementById('categorySelect').value;
	let linkOption = document.querySelector('input[name="link"]:checked').value;
	let page = 1;
	if (sortSeq + ':' + orderSeq == "6:2" || sortOrder == "6:2") {
		sortOrder = sortSeq + ":" + orderSeq;
	} else {
		sortOrder = makesortOrderQuery(sortOrder, sortSeq, orderSeq);
	}
	$.ajax({
		type: 'GET',
		url: '/cooperationProducts/unlink?&page=' + page + '&linkOption=' + linkOption + '&categorySeq=' + categorySeq + '&sortOrder=' + sortOrder,
		dataType: "json",
		success: function(response) {
			console.log(response);
			let cooperationProductThead = "";
			cooperationProductThead += '<th>협력사</th>';
			cooperationProductThead += '<th>카테고리</th>';
			cooperationProductThead += '<th><a onClick="findCooperationProductList(1, 1 ,1)" href="#a">상품명</a></th>';
			cooperationProductThead += '<td>ⓘ</td>';
			cooperationProductThead += '<td>ⓤ</td>';
			cooperationProductThead += '<th>PC가격</th>';
			cooperationProductThead += '<th>모바일 가격</th>';
			cooperationProductThead += '<th><a onClick="findCooperationProductList(1, 2 ,1)" href="#a">최초 등록</a></th>';



			let cooperationProductTable = "";
			$.each(response['page']['content'], function(index, cooperationProduct) {
				cooperationProductTable += '<tr style="cursor:pointer;">';
				cooperationProductTable += '<td><input type="checkbox" name="cooperationProductSeq" value="' + cooperationProduct['cooperationProductSeq'] + '"></td>';
				cooperationProductTable += '<td> <input type="hidden" id="cooperationCompanySeq" name="cooperationCompanySeq" value="' + cooperationProduct["cooperationCompanySeq"] + '">' + cooperationProduct["cooperationCompanySeq"] + '</td>';
				cooperationProductTable += '<td>' + cooperationProduct['category']['name'] + '</td>';
				cooperationProductTable += '<td>' + cooperationProduct['name'] + '</td>';
				cooperationProductTable += '<td><a target="_blank" href="' + cooperationProduct['name'] + '">ⓘ</td>'
				cooperationProductTable += '<td><a target="_blank" href="' + cooperationProduct['name'] + '">ⓤ</a></td>';
				cooperationProductTable += '<td>' + cooperationProduct['price'] + '</td>';
				cooperationProductTable += '<td>' + cooperationProduct['mobilePrice'] + '</td>';
				cooperationProductTable += '<td>' + cooperationProduct['inputDate'] + '</td>';
				cooperationProductTable += '</tr>';
			});

			$("#cooperationProductTbody").html(cooperationProductTable);
			let paginationResponse = response['page'];
			let pagination = makePagination(paginationResponse);
			$("#cooperationPagination").html(pagination);
		}

	})

}

function makePagination(paginationResponse) {
	let pagination = "";
	//let preArrow = Number(tableData['aPageData']['nCurrentPage']) - Number(tableData['aPageData']['nBlockPage']);
	//let nextArrow = Number(tableData['aPageData']['nCurrentPage']) + Number(tableData['aPageData']['nBlockPage']);
	pagination += '<a class="arrow" onClick="" href="#a"><<</a>';
	pagination += '<a class="arrow" onClick="" href="javascript:void(0);">&lt;</a>';
	for (let i = 1; i <= paginationResponse['totalPages']; i++) {
		if (paginationResponse['pageNumber'] + 1 == i) {
			pagination += '<a class="active" onClick=""  href="javascript:void(0);">' + i + '</a>';
		} else {
			pagination += '<a onClick=""  href="javascript:void(0);">' + i + '</a>';
		}
	}
	pagination += '<a class="arrow" onClick="" href="#a">></a>';
	pagination += '<a class="arrow" onClick="" href="javascript:void(0);">>></a>';
	return pagination;
}

function makesortOrderQuery(priorityQuery, sortSeq, orderSeq) {
	const priorityList = priorityQuery.split(",");
	let newPriorityQuery = "";
	let ok = false;

	for (let i = 0; i < priorityList.length; i++) {
		const sortOption = priorityList[i].split(":");
		if (sortOption[0] == sortSeq) {
			ok = true;
			if (sortOption[1] == 1) {
				newPriorityQuery += sortSeq + ':' + 2;
			} else {
				newPriorityQuery += sortSeq + ':' + 1;
			}
		} else {
			newPriorityQuery += priorityList[i];
		}
		newPriorityQuery += ',';
	}
	if (ok == false) {
		newPriorityQuery += sortSeq + ':' + orderSeq;
	} else {
		newPriorityQuery = newPriorityQuery.substring(0, newPriorityQuery.length - 1);
	}
	return newPriorityQuery;
}
