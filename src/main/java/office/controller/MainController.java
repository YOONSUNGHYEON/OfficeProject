package office.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping(value={"/" , "/standardProduct"})
	public String create() {
		return "/createStandardProduct";
	}

	@GetMapping("/excel")
	public String upload() {
		return "/uploadExcel";
	}

	@GetMapping("/parsing")
	public String parsing() {
		return "/productParsing";
	}

	@GetMapping("/link")
	public String link() {
		return "/linkProduct";
	}
	@GetMapping("/blog")
	public String blog() {
		return "/blog";
	}
}
