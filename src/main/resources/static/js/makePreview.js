let ok="";
$(document).ready(function() {
	const xOffset = 10;
	const yOffset = 30;

	//마우스 오버시 preview 생성
	$(document).on("mouseover", ".preImg", function(e) {
		let imgURL = $(this).children('input[name="imageURL"]').val();
		urlExists(imgURL);
		if (ok!=true) {
			imgURL = '/image/noImage.jpg';
		} else {
			imgURL = 'http://192.168.56.106/DanawaOfficeProject/image/' + imgURL;
		}
		$("body").append('<img id="preview" src="' + imgURL + '"  class="previewImg" >');
		$("#preview")
			.css("top", (e.pageY - xOffset) + "px")
			.css("left", (e.pageX + yOffset) + "px")
			.fadeIn("fast");

	});
	//마우스 이동시 preview 이동
	$(document).on("mousemove", ".preImg", function(e) {
		$("#preview")
			.css("top", (e.pageY - xOffset) + "px")
			.css("left", (e.pageX + yOffset) + "px");
	});

	//마우스 아웃시 preview 제거
	$(document).on("mouseout", ".preImg", function() {
		$("#preview").remove();
	});
});

function urlExists(url) {
	$.getJSON('/images/' + url, function(existResponse) {
		ok = existResponse;
	})
}
