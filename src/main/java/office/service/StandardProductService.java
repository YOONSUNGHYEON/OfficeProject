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
import office.entity.StandardProduct;
import office.repository.StandardProductRepository;

@Service
@RequiredArgsConstructor
public class StandardProductService {

	private static final Logger log = LoggerFactory.getLogger(StandardProductService.class);

	private final StandardProductRepository standardProductRepository;

	/**
	 * @param sortPriorityStr
	 * @return
	 */
	public Sort getSort(String sortPriorityStr) {
		Sort sort = null;
		String[] sortPriorityList = sortPriorityStr.split(",");
		SortEnum[] SortEnumList = SortEnum.values();
		for (String sortOrder : sortPriorityList) {
			String[] sortOrderList = sortOrder.split(":");
			for (SortEnum sortEnum : SortEnumList) {
				if (sortEnum.getCode() == Integer.parseInt(sortOrderList[0])) {
					sort = sort.by(sortEnum.getName());
				}
			}
			if (sortOrderList[1].equals("1")) {
				sort = sort.ascending();
			} else {
				sort = sort.descending();
			}
		}
		return sort;
	}

	/**
	 * @param categorySeq
	 * @return List<StandardProductDTO>
	 */
	public Page<StandardProductResponse> findUnLinkProductByCategory(long categorySeq, String sortPriorityStr) {
		Pageable firstPageWithTwoElements = PageRequest.of(0, 20, getSort(sortPriorityStr));
		Page<StandardProduct> allStandardProducts = standardProductRepository
				.findAllByCategorySeqAndLowestPrice(categorySeq, 0, firstPageWithTwoElements);
		Page<StandardProductResponse> standardProductPageResponse = allStandardProducts
				.map(standardProduct -> standardProduct.toDTO());
		return standardProductPageResponse;

	}

	public void update() {

	}

	public StandardProductResponse findBySeq(String standardProductSeq) {
		StandardProduct standardProduct =  standardProductRepository.findBySeq(standardProductSeq);
		return standardProduct.toDTO();
	}

	public void findLowestPrice(ArrayList<Integer> priceList, ArrayList<Integer> mobilePriceList,
			StandardProductResponse standardProductResponse) {
		int averagePrice = 0;
		int minPrice = priceList.get(0);
		int minMobilePrice = mobilePriceList.get(0);
		StandardProduct standardProduct;
		for(int i=1; i<priceList.size(); i++) {
			averagePrice += priceList.get(i) + mobilePriceList.get(i);
			if(minPrice > priceList.get(i)) {
				minPrice = priceList.get(i);
			}
			if(minMobilePrice > mobilePriceList.get(i)) {
				minMobilePrice = mobilePriceList.get(i);
			}
		}
		if(standardProductResponse.getLowestPrice() == 0 || standardProductResponse.getLowestPrice()>minPrice) {
			standardProduct = StandardProduct.builder().lowestPrice(minPrice).build();
		}
		if(standardProductResponse.getLowestPrice() == 0 || standardProductResponse.getLowestPrice()>minPrice) {
			standardProduct = StandardProduct.builder().lowestPrice(minPrice).build();
		}

	}

}
