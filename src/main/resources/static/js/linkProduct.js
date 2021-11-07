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


function sort(sortSeq, orderSeq) {
	let categorySeq = document.getElementById('categorySelect').value;
	let linkOption = document.querySelector('input[name="link"]:checked').value;

	if (sortSeq+':'+orderSeq == "6:2" || sortOrder == "6:2") {
		sortOrder = sortSeq + ":" + orderSeq;
	} else {
		sortOrder = makesortOrderQuery(sortOrder, sortSeq , orderSeq);
	}
	console.log('/standardProducts?linkOption=' + linkOption + '&categorySeq=' + categorySeq + '&sortOrder=' + sortOrder);
	$.ajax({
		type: 'GET',
		url: '/standardProducts?linkOption=' + linkOption + '&categorySeq=' + categorySeq + '&sortOrder=' + sortOrder,
		dataType: "json",
		success: function(response) {
			sortOrder = response['sortOption'];
			
		}
	});
}

function makesortOrderQuery(priorityQuery, sortSeq , orderSeq) {
	const priorityList = priorityQuery.split(",");
	let newPriorityQuery = "";
	let ok = false;
	for(let i=0; i < priorityList.length; i++){
		const sortOption = priorityList[i].split(":");
		if(sortOption[0]==sortSeq) {
			ok=true;
			if(sortOption[1]==1){
				newPriorityQuery += sortSeq + ':' + 2;
			} else {
				newPriorityQuery += sortSeq + ':' + 1;
			}
		}else {
			newPriorityQuery+=priorityList[i];
		}
		newPriorityQuery+=',';
	}
	if(ok == false) {
		newPriorityQuery+=sortSeq + ':' + orderSeq;
	} else {
		newPriorityQuery = newPriorityQuery.substring(0,newPriorityQuery.length-1);
	}
	return newPriorityQuery;
}
