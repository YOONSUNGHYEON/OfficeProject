package office.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import office.entity.StandardProduct;

public interface StandardProductRepository extends PagingAndSortingRepository<StandardProduct, String>{

	List<StandardProduct> findByCategorySeq(long categorySeq);

	Page<StandardProduct> findAllByCategorySeqAndLowestPrice(long categorySeq, int lowerPrice, Pageable pageable);
	@Override
	List<StandardProduct> findAll();

}
