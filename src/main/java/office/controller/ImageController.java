package office.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {
	/**
	 * 이미지 url 파일 존재 여부
	 * @param imageURL
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/images/{imageURL}")
	public boolean image(@PathVariable("imageURL") String imageURL) throws IOException {
		URL url = new URL("http://192.168.0.53/DanawaOfficeProject/image/" + imageURL);
		URLConnection con = url.openConnection();
		HttpURLConnection exitCode = (HttpURLConnection)con;
		if(exitCode.getResponseCode()==200) {
			return true;
		}
		return false;
	}
}
