package office;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.jupiter.api.Test;



class FileTest {

	@Test
	void test() throws IOException {
		//File file1 = new File("https://img.danawa.com/new/noData/img/noImg_360.gif");
		URL url = new URL("http://192.168.56.102/DanawaOfficeProject/image/E20211107151310.jpg");
		URLConnection con = url.openConnection();
		HttpURLConnection exitCode = (HttpURLConnection)con;

		assertEquals(200,exitCode.getResponseCode());

		//exitCode.getResponseCode() == 200 : 파일존재
		//exitCode.getResponseCode() == 404 : 파일 존재하지 않음.
		//assertEquals(true, isValidURL("http://192.168.56.102/DanawaOfficeProject/image/E202117151310.jpg"));
	}

	boolean isValidURL(String url) {
		try {
			new URI(url).parseServerAuthority();
			return true;
		} catch (URISyntaxException e) {
			return false;
		}
	}
}
