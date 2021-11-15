$(document).ready(function() {
	var xOffset = 10;
	var yOffset = 30;

	//마우스 오버시 preview 생성
	$(document).on("mouseover", ".preImg", function(e) {
		let imgURL = 'http://192.168.56.106/DanawaOfficeProject/image/' + $(this).children('input[name="imageURL"]').val();
		$("body").append("<img id='preview' src='" + imgURL + "' class='previewImg' />");
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
	// http://abc.com -> http:// qwe.com X (CORS), server는 가능
	// http://abc.com -> http://abc.com(server) -> http://qwe.com
	// http://localhost:9090/link -> http://localhost:9090/check(controller) -> http://192.168.56.106/DanawaOfficeProject/image/ 호출해서 체크

	var http_status = "";
	var http = new XMLHttpRequest();
	http.open('HEAD', url, false);
	http.send();
	http_status = http.status;
	return http_status;
}
