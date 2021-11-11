$(document).ready(function() {
	var xOffset = 10;
	var yOffset = 30;

	//마우스 오버시 preview 생성
	$(document).on("mouseover", ".preImg", function(e) {
		/*console.log($(this));
		let imgURL = 'http://192.168.56.106/DanawaOfficeProject/image/' + document.querySelector('input[name="imageURL"]').value;
		let imgURL = 'http://localhost/image/' + document.querySelector('input[name="imageURL"]').value;
		if(404 == urlExists(imgURL)) {}
			imgURL = 'image/noImage.jpg';
		}
		$("body").append("<p id='preview'><img src='" + imgURL + "' width='400px' /></p>");
		*/
	});

	//마우스 이동시 preview 이동
	$(document).on("mousemove", ".preImg", function(e) {
		console.log("333");
	});

	//마우스 아웃시 preview 제거
	$(document).on("mouseout", ".preImg", function() {
		console.log("222");
	});
});

function urlExists(url) {
	var http_status = "";
	var http = new XMLHttpRequest();
	http.open('HEAD', url, false);
	http.send();
	http_status = http.status;
	return http_status;
}
