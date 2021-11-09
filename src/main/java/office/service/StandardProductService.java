package office.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import office.dto.sort.SortEnum;
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
	 * 선택된 다중 정렬 문자열을 받아서 배열로 변환 </br>
	 * ex) 1:2,2:1 -> arr = {2, 1}
	 *
	 * @param sortPriorityStr
	 * @return int[] sortArr
	 */
	public int[] makeSortArr(String sortPriorityStr) {
		String[] sortPriorityList = sortPriorityStr.split(",");
		int[] sortArr = new int[7];
		for (String sortOrder : sortPriorityList) {
			String[] sortOrderList = sortOrder.split(":");
			sortArr[Integer.parseInt(sortOrderList[0])] = Integer.parseInt(sortOrderList[1]);
		}
		return sortArr;
	}

	/**
	 * 다중 정렬 배열을 받아 정렬 우선순위에 맞춰 Sort 클래스 생성
	 *
	 * @param int[]
	 * @return Sort
	 */
	public Sort getSort(int[] sortArr) {

		Sort sort = null;
		if (sortArr[1] != 0) {
			sort = sortArr[1] == 1 ? sort.by(SortEnum.NAME.getName()).ascending() : sort.by(SortEnum.NAME.getName()).descending();
		}
		if (sortArr[2] != 0) {
			sort = sortArr[2] == 1 ? sort.and(sort.by(SortEnum.COMBINED_LOWEST_PRICE.getName()).ascending()) : sort.and(sort.by(SortEnum.COMBINED_LOWEST_PRICE.getName()).descending());
		}
		if (sortArr[3] != 0) {
			sort = sortArr[3] == 1 ? sort.and(sort.by(SortEnum.LOWEST_PRICE.getName()).ascending()) : sort.and(sort.by(SortEnum.LOWEST_PRICE.getName()).descending());
		}
		if (sortArr[4] != 0) {
			sort = sortArr[4] == 1 ? sort.and(sort.by(SortEnum.MOBILE_LOWEST_PRICE.getName()).ascending()) : sort.and(sort.by(SortEnum.MOBILE_LOWEST_PRICE.getName()).descending());
		}
		if (sortArr[5] != 0) {
			sort = sortArr[5] == 1 ? sort.and(sort.by(SortEnum.COOPERATION_COMPANY_COUNT.getName()).ascending()) : sort.and(sort.by(SortEnum.COOPERATION_COMPANY_COUNT.getName()).descending());
		}
		if (sortArr[6] != 0) {
			sort = Sort.by("seq").descending();
		}

		log.info(sort.toString());
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

		Pageable firstPageWithTwoElements = PageRequest.of(page, 20, getSort(makeSortArr(sortPriorityStr)));
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
		Pageable firstPageWithTwoElements = PageRequest.of(page, 20, getSort(makeSortArr(sortPriorityStr)));
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
