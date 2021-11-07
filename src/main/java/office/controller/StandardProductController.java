package office.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import office.dto.SearchRequest;
import office.entity.StandardProduct;
import office.service.StandardProductService;

@RestController
@RequiredArgsConstructor
public class StandardProductController {

	private static final Logger log = LoggerFactory.getLogger(StandardProductController.class);
	private final StandardProductService standardProductService;

	@GetMapping(value = "/standardProducts")
	public Page<StandardProduct> findAll(@ModelAttribute SearchRequest searchRequest) {
		Page<StandardProduct> standardProductDTOList = standardProductService.findByCategorySeq(searchRequest.getCategorySeq(), searchRequest.getSortOrder());
		//ListResult<StandardProduct> standardProductListResponse =  new ListResult<>(standardProductDTOList, new ResultResponse(200, "조회 성공"), searchRequest.getSortOrder());
		log.info(" 링크 옵션 :  "+searchRequest.getSortOrder());
		return standardProductDTOList;
	}
}
