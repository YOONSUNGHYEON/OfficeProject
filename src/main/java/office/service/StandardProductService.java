package office.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import office.dto.StandardProductDTO;
import office.entity.StandardProduct;
import office.repository.StandardProductRepository;

@Service
@RequiredArgsConstructor
public class StandardProductService {
	private final StandardProductRepository StandardProductRepository;

	/**
	 * @param categorySeq
	 * @return List<StandardProductDTO>
	 */
	public List<StandardProductDTO> findByCategorySeq(long categorySeq) {
		List<StandardProduct> standardProductList = StandardProductRepository.findByCategorySeq(categorySeq);
		List<StandardProductDTO> standardProductDTOList = new ArrayList<StandardProductDTO>();
		for (StandardProduct StandardProduct : standardProductList) {
			standardProductDTOList.add(StandardProductDTO.builder().averagePrice(3000).build());
		}
		return standardProductDTOList;
	}

}


