package office.controller;

import java.util.ArrayList;

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
import office.dto.standardProduct.StandardProductResponse;
import office.service.CooperationProductService;
import office.service.StandardProductService;

@RestController
@RequiredArgsConstructor
public class CooperationProductController {

	private static final Logger log = LoggerFactory.getLogger(CooperationProductController.class);
	private final CooperationProductService cooperationProductService;
	private final StandardProductService standardProductService;

	@GetMapping("/cooperationProducts/unlink")
	public CooperationProductListResponse findUnLinkProductByCategory(SearchRequest searchRequest) {
		Page<CooperationProductResponse> cooperationProductResponse =  cooperationProductService.findUnLinkProductByCategory(searchRequest.getCategorySeq(), searchRequest.getSortOrder());
		CooperationProductListResponse cooperationProductListResponse = new CooperationProductListResponse(searchRequest.getSortOrder(), cooperationProductResponse, 200, "조회 성공했습니다.");
		return cooperationProductListResponse;
	}

	@GetMapping("/cooperationProducts/link")
	public String findLinkProductByCategory(SearchRequest searchRequest) {
		return "/createCooperationProduct";
	}

	@PostMapping("/cooperationProducts/link")
	public String link(@RequestBody CooperationProductListLinkReqeust linkReqeust) {
		String[] cooperationProductSeqArr = linkReqeust.getCooperationProductSeqArr();
		String[] cooperationCompanySeqArr = linkReqeust.getCooperationCompanySeqArr();
		ArrayList<Integer> priceList = new ArrayList<>();
		ArrayList<Integer> mobilePriceList = new ArrayList<>();
		StandardProductResponse standardProductResponse =  standardProductService.findBySeq(linkReqeust.getStandardProductSeq());
		for(int i=0; i<cooperationProductSeqArr.length; i++) {
			CooperationProductResponse cooperationProductResponse = cooperationProductService.link(standardProductResponse, cooperationProductSeqArr[i], cooperationCompanySeqArr[i]);
			priceList.add(cooperationProductResponse.getPrice());
			mobilePriceList.add(cooperationProductResponse.getMobilePrice());
		}
		standardProductService.findLowestPrice(priceList, mobilePriceList, standardProductResponse);
		return "/createCooperationProduct";
	}
}
