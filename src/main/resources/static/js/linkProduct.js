window.onload = function() {
	findCategorys();
}

/*카테고리 목록 가져오기*/
function findCategorys() {
	$.ajax({
		type: 'GET',
		url: '/categorys',
		dataType: "json",
		success: function(categorysResponse) {
			if (categorysResponse['code'] == '200') {
				categorySelect = makeSelect(categorysResponse['list']);
				document.getElementById('categorySelect').innerHTML += categorySelect;
			} else if (cooperationCompanysResponse['code'] == '400') {
				alert("");
			} else if (cooperationCompanysResponse['code'] == '500') {
				alert("서버 상에 문제가 있어 카테고리를 불러오는데 실패 했습니다.");
			}
		}
	});
}

/* option 만들기 */
function makeSelect(optionDatas) {
	let dataSelect = '';
	$.each(optionDatas, function(index, data) {
		dataSelect += '<option value="';
		dataSelect += data['seq'] + '">';
		dataSelect += data['name'] + '</option>';
	});
	return dataSelect;
}


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
			if (response['code'] == 200) {
				alert("링크를 생성했습니다.");
				getStandardProducts(6, 2, 0);
				getCooperationProducts(3, 2, 0);
			} else if (response['code'] == 400) {
				alert("이미 링크가 되어 있는 상품 입니다.");
			}

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
			console.log(response);
			if (response['code'] == 200) {
				alert("링크를 해제했습니다.");
				getStandardProducts(6, 2, 0);
				getCooperationProducts(3, 2, 0);
			} else if (response['code'] == 400) {
				alert("이미 링크가 해제 되어 있는 상품입니다.");
			}

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
	if (document.getElementById('categorySelect').value == -1) {
		alert("카테고리를 선택해 주세요.");
	} else {
		getStandardProducts(6, 2, 0);
		getCooperationProducts(3, 2, 0);
	}
}

let standardSortOrder = "";
let cooperationSortOrder = "";
function getStandardProducts(sortSeq, orderSeq, page) {
	const categorySeq = document.getElementById('categorySelect').value;
	const linkOption = document.querySelector('input[name="link"]:checked').value;
	let url = '';
	if (sortSeq + ':' + orderSeq == "6:2" || standardSortOrder == "6:2") {
		standardSortOrder = sortSeq + ":" + orderSeq;
	} else {
		standardSortOrder = makesortOrderQuery(standardSortOrder, sortSeq, orderSeq);
	}
	if (linkOption == 1) {
		url = '/standardProducts?&page=' + page + '&categorySeq=' + categorySeq + '&sortOrder=' + standardSortOrder;
	} else {
		url = '/standardProducts/link?&page=' + page + '&categorySeq=' + categorySeq + '&sortOrder=' + standardSortOrder;
	}
	ajaxStandardProducts(url);


}

/* 기준 상품 Table > Thead 에 들어갈 html 만들기 */
function makeStandardProductThead(currentPage, sortOrderArr) {
	let standardProductThead = "";
	standardProductThead += '<th>카테고리</th>';
	standardProductThead += '<th><a id="option1" onClick="getStandardProducts(1, 1, ' + currentPage + ');" href="javascript:void(0);">상품 명</a></th>';
	standardProductThead += '<th>ⓘ</th>';
	standardProductThead += '<th>ⓤ</th>';
	standardProductThead += '<th><a id="option2" onClick="getStandardProducts(2, 1, ' + currentPage + ');" href="javascript:void(0);">통합 최저가</a></th>';
	standardProductThead += '<th><a id="option3" onClick="getStandardProducts(3, 1, ' + currentPage + ');" href="javascript:void(0);">PC 최저가</a></th>';
	standardProductThead += '<th><a id="option4" onClick="getStandardProducts(4, 1, ' + currentPage + ');" href="javascript:void(0);">모바일 최저가</a></th>';
	standardProductThead += '<th>평균가</th>';
	standardProductThead += '<th><a id="option5" onClick="getStandardProducts(5, 1, ' + currentPage + ');" href="javascript:void(0);">업체</a></th>';
	standardProductThead += '<th>   </th>';

	$("#standardProductThead").html(standardProductThead);

	for (let i = 1; i < 6; i++) {
		if (sortOrderArr[i] != null) {
			let IDName = 'option' + i;
			document.getElementById(IDName).className = 'activeThead';
			if (sortOrderArr[i] == 1) {
				$('#standardProductThead #' + IDName).append('↓');
			} else {
				$('#standardProductThead #' + IDName).append('↑');
			}
		}
	}
}

/* 기준 상품 Table > Tbody 에 들어갈 html 만들기 */
function makeStandardProductTbody(standardProducts) {
	let editUrl = 'http://192.168.0.53/DanawaOfficeProject/createStandardProduct.html?seq=';
	let blogUrl = 'http://192.168.0.53/DanawaOfficeProject/blog.html?standardProductSeq=';
	let standardProductTable = "";
	$.each(standardProducts, function(index, standardProduct) {
		standardProductTable += '<tr style="cursor:pointer;" onclick="clickStandardProduct(\'' + standardProduct['seq'] + '\');" >';
		standardProductTable += '<td>' + standardProduct['categoryName'] + '</td>';
		standardProductTable += '<td onclick="event.cancelBubble=true"> <a href="' + editUrl + standardProduct['seq'] + '"><p>' + standardProduct['name'] + '</p></a></td>';
		standardProductTable += '<td class="preImg" onclick="event.cancelBubble=true"><input name="imageURL" type="hidden" value="' + standardProduct['imageURL'] + '"><a >ⓘ</a></td>';
		standardProductTable += '<td onclick="event.cancelBubble=true"><a href="' + blogUrl + standardProduct['seq'] + '">ⓤ</a></td>';
		standardProductTable += '<td>' + standardProduct['combinedLowestPrice'] + '</td>';
		standardProductTable += '<td>' + standardProduct['lowestPrice'] + '</td>';
		standardProductTable += '<td>' + standardProduct['mobileLowestPrice'] + '</td>';
		standardProductTable += '<td>' + standardProduct['averagePrice'] + '</td>';
		standardProductTable += '<td>' + standardProduct['cooperationCompanyCount'] + '</td>';
		standardProductTable += '<td>' + '<input type="radio" id=' + standardProduct['seq'] + ' name="standardProduct" value="' + standardProduct['seq'] + '"></td>';
		cooperationProductTable += '</tr>';
	});
	$("#standardProductTbody").html(standardProductTable);
}


function ajaxStandardProducts(url) {
	$.ajax({
		type: 'GET',
		url: url,
		dataType: "json",
		success: function(response) {
			console.log(response);
			let currentPage = response['page']['pageable']['pageNumber'];
			makeStandardProductThead(currentPage, makesortOrderArr(response['sortOrder']));
			makeStandardProductTbody(response['page']['content']);
			let pagination = makePagination(response['page'], "standardProduct");
			$("#standardPagination").html(pagination);

		}
	});
}
function makesortOrderArr(multiSortQuery) {
	const multiSortArr = multiSortQuery.split(",");
	let sortOrderArr = [];
	for (let i = 0; i < multiSortArr.length; i++) {
		const sortArr = multiSortArr[i].split(":");
		sortOrderArr[sortArr[0]] = sortArr[1];
	}
	return sortOrderArr;
}

function getCooperationProducts(sortSeq, orderSeq, page) {
	const categorySeq = document.getElementById('categorySelect').value;
	const linkOption = document.querySelector('input[name="link"]:checked').value;
	let url = '';
	if (sortSeq + ':' + orderSeq == "3:2") {
		cooperationSortOrder = sortSeq + ":" + orderSeq;
	} else {
		cooperationSortOrder = makesortOrderQuery(cooperationSortOrder, sortSeq, orderSeq);
	}
	if (linkOption == 1) {
		url = '/cooperationProducts/unlink?&page=' + page + '&categorySeq=' + categorySeq + '&sortOrder=' + cooperationSortOrder;
	} else {
		url = '/cooperationProducts/link?&page=' + page + '&categorySeq=' + categorySeq + '&sortOrder=' + cooperationSortOrder;
	}
	ajaxCooperationProducts(url);
}
/* 협력사 상품 Table > Thead 에 들어갈 html 만들기 */
function makeCooperationProductThead(currentPage, sortOrderArr) {
	let cooperationProductThead = "";
	cooperationProductThead += '<th><input name="checkAll" type="checkbox" onclick="selectAll(this)"></th>';
	cooperationProductThead += '<th><a id="cooperationOption1" onClick="getCooperationProducts(1, 1, ' + currentPage + ');" href="#a">협력사</a></th>';
	cooperationProductThead += '<th>카테고리</th>';
	cooperationProductThead += '<th><a id="cooperationOption2" onClick="getCooperationProducts(2, 1, ' + currentPage + ')" href="#a">상품명</a></th>';
	cooperationProductThead += '<th>ⓘ</th>';
	cooperationProductThead += '<th>ⓤ</th>';
	cooperationProductThead += '<th>PC가격</th>';
	cooperationProductThead += '<th>모바일 가격</th>';
	cooperationProductThead += '<th><a id="cooperationOption3" onClick="getCooperationProducts(3,  1, ' + currentPage + ')" href="#a">최초 등록</a></th>';
	$("#cooperationProductThead").html(cooperationProductThead);

	for (let i = 1; i < sortOrderArr.length; i++) {
		if (sortOrderArr[i] != null) {
			let IDName = 'cooperationOption' + i;
			document.getElementById(IDName).className = 'activeThead';
			if (sortOrderArr[i] == 1) {
				$('#cooperationProductThead #' + IDName).append('↓');
			} else {
				$('#cooperationProductThead #' + IDName).append('↑');
			}
		}
	}
}




/* 협력사 상품 Table > Tbody 에 들어갈 html 만들기 */
function makeCooperationProductTbody(cooperationProducts) {
	let cooperationProductTable = "";
	$.each(cooperationProducts, function(index, cooperationProduct) {
		let editUrl = 'http://192.168.0.53/DanawaOfficeProject/createCooperationProduct.html?cooperationProductSeq=' + cooperationProduct['cooperationProductSeq'] + '&cooperationCompanySeq=' + cooperationProduct["cooperationCompanySeq"];
		cooperationProductTable += '<tr style="cursor:pointer;">';
		cooperationProductTable += '<td><input type="checkbox" name="cooperationProductSeq" onclick="checkSelectAll(this)" value="' + cooperationProduct['cooperationProductSeq'] + '"></td>';
		cooperationProductTable += '<td> <input type="hidden" id="cooperationCompanySeq" name="cooperationCompanySeq" value="' + cooperationProduct["cooperationCompanySeq"] + '">' + cooperationProduct["cooperationCompanyName"] + '</td>';
		cooperationProductTable += '<td>' + cooperationProduct['categoryName'] + '</td>';
		cooperationProductTable += '<td title="' + cooperationProduct['name'] + '" class="productName" ><a href="' + editUrl + '"><p>' + cooperationProduct['name'] + '</p></a></td>';
		cooperationProductTable += '<td class="preImg" onclick="event.cancelBubble=true"><input name="imageURL" type="hidden" value="' + cooperationProduct['imageURL'] + '"><a >ⓘ</a></td>';
		cooperationProductTable += '<td><a target="_blank" href="' + cooperationProduct['url'] + '">ⓤ</a></td>';
		cooperationProductTable += '<td>' + cooperationProduct['price'] + '</td>';
		cooperationProductTable += '<td>' + cooperationProduct['mobilePrice'] + '</td>';
		cooperationProductTable += '<td>' + cooperationProduct['inputDate'] + '</td>';
		cooperationProductTable += '</tr>';
	});

	$("#cooperationProductTbody").html(cooperationProductTable);
}
function ajaxCooperationProducts(url) {
	$.ajax({
		type: 'GET',
		url: url,
		dataType: "json",
		success: function(response) {
			console.log(response);
			let currentPage = response['page']['pageable']['pageNumber'];
			makeCooperationProductThead(currentPage, makesortOrderArr(response['sortOrder']));
			makeCooperationProductTbody(response['page']['content']);
			let pagination = makePagination(response['page'], 'cooperationProduct');
			$("#cooperationPagination").html(pagination);
		}

	})

}
/* 페이지네이션 제작 */
function makePagination(paginationResponse, option) {
	const blockPage = 10; //보여줄 페이지 수
	const currentPage = paginationResponse['pageable']['pageNumber']; //현재 페이지 넘버
	const startPage = Math.floor(currentPage / blockPage) * 10; // 현재 페이지 묶음시작 페이지
	const nextArrow = (Math.floor(currentPage / blockPage) - 1) * 10; // 전 페이지 묶음 시작 페이지
	const prevArrow = (Math.floor(currentPage / blockPage) + 1) * 10; // 다음 페이지 묶음 시작 페이지
	const totalLastPage = Math.floor(paginationResponse['totalPages'] / blockPage) * 10; // 전체 마지막 페이지 묶음 시작 페이지
	let pagination = "";
	if (option == "standardProduct") {
		option = "getStandardProducts(6, 2,";
	} else {
		option = "getCooperationProducts(3, 2,";
	}

	if (startPage != 0) {
		pagination += '<a class="arrow" onClick="' + option + ' 0)" href="#a"><<</a>';
		pagination += '<a class="arrow" onClick=" ' + option + nextArrow + ')" href="javascript:void(0);">&lt;</a>';
	}
	for (let i = startPage; i < startPage + blockPage; i++) {
		let pageNumber = i + 1;
		if (currentPage == i) {
			pagination += '<a class="active" onClick=" ' + option + i + ')"  href="javascript:void(0);">' + pageNumber + '</a>';
		} else {
			pagination += '<a onClick="' + option + i + ')"  href="javascript:void(0);">' + pageNumber + '</a>';
		}
		if (paginationResponse['numberOfElements'] < 20) {
			break;
		}
	}
	if (totalLastPage != startPage) {
		pagination += '<a class="arrow" onClick="' + option + prevArrow + ')" href="#a">></a>';
		pagination += '<a class="arrow" onClick="' + option + (paginationResponse['totalPages'] - 1) + ')" href="javascript:void(0);">>></a>';
	}
	return pagination;
}

function makesortOrderQuery(priorityQuery, sortSeq, orderSeq) {
	const prioritys = priorityQuery.split(",");
	let newPriorityQuery = "";
	let ok = false;
	for (let i = 0; i < prioritys.length; i++) {
		const sortOption = prioritys[i].split(":");
		if (sortOption[0] == sortSeq) {
			ok = true;
			if (sortOption[1] == 1) {
				newPriorityQuery += sortSeq + ':' + 2;
			} else {
				newPriorityQuery += sortSeq + ':' + 1;
			}
		} else {
			newPriorityQuery += prioritys[i];
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
		$('input[type=radio][value="' + standardProductSeq + '"]').prop('checked', true);
		findLinkedCooperationProductByStandardProduct(standardProductSeq, 3, 2);
	}
}

/* 기준 상품과 링크된 협력 상품 가져오기 */
function findLinkedCooperationProductByStandardProduct(standardProductSeq, sortSeq, orderSeq) {
	let categorySeq = document.getElementById('categorySelect').value;
	let page = 1;
	cooperationSortOrder = makesortOrderQuery(cooperationSortOrder, sortSeq, orderSeq);
	url = '/standardProduct/' + standardProductSeq + '?&page=' + page + '&categorySeq=' + categorySeq + '&sortOrder=' + cooperationSortOrder;
	ajaxCooperationProducts(url);
}

/* 링크상품보기 체크박스 클릭시 */
function checkLinked() {
	$('input[type=radio][value="0"]').prop('checked', true);
}

/* 링크안된상품 라디오 버튼 클릭시 */
function checkUnlinked() {
	$('input[type=checkbox][name="linkedProduct"]').prop('checked', false);
}



