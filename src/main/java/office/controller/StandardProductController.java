package office.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import office.dto.StandardProductDTO;

@RestController
@RequiredArgsConstructor
public class StandardProductController {

	private static final Logger log = LoggerFactory.getLogger(StandardProductController.class);

	@PostMapping(value = "/standardProducts")
    public void create(@RequestBody StandardProductDTO standardProductDTO) {
		String name = standardProductDTO.getName();
		String date = standardProductDTO.getManufactureDate();
		log.info(name);
    }
}
