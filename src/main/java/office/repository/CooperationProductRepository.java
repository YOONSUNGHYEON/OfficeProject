package office.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import office.entity.CooperationProduct;
import office.entity.CooperationProductID;

public interface CooperationProductRepository extends PagingAndSortingRepository<CooperationProduct, CooperationProductID>{


	Page<CooperationProduct> findAllByCategorySeqAndStandardProductSeq(long categorySeq, String standardProductSeq, Pageable pageable);

	void save(Optional<CooperationProduct> cooperationProduct);

	CooperationProduct findByCooperationProductSeqAndCooperationCompanySeq(String cooperationProductSeq, String cooperationCompanySeq);


}
