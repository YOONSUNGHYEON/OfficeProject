package office.service;

import java.util.ArrayList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import office.dto.cooperationProduct.CooperationProductResponse;
import office.dto.sort.CooperationProductSortEnum;
import office.dto.sort.OrderEnum;
import office.entity.CooperationProduct;
import office.repository.CooperationProductRepository;

@Service
@RequiredArgsConstructor
public class CooperationProductService {
	private static final Logger log = LoggerFactory.getLogger(CooperationProductService.class);
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
		Map<Integer, String> orderEnumMap = OrderEnum.makeMap();
		Map<Integer, String> sortEnumMap = CooperationProductSortEnum.makeMap();
		log.info(multiSortOrderStr);
		Sort sort = null;

		for (int i = 0; i < list.size(); i++) {
			String[] sortOrderArr = list.get(i).split(":");
			int sortCode = Integer.parseInt(sortOrderArr[0]);
			int orderCode = Integer.parseInt(sortOrderArr[1]);
			if (i == 0) {
				sort = orderEnumMap.get(orderCode) == OrderEnum.DESC.getName() ? Sort.by(sortEnumMap.get(sortCode)).ascending() : Sort.by(sortEnumMap.get(sortCode)).descending();
			} else {
				sort = orderEnumMap.get(orderCode) == OrderEnum.DESC.getName() ? sort.and(Sort.by(sortEnumMap.get(sortCode)).ascending()) : sort.and(Sort.by(sortEnumMap.get(sortCode)).descending());
			}
		}
		return sort;
	}

	/**
	 * 카테고리에 해당하는 링크된 협력사 상품 목록을 조회
	 *
	 * @param categorySeq 카테고리 Seq
	 * @param multiSortOrderStr 다중 정렬 할 칼럼의 코드와 나열 순서가 한 묶음으로 무작위로 이어져있는 문자열 형태 ex)
	 *                          5:1,1:2,3:2
	 * @return Page
	 */
	public Page<CooperationProductResponse> findLinkProductByCategory(int categorySeq, int page, String multiSortOrderStr) {
		Page<CooperationProduct> cooperationProductPage = cooperationProductRepository.findAllByCategorySeqAndStandardProductSeqNotNull(categorySeq, PageRequest.of(page, 20, getSort(multiSortOrderStr)));
		Page<CooperationProductResponse> cooperationProductResponse = cooperationProductPage.map(cooperationProduct -> cooperationProduct.toDTO());
		return cooperationProductResponse;
	}
	/**
	 * 카테고리에 해당하는 링크 안된 협력사 상품 목록을 조회
	 *
	 * @param categorySeq 카테고리 Seq
	 * @param multiSortOrderStr 다중 정렬 할 칼럼의 코드와 나열 순서가 한 묶음으로 무작위로 이어져있는 문자열 형태 ex)
	 *                          5:1,1:2,3:2
	 * @return Page
	 */
	public Page<CooperationProductResponse> findUnlinkProductByCategory(long categorySeq,int page, String multiSortOrderStr) {
		Page<CooperationProduct> cooperationProductPage = cooperationProductRepository.findAllByCategorySeqAndStandardProductSeq(categorySeq, null, PageRequest.of(page, 20, getSort(multiSortOrderStr)));
		Page<CooperationProductResponse> cooperationProductResponse = cooperationProductPage.map(cooperationProduct -> cooperationProduct.toDTO());
		return cooperationProductResponse;

	}

	/**
	 * 기준 상품에 링크 되어있는 협력사 상품 조회
	 *
	 * @param standardProductSeq 기준상품 코드
	 * @param multiSortOrderStr 다중 정렬 할 칼럼의 코드와 나열 순서가 한 묶음으로 무작위로 이어져있는 문자열 형태 ex)
	 *                          5:1,1:2,3:2
	 * @return Page
	 */
	public Page<CooperationProductResponse> findByStandardProductSeq(String standardProductSeq, String multiSortOrderStr) {
		Page<CooperationProduct> cooperationProductPage = cooperationProductRepository.findByStandardProductSeq(standardProductSeq, PageRequest.of(0, 20, getSort(multiSortOrderStr)));
		Page<CooperationProductResponse> cooperationProductResponse = cooperationProductPage.map(cooperationProduct -> cooperationProduct.toDTO());
		return cooperationProductResponse;
	}





}
