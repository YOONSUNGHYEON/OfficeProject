package office.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import office.dto.cooperationProduct.CooperationProductResponse;
import office.dto.sort.SortEnum;
import office.entity.CooperationProduct;
import office.entity.StandardProduct;
import office.repository.CooperationProductRepository;

@Service
@RequiredArgsConstructor
public class CooperationProductService {
	private static final Logger log = LoggerFactory.getLogger(CooperationProductService.class);

	private final CooperationProductRepository cooperationProductRepository;

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

	public Page<CooperationProductResponse> findLinkProductByCategory(int categorySeq, String sortOrder) {
		Page<CooperationProduct> cooperationProductPage = cooperationProductRepository.findAllByCategorySeqAndStandardProductSeqNotNull(categorySeq, PageRequest.of(0, 20));
		Page<CooperationProductResponse> cooperationProductResponse = cooperationProductPage.map(cooperationProduct -> cooperationProduct.toDTO());
		return cooperationProductResponse;
	}

	public Page<CooperationProductResponse> findUnlinkProductByCategory(long categorySeq, String sortPriorityStr) {
		Page<CooperationProduct> cooperationProductPage = cooperationProductRepository.findAllByCategorySeqAndStandardProductSeq(categorySeq, null, PageRequest.of(0, 20));
		Page<CooperationProductResponse> cooperationProductResponse = cooperationProductPage.map(cooperationProduct -> cooperationProduct.toDTO());
		return cooperationProductResponse;

	}

	public Page<CooperationProductResponse> findByStandardProductSeq(String standardProductSeq) {
		Page<CooperationProduct> cooperationProductPage = cooperationProductRepository.findByStandardProductSeq(standardProductSeq, PageRequest.of(0, 20));
		Page<CooperationProductResponse> cooperationProductResponse = cooperationProductPage.map(cooperationProduct -> cooperationProduct.toDTO());
		return cooperationProductResponse;
	}

	public CooperationProductResponse link(String standardProductSeq, String cooperationProductSeq, String cooperationCompanySeq) {
		CooperationProduct cooperationProduct = cooperationProductRepository.findByCooperationProductSeqAndCooperationCompanySeq(cooperationProductSeq, cooperationCompanySeq);
		StandardProduct standardProduct = StandardProduct.builder().seq(standardProductSeq).build();
		CooperationProductResponse cooperationProductResponse = cooperationProductRepository.save(cooperationProduct.updateStandardProduct(standardProduct)).toDTO();
		return cooperationProductResponse;
	}

	public CooperationProductResponse unlink(String standardProductSeq, String cooperationProductSeq, String cooperationCompanySeq) {
		CooperationProduct cooperationProduct = cooperationProductRepository.findByCooperationProductSeqAndCooperationCompanySeq(cooperationProductSeq, cooperationCompanySeq);
		CooperationProductResponse cooperationProductResponse = cooperationProductRepository.save(cooperationProduct.updateStandardProduct(null)).toDTO();
		return cooperationProductResponse;
	}

}
