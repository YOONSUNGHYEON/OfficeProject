package office.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import office.entity.StandardProduct;

public interface StandardProductRepository extends JpaRepository<StandardProduct, String> {

	List<StandardProduct> findByCategorySeq(long categorySeq);

	Page<StandardProduct> findAllByCategorySeqAndLowestPrice(long categorySeq, int lowestPrice, Pageable pageable);
}
