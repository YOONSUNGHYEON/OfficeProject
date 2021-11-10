package office.service;

import java.util.ArrayList;
import java.util.HashMap;
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
import office.entity.CooperationProduct;
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
				sort = orderEnumrMap.get(orderCode) == OrderEnum.ASC.getName() ? Sort.by(sortEnumMap.get(sortCode)).ascending() : Sort.by(sortEnumMap.get(sortCode)).descending();
			} else {
				sort = orderEnumrMap.get(orderCode) == OrderEnum.ASC.getName() ? sort.and(Sort.by(sortEnumMap.get(sortCode)).ascending()) : sort.and(Sort.by(sortEnumMap.get(sortCode)).descending());
			}
		}
		return sort;
	}


	/**
	 * 선택된 다중 정렬 문자열을 받아서 배열로 변환 </br>
	 * ex) 1:2,2:1 -> arr = {2, 1}
	 *
	 * @param sortPriorityStr
	 * @return int[] sortArr
	 */
	public Sort makeSortArr(String sortPriorityStr) {
		ArrayList<String> list = new ArrayList<String>();

		String[] sortPriorityList = sortPriorityStr.split(",");
		int[] sortArr = new int[7];
		for (String sortOrder : sortPriorityList) {
			String[] sortOrderList = sortOrder.split(":");
			sortArr[Integer.parseInt(sortOrderList[0])] = Integer.parseInt(sortOrderList[1]);
		}
		for (String sortOrder : sortPriorityList) {
			list.add(sortOrder);
		}
		list.sort(null);

		Map<Integer, String> sortMap = new HashMap<Integer, String>();
		Map<Integer, String> orderMap = new HashMap<Integer, String>();

		for (StandardProductSortEnum sort : StandardProductSortEnum.values()) {
			sortMap.put(sort.getCode(), sort.getName());
		}
		for (OrderEnum order : OrderEnum.values()) {
			orderMap.put(order.getCode(), order.getName());
		}
		Sort sort = null;
		for (int i = 0; i < list.size(); i++) {
			String[] sortOrderArr = list.get(i).split(":");
			int sortCode = Integer.parseInt(sortOrderArr[0]);
			int orderCode = Integer.parseInt(sortOrderArr[1]);
			if (i == 0) {
				sort = orderMap.get(orderCode) == OrderEnum.ASC.getName() ? Sort.by(sortMap.get(sortCode)).ascending() : Sort.by(sortMap.get(sortCode)).descending();
			} else {
				sort = orderMap.get(orderCode) == OrderEnum.ASC.getName() ? sort.and(Sort.by(sortMap.get(sortCode)).ascending()) : sort.and(Sort.by(sortMap.get(sortCode)).descending());
			}
		}
		log.info(sort + "");
		return sort;
	}

	/**
	 * 다중 정렬 배열을 받아 정렬 우선순위에 맞춰 Sort 클래스 생성
	 *
	 * @param int[]
	 * @return Sort
	 */
	public Sort getSort(int[] sortArr) {
		Sort sort = null;
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

		Pageable firstPageWithTwoElements = PageRequest.of(page, 20, makeSortArr(sortPriorityStr));
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
		Pageable firstPageWithTwoElements = PageRequest.of(page, 20, makeSortArr(sortPriorityStr));
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

	/**
	 * 최저가 갱신 프로그램
	 *
	 * @param standardProductSeq 갱신할 기준 상품 seq
	 */
	public void findLowestPrice(String standardProductSeq) {
		StandardProduct standardProduct = standardProductRepository.findBySeq(standardProductSeq);
		ArrayList<CooperationProduct> cooperationProductList = cooperationProductRepository.findByStandardProductSeq(standardProductSeq);
		int minPrice = standardProduct.getLowestPrice();
		int minMobilePrice = standardProduct.getMobileLowestPrice();
		int averagePrice = 0;
		for (CooperationProduct cooperationProduct : cooperationProductList) {
			averagePrice += cooperationProduct.getPrice();
			if (standardProduct.getCooperationCompanyCount() == 0) {
				minPrice = cooperationProduct.getPrice();
				minMobilePrice = cooperationProduct.getMobilePrice();
			} else if (minPrice > cooperationProduct.getPrice()) {
				minPrice = cooperationProduct.getPrice();
			} else if (minMobilePrice > cooperationProduct.getMobilePrice()) {
				minMobilePrice = cooperationProduct.getMobilePrice();
			}
		}
		if (cooperationProductList.size() == 0) {
			minPrice = 0;
			minMobilePrice = 0;
		}
		try {
			averagePrice = averagePrice / cooperationProductList.size();
		} catch (ArithmeticException e) {
			averagePrice = 0;
		}

		StandardProduct newStandardProduct = standardProduct.updatePrice(StandardProduct.builder().cooperationCompanyCount(cooperationProductList.size()).lowestPrice(minPrice).mobileLowestPrice(minMobilePrice).averagePrice(averagePrice).combinedLowestPrice(minPrice < minMobilePrice ? minPrice : minMobilePrice).build());
		standardProductRepository.save(newStandardProduct);
	}

}
