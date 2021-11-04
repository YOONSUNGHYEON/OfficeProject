package office.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import office.dto.ListResult;
import office.dto.ResultResponse;
import office.dto.SearchRequest;
import office.dto.StandardProductDTO;
import office.service.StandardProductService;

@RestController
@RequiredArgsConstructor
public class StandardProductController {

	private static final Logger log = LoggerFactory.getLogger(StandardProductController.class);
	private final StandardProductService standardProductService;

	@GetMapping(value = "/standardProducts")
	public ListResult<StandardProductDTO> findAll(@ModelAttribute SearchRequest searchRequest) {
		List<StandardProductDTO> standardProductDTOList = standardProductService.findByCategorySeq(searchRequest.getCategorySeq());
		ListResult<StandardProductDTO> standardProductListResponse =  new ListResult<>(standardProductDTOList, new ResultResponse(200, "조회 성공"));
		log.info(" 링크 옵션 :  "+searchRequest.getLinkOption());
		return standardProductListResponse;
	}
}
