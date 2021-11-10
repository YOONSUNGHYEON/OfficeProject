function checkSelectAll(checkbox) {
	const selectall = document.querySelector('input[name="checkAll"]');
	if (checkbox.checked === false) {
		selectall.checked = false;
	}
}

function selectAll(selectAll) {
	const checkboxes = document.getElementsByName('cooperationProductSeq');
	checkboxes.forEach((checkbox) => {
		checkbox.checked = selectAll.checked
	})
}