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

function search() {
	let categorySeq = document.getElementById('categorySelect').value;
	let linkRadio = document.querySelector('input[name="link"]:checked').value;
}

