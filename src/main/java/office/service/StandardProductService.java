package office.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import office.dto.standardProduct.StandardProductResponse;
import office.entity.StandardProduct;
import office.repository.StandardProductRepository;

@Service
@RequiredArgsConstructor
public class StandardProductService {
	private final StandardProductRepository standardProductRepository;

	/**
	 * @param categorySeq
	 * @return List<StandardProductDTO>
	 */
	public Page<StandardProductResponse> findUnLinkProductByCategory(long categorySeq) {
		Pageable firstPageWithTwoElements = PageRequest.of(0, 20);
		Page<StandardProduct> allStandardProducts = standardProductRepository.findAllByCategorySeqAndLowestPrice(categorySeq,0,firstPageWithTwoElements);
		Page<StandardProductResponse> standardProductPageResponse = allStandardProducts.map(standardProduct -> standardProduct.toDTO());
		return standardProductPageResponse;
	}

}


