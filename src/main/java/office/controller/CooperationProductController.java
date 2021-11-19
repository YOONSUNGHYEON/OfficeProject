package office.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import office.dto.ResultResponse;
import office.dto.SearchRequest;
import office.dto.cooperationProduct.CooperationProductListResponse;
import office.dto.cooperationProduct.CooperationProductResponse;
import office.dto.cooperationProduct.LinkListReqeust;
import office.dto.cooperationProduct.LinkReqeust;
import office.service.CooperationProductService;
import office.service.LinkService;
import office.service.StandardProductService;

@RestController
@RequiredArgsConstructor
public class CooperationProductController {

	private static final Logger log = LoggerFactory.getLogger(CooperationProductController.class);
	private final CooperationProductService cooperationProductService;
	private final StandardProductService standardProductService;
	private final LinkService linkService;
	/**
	 * 링크 안된 협력사 상품 목록 조회
	 *
	 * @param searchRequest
	 * @return cooperationProductListResponse
	 */
	@GetMapping("/cooperationProducts/unlink")
	public CooperationProductListResponse findUnlinkProductByCategory(SearchRequest searchRequest) {
		Page<CooperationProductResponse> cooperationProductResponse = cooperationProductService.findUnlinkProductByCategory(searchRequest.getCategorySeq(), searchRequest.getPage(), searchRequest.getSortOrder());
		CooperationProductListResponse cooperationProductListResponse = new CooperationProductListResponse(searchRequest.getSortOrder(), cooperationProductResponse, 200, "조회 성공했습니다.");
		return cooperationProductListResponse;
	}

	/**
	 * 링크 된 협력사 상품 목록 조회
	 *
	 * @param searchRequest
	 * @return cooperationProductListResponse

	@GetMapping("/cooperationProducts/link")
	public CooperationProductListResponse findLinkProductByCategory(SearchRequest searchRequest) {
		Page<CooperationProductResponse> cooperationProductResponse = cooperationProductService.findLinkProductByCategory(searchRequest.getCategorySeq(), searchRequest.getPage(), searchRequest.getSortOrder());
		CooperationProductListResponse cooperationProductListResponse = new CooperationProductListResponse(searchRequest.getSortOrder(), cooperationProductResponse, 200, "조회 성공했습니다.");
		return cooperationProductListResponse;
	}*/

	/**
	 * 링크 생성하기
	 *
	 * @param linkReqeust
	 * @return
	 */
	@PostMapping("/cooperationProducts/link")
	public ResultResponse link(@RequestBody LinkListReqeust linkListReqeust) {
		ResultResponse response = null;
		for (int i = 0; i < linkListReqeust.getCooperationProductID().length; i++) {
			LinkReqeust linkReqeust = new  LinkReqeust(linkListReqeust.getStandardProductSeq(),
						linkListReqeust.getCooperationProductID()[i].getCooperationProductSeq(),
						linkListReqeust.getCooperationProductID()[i].getCooperationCompanySeq());
			linkService.link(linkReqeust);
		}
		return response;
	}

	/**
	 * 링크 해제하기
	 *
	 * @param linkReqeust
	 * @return

	@PostMapping("/cooperationProducts/unlink")
	public ResultResponse unlink(@RequestBody LinkListReqeust linkReqeust) {
		log.info(linkReqeust.getCooperationProductID()[0].getCooperationCompanySeq() + "");
		ResultResponse response = null;
		for (int i = 0; i < linkReqeust.getCooperationProductID().length; i++) {
			String cooperationProductSeq = linkReqeust.getCooperationProductID()[i].getCooperationProductSeq();
			String cooperationCompanySeq = linkReqeust.getCooperationProductID()[i].getCooperationCompanySeq();
			CooperationProductResponse cooperationProductResponse = cooperationProductService.unlink(linkReqeust.getStandardProductSeq(), cooperationProductSeq, cooperationCompanySeq);
			standardProductService.findLowestPrice(linkReqeust.getStandardProductSeq());
			if(cooperationProductResponse == null) {
				response = new ResultResponse(400, "링크 해제하기 실패");
				break;
			} else {
				standardProductService.findLowestPrice(linkReqeust.getStandardProductSeq());
				response = new ResultResponse(200, "링크 해제하기 성공");
			}
		}

		return response;

	} */
}
