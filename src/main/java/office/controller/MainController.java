package office.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping(value={"/link","/"})
	public String link() {
		return "/linkProduct";
	}



}
