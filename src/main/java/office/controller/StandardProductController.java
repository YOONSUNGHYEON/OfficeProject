package office.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import office.dto.SearchRequest;
import office.dto.standardProduct.StandardProductListResponse;
import office.dto.standardProduct.StandardProductResponse;
import office.service.StandardProductService;

@RestController
@RequiredArgsConstructor
public class StandardProductController {

	private static final Logger log = LoggerFactory.getLogger(StandardProductController.class);
	private final StandardProductService standardProductService;

	/**
	 * 링크 안된 상품 목록 카테고리 별로 조회하기
	 *
	 * @param searchRequest
	 * @return
	 */

	@GetMapping(value = "/standardProducts")
	public StandardProductListResponse findUnLinkProductByCategory(SearchRequest searchRequest) {
		Page<StandardProductResponse> standardProductDTOs = standardProductService.findUnLinkProductByCategory(searchRequest.getCategorySeq(), searchRequest.getSortOrder());
		StandardProductListResponse standardProductListResponse = new StandardProductListResponse(searchRequest.getSortOrder(), standardProductDTOs, 200, "조회 성공했습니다.");
		log.info(" 링크 옵션 :  "+searchRequest.getLinkOption());
		return standardProductListResponse;

	}

	@GetMapping(value = "/standardProducts/link")
	public StandardProductListResponse findLinkProductByCategory(SearchRequest searchRequest) {
		Page<StandardProductResponse> standardProductDTOs = standardProductService.findLinkProductByCategory(searchRequest.getCategorySeq(), searchRequest.getSortOrder());
		StandardProductListResponse standardProductListResponse = new StandardProductListResponse(searchRequest.getSortOrder(), standardProductDTOs, 200, "조회 성공했습니다.");
		log.info(" 링크 옵션 :  "+searchRequest.getLinkOption());
		return standardProductListResponse;

	}
}
