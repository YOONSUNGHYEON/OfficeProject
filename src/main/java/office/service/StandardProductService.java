package office.service;

import java.util.ArrayList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import office.dto.sort.OrderEnum;
import office.dto.sort.StandardProductSortEnum;
import office.dto.standardProduct.StandardProductResponse;
import office.entity.StandardProduct;
import office.repository.CooperationProductRepository;
import office.repository.StandardProductRepository;

@Service
@RequiredArgsConstructor
public class StandardProductService {

	private static final Logger log = LoggerFactory.getLogger(StandardProductService.class);

	private final StandardProductRepository standardProductRepository;
	private final CooperationProductRepository cooperationProductRepository;

	/**
	 * 다중 정렬 묶음을 ','을 기준으로 나눈 후 list에 저장한다. 그 후 우선 순위 순으로 list를 정렬한다.
	 *
	 * @param multiSortOrderStr 다중 정렬 할 칼럼의 코드와 나열 순서가 한 묶음으로 무작위로 이어져있는 문자열 형태 ex)
	 *                          5:1,1:2,3:2
	 * @return ArrayList multiSortOrderList
	 */
	public ArrayList<String> makeList(String multiSortOrderStr) {
		ArrayList<String> multiSortOrderList = new ArrayList<String>();
		String[] sortOrderArr = multiSortOrderStr.split(",");
		for (String sortOrder : sortOrderArr) {
			multiSortOrderList.add(sortOrder);
		}

		multiSortOrderList.sort(null);
		return multiSortOrderList;
	}

	/**
	 * 주어진 문자열을 분해해서 새로운 다중 정렬 Sort를 만든다.
	 *
	 * @param multiSortOrderStr 다중 정렬 할 칼럼의 코드와 나열 순서가 한 묶음으로 무작위로 이어져있는 문자열 형태 ex)
	 *                          5:1,1:2,3:2
	 * @return Sort
	 */
	public Sort getSort(String multiSortOrderStr) {
		ArrayList<String> list = makeList(multiSortOrderStr);
		Map<Integer, String> orderEnumrMap = OrderEnum.makeMap();
		Map<Integer, String> sortEnumMap = StandardProductSortEnum.makeMap();

		Sort sort = null;

		for (int i = 0; i < list.size(); i++) {
			String[] sortOrderArr = list.get(i).split(":");
			int sortCode = Integer.parseInt(sortOrderArr[0]);
			int orderCode = Integer.parseInt(sortOrderArr[1]);
			if (i == 0) {
				sort = orderEnumrMap.get(orderCode) == OrderEnum.DESC.getName() ? Sort.by(sortEnumMap.get(sortCode)).ascending() : Sort.by(sortEnumMap.get(sortCode)).descending();
			} else {
				sort = orderEnumrMap.get(orderCode) == OrderEnum.DESC.getName() ? sort.and(Sort.by(sortEnumMap.get(sortCode)).ascending()) : sort.and(Sort.by(sortEnumMap.get(sortCode)).descending());
			}
		}
		return sort;
	}

	/**
	 * 링크 안된 상품 목록 카테고리 별로 조회하기
	 *
	 * @param categorySeq     카테고리 Seq
	 * @param sortPriorityStr 다중 정렬 문자열
	 * @param page            현재 페이지
	 * @return StandardProductResponse
	 */
	public Page<StandardProductResponse> findUnLinkProductByCategory(long categorySeq, String sortPriorityStr, int page) {

		Pageable firstPageWithTwoElements = PageRequest.of(page, 20, getSort(sortPriorityStr));
		Page<StandardProduct> allStandardProducts = standardProductRepository.findAllByCategorySeqAndLowestPrice(categorySeq, 0, firstPageWithTwoElements);
		Page<StandardProductResponse> standardProductPageResponse = allStandardProducts.map(standardProduct -> standardProduct.toDTO());
		return standardProductPageResponse;

	}

	/**
	 * 링크 된 상품 목록 카테고리 별로 조회하기
	 *
	 * @param categorySeq     카테고리 Seq
	 * @param sortPriorityStr 다중 정렬 문자열
	 * @param page            현재 페이지
	 * @return StandardProductResponse
	 */
	public Page<StandardProductResponse> findLinkProductByCategory(long categorySeq, String sortPriorityStr, int page) {
		Pageable firstPageWithTwoElements = PageRequest.of(page, 20, getSort(sortPriorityStr));
		Page<StandardProduct> allStandardProducts = standardProductRepository.findAllByCategorySeqAndCooperationCompanyCountGreaterThan(categorySeq, 0, firstPageWithTwoElements);
		Page<StandardProductResponse> standardProductPageResponse = allStandardProducts.map(standardProduct -> standardProduct.toDTO());
		return standardProductPageResponse;

	}

	/**
	 * 기준 상품 seq에 해당하는 기준상품 목록 반환
	 *
	 * @param standardProductSeq
	 * @return StandardProductResponse
	 */
	public StandardProductResponse findBySeq(String standardProductSeq) {
		StandardProduct standardProduct = standardProductRepository.findBySeq(standardProductSeq);
		return standardProduct.toDTO();
	}



}
