package office.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import office.dto.SearchRequest;
import office.dto.cooperationProduct.CooperationProductListLinkReqeust;
import office.dto.cooperationProduct.CooperationProductListResponse;
import office.dto.cooperationProduct.CooperationProductResponse;
import office.service.CooperationProductService;
import office.service.StandardProductService;

@RestController
@RequiredArgsConstructor
public class CooperationProductController {

	private static final Logger log = LoggerFactory.getLogger(CooperationProductController.class);
	private final CooperationProductService cooperationProductService;
	private final StandardProductService standardProductService;

	/**
	 * 링크 안된 협력사 상품 목록 조회
	 *
	 * @param searchRequest
	 * @return cooperationProductListResponse
	 */
	@GetMapping("/cooperationProducts/unlink")
	public CooperationProductListResponse findUnlinkProductByCategory(SearchRequest searchRequest) {
		Page<CooperationProductResponse> cooperationProductResponse = cooperationProductService.findUnlinkProductByCategory(searchRequest.getCategorySeq(), searchRequest.getSortOrder());
		CooperationProductListResponse cooperationProductListResponse = new CooperationProductListResponse(searchRequest.getSortOrder(), cooperationProductResponse, 200, "조회 성공했습니다.");
		return cooperationProductListResponse;
	}

	/**
	 * 링크 된 협력사 상품 목록 조회
	 *
	 * @param searchRequest
	 * @return cooperationProductListResponse
	 */
	@GetMapping("/cooperationProducts/link")
	public CooperationProductListResponse findLinkProductByCategory(SearchRequest searchRequest) {
		Page<CooperationProductResponse> cooperationProductResponse = cooperationProductService.findLinkProductByCategory(searchRequest.getCategorySeq(), searchRequest.getSortOrder());
		CooperationProductListResponse cooperationProductListResponse = new CooperationProductListResponse(searchRequest.getSortOrder(), cooperationProductResponse, 200, "조회 성공했습니다.");
		return cooperationProductListResponse;
	}

	/**
	 * 링크 생성하기
	 *
	 * @param linkReqeust
	 * @return
	 */
	@PostMapping("/cooperationProducts/link")
	public String link(@RequestBody CooperationProductListLinkReqeust linkReqeust) {
		log.info(linkReqeust.getCooperationProductID()[0].getCooperationCompanySeq() + "");
		for (int i = 0; i < linkReqeust.getCooperationProductID().length; i++) {
			String cooperationProductSeq = linkReqeust.getCooperationProductID()[i].getCooperationProductSeq();
			String cooperationCompanySeq = linkReqeust.getCooperationProductID()[i].getCooperationCompanySeq();
			CooperationProductResponse cooperationProductResponse = cooperationProductService.link(linkReqeust.getStandardProductSeq(), cooperationProductSeq, cooperationCompanySeq);
			standardProductService.findLowestPrice(linkReqeust.getStandardProductSeq());
		}

		return "/createCooperationProduct";
	}

	/**
	 * 링크 해제하기
	 *
	 * @param linkReqeust
	 * @return
	 */
	@PostMapping("/cooperationProducts/unlink")
	public String unlink(@RequestBody CooperationProductListLinkReqeust linkReqeust) {
		log.info(linkReqeust.getCooperationProductID()[0].getCooperationCompanySeq() + "");
		for (int i = 0; i < linkReqeust.getCooperationProductID().length; i++) {
			String cooperationProductSeq = linkReqeust.getCooperationProductID()[i].getCooperationProductSeq();
			String cooperationCompanySeq = linkReqeust.getCooperationProductID()[i].getCooperationCompanySeq();
			CooperationProductResponse cooperationProductResponse = cooperationProductService.unlink(linkReqeust.getStandardProductSeq(), cooperationProductSeq, cooperationCompanySeq);
			standardProductService.findLowestPrice(linkReqeust.getStandardProductSeq());
		}

		return "/createCooperationProduct";
	}

}
