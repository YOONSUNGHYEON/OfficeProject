package office.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import office.dto.SearchRequest;
import office.dto.cooperationProduct.CooperationProductListResponse;
import office.dto.cooperationProduct.CooperationProductResponse;
import office.dto.standardProduct.StandardProductListResponse;
import office.dto.standardProduct.StandardProductResponse;
import office.service.CooperationProductService;
import office.service.StandardProductService;

@RestController
@RequiredArgsConstructor
public class StandardProductController {

	private static final Logger log = LoggerFactory.getLogger(StandardProductController.class);
	private final StandardProductService standardProductService;
	private final CooperationProductService cooperationProductService;

	/**
	 * 링크 안된 상품 목록 카테고리 별로 조회하기
	 *
	 * @param searchRequest
	 * @return
	 */
	@GetMapping(value = "/standardProducts")
	public StandardProductListResponse findUnLinkProductByCategory(SearchRequest searchRequest) {
		Page<StandardProductResponse> standardProductDTOs = standardProductService.findUnLinkProductByCategory(searchRequest.getCategorySeq(), searchRequest.getSortOrder(), searchRequest.getPage());
		StandardProductListResponse standardProductListResponse;
		if(standardProductDTOs.getContent()!=null) {
			standardProductListResponse = new StandardProductListResponse(searchRequest.getSortOrder(), standardProductDTOs, 200, "조회 성공했습니다.");
		} else {
			standardProductListResponse = new StandardProductListResponse(searchRequest.getSortOrder(), standardProductDTOs, 400, "조회 실패했습니다.");
		}
		return standardProductListResponse;

	}

	/**
	 * 링크 된 상품 목록 카테고리 별로 조회하기
	 *
	 * @param searchRequest
	 * @return
	 */
	@GetMapping(value = "/standardProducts/link")
	public StandardProductListResponse findLinkProductByCategory(SearchRequest searchRequest) {
		Page<StandardProductResponse> standardProductDTOs = standardProductService.findLinkProductByCategory(searchRequest.getCategorySeq(), searchRequest.getSortOrder(), searchRequest.getPage());
		StandardProductListResponse standardProductListResponse = new StandardProductListResponse(searchRequest.getSortOrder(), standardProductDTOs, 200, "조회 성공했습니다.");
		return standardProductListResponse;

	}

	/**
	 * 기준 상품과 연결되어 있는 협력사 상품 목록 조회
	 *
	 * @param standardProductSeq
	 * @return
	 */
	@GetMapping(value = "/standardProduct/{standardProductSeq}")
	public CooperationProductListResponse findLinkedCooperationProductList(@PathVariable("standardProductSeq") String standardProductSeq, SearchRequest searchRequest) {
		Page<CooperationProductResponse> cooperationProductResponse = cooperationProductService.findByStandardProductSeq(standardProductSeq, searchRequest.getSortOrder());
		CooperationProductListResponse cooperationProductListResponse = new CooperationProductListResponse(searchRequest.getSortOrder(), cooperationProductResponse, 200, "조회 성공했습니다.");
		return cooperationProductListResponse;

	}
}
