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


/* 링크 생성 클릭 -> 링크 생성 */
function link() {
	let standardProductSeq = document.querySelector('input[name="standardProduct"]:checked').value;
	let cooperationProductIDArr = getCheckboxValue("cooperationProductSeq");
	$.ajax({
		url: "/cooperationProducts/link",
		type: "POST",
		data: JSON.stringify({
			"standardProductSeq": standardProductSeq,
			"cooperationProductID": cooperationProductIDArr,
		}),
		contentType: 'application/json',
		dataType: "json",
		success: function(response) {
			location.reload(true);
		}
	})
}
/* 링크 해제 클릭 -> 링크 해제 */
function unLink() {
	let standardProductSeq = document.querySelector('input[name="standardProduct"]:checked').value;
	let cooperationProductIDArr = getCheckboxValue("cooperationProductSeq");
	$.ajax({
		url: "/cooperationProducts/unlink",
		type: "POST",
		data: JSON.stringify({
			"standardProductSeq": standardProductSeq,
			"cooperationProductID": cooperationProductIDArr,
		}),
		contentType: 'application/json',
		dataType: "json",
		success: function(response) {
			location.reload(true);
		}


	})
}

/* 선택된 협력 상품 checkbox의 (협력사 seq, 협력 상품 seq) json  배열 생성 후 반환 */
function getCheckboxValue() {
	let arr = [];
	$("input[name=cooperationProductSeq]:checked").each(function() {
		let cooperationProductSeq = $(this).val();
		let cooperationCompanySeq = $(this).parent().parent().find('input[name=cooperationCompanySeq]').val();
		let cooperationProductID = {
			"cooperationProductSeq": cooperationProductSeq,
			"cooperationCompanySeq": cooperationCompanySeq
		}
		arr.push(cooperationProductID);
	})
	return arr;
}

/* 검색 버튼 클릭 */
function searchProduct(sortSeq, orderSeq, page) {
	getStandardProducts(6, 2, 0);
	getCooperationProducts(6, 2, 0);
}



function getStandardProducts(sortSeq, orderSeq, page) {
	let categorySeq = document.getElementById('categorySelect').value;
	let linkOption = document.querySelector('input[name="link"]:checked').value;
	let url = '';
	if (sortSeq + ':' + orderSeq == "6:2" || sortOrder == "6:2") {
		sortOrder = sortSeq + ":" + orderSeq;
	} else {
		sortOrder = makesortOrderQuery(sortOrder, sortSeq, orderSeq);
	}
	if (linkOption == 1) {
		url = '/standardProducts?&page=' + page + '&categorySeq=' + categorySeq + '&sortOrder=' + sortOrder;
	} else {
		url = '/standardProducts/link?&page=' + page + '&categorySeq=' + categorySeq + '&sortOrder=' + sortOrder;
	}
	ajaxStandardProducts(url);
}


function ajaxStandardProducts(url) {
	$.ajax({
		type: 'GET',
		url: url,
		dataType: "json",
		success: function(response) {
			console.log(response);
			let currentPage = response['page']['pageable']['pageNumber'];
			let standardProductThead = "";
			standardProductThead += '<th>카테고리</th>';
			standardProductThead += '<th><a onClick="getStandardProducts(1, 1, ' + currentPage + ');" href="javascript:void(0);">상품 명</a></th>';
			standardProductThead += '<th>ⓘ</th>';
			standardProductThead += '<th>ⓤ</th>';
			standardProductThead += '<th><a onClick="getStandardProducts(2, 1, ' + currentPage + ');" href="javascript:void(0);">통합 최저가</a></th>';
			standardProductThead += '<th><a onClick="getStandardProducts(3, 1, ' + currentPage + ');" href="javascript:void(0);">PC 최저가</a></th>';
			standardProductThead += '<th><a onClick="getStandardProducts(4, 1, ' + currentPage + ');" href="javascript:void(0);">모바일 최저가</a></th>';
			standardProductThead += '<th>평균가</th>';
			standardProductThead += '<th><a onClick="getStandardProducts(5, 1, ' + currentPage + ');" href="javascript:void(0);">업체</a></th>';
			standardProductThead += '<th>   </th>';

			$("#standardProductThead").html(standardProductThead);

			let standardProductTable = "";
			let standardProductList = response['page']['content'];
			$.each(standardProductList, function(index, standardProduct) {
				standardProductTable += '<tr style="cursor:pointer;" onclick="clickStandardProduct(\'' + standardProduct['seq'] + '\');" >';
				standardProductTable += '<td>' + standardProduct['categorySeq'] + '</td>';
				standardProductTable += '<td onclick="event.cancelBubble=true">' + standardProduct['name'] + '</td>';
				standardProductTable += '<td onclick="event.cancelBubble=true">ⓘ</td>';
				standardProductTable += '<td onclick="event.cancelBubble=true">ⓤ</td>';
				standardProductTable += '<td>' + standardProduct['combinedLowestPrice'] + '</td>';
				standardProductTable += '<td>' + standardProduct['lowestPrice'] + '</td>';
				standardProductTable += '<td>' + standardProduct['mobileLowestPrice'] + '</td>';
				standardProductTable += '<td>' + standardProduct['averagePrice'] + '</td>';
				standardProductTable += '<td>' + standardProduct['cooperationCompanyCount'] + '</td>';
				standardProductTable += '<td>' + '<input type="radio" id=' + standardProduct['seq'] + ' name="standardProduct" value="' + standardProduct['seq'] + '"></td>';
				cooperationProductTable += '</tr>';
			});
			$("#standardProductTbody").html(standardProductTable);
			let pagination = makePagination(response['page']);
			$("#standardPagination").html(pagination);
		}
	});
}

function getCooperationProducts(sortSeq, orderSeq) {
	let categorySeq = document.getElementById('categorySelect').value;
	let linkOption = document.querySelector('input[name="link"]:checked').value;
	let page = 1;
	let url = '';
	if (sortSeq + ':' + orderSeq == "6:2" || sortOrder == "6:2") {
		sortOrder = sortSeq + ":" + orderSeq;
	} else {
		sortOrder = makesortOrderQuery(sortOrder, sortSeq, orderSeq);
	}
	if (linkOption == 1) {
		url = '/cooperationProducts/unlink?&page=' + page + '&categorySeq=' + categorySeq + '&sortOrder=' + sortOrder;
	} else {
		url = '/cooperationProducts/link?&page=' + page + '&categorySeq=' + categorySeq + '&sortOrder=' + sortOrder;
	}
	ajaxCooperationProducts(url);
}

function ajaxCooperationProducts(url) {
	$.ajax({
		type: 'GET',
		url: url,
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
	for (let i = 0; i < paginationResponse['totalPages']; i++) {
		let currentPage = i + 1;
		if (paginationResponse['pageable']['pageNumber'] == i) {
			pagination += '<a class="active" onClick="getStandardProducts(6, 2, ' + i + ')"  href="javascript:void(0);">' + currentPage + '</a>';
		} else {
			pagination += '<a onClick="getStandardProducts(6, 2, ' + i + ')"  href="javascript:void(0);">' + currentPage + '</a>';
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

/* 기준 상품 tr 영역 클릭 */
function clickStandardProduct(standardProductSeq) {
	if (document.getElementById("linkedProductCheckBox").checked) {
		
		findLinkedCooperationProductByStandardProduct(standardProductSeq, 6, 3);
	}
}

/* 기준 상품과 링크된 협력 상품 가져오기 */
function findLinkedCooperationProductByStandardProduct(standardProductSeq, sortSeq, orderSeq) {
	let categorySeq = document.getElementById('categorySelect').value;
	let page = 1;

	if (sortSeq + ':' + orderSeq == "6:2" || sortOrder == "6:2") {
		sortOrder = sortSeq + ":" + orderSeq;
	} else {
		sortOrder = makesortOrderQuery(sortOrder, sortSeq, orderSeq);
	}
	url = '/standardProduct/' + standardProductSeq + '?&page=' + page + '&categorySeq=' + categorySeq + '&sortOrder=' + sortOrder;
	ajaxCooperationProducts(url);
}
