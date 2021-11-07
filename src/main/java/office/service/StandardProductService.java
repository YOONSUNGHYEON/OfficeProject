package office.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import office.dto.SortEnum;
import office.entity.StandardProduct;
import office.repository.StandardProductRepository;

@Service
@RequiredArgsConstructor
public class StandardProductService {

	private static final Logger log = LoggerFactory.getLogger(StandardProductService.class);

	private final StandardProductRepository StandardProductRepository;

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
			if(sortOrderList[1].equals("1")) {
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
	public Page<StandardProduct> findByCategorySeq(long categorySeq, String sortPriorityStr) {
		log.info(sortPriorityStr);
		Pageable firstPageWithTwoElements = PageRequest.of(0, 5, getSort(sortPriorityStr));
		Page<StandardProduct> standardProductList = StandardProductRepository
				.findAllByCategorySeqAndLowestPrice(categorySeq, 0, firstPageWithTwoElements);
		// List<StandardProductDTO> standardProductDTOList = new
		// ArrayList<StandardProductDTO>();
		// for (StandardProduct StandardProduct : standardProductList) {
		// standardProductDTOList.add(StandardProductDTO.builder().averagePrice(3000).build());
		// }
		return standardProductList;
	}

}
