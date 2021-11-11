package office.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import office.entity.StandardProduct;

public interface StandardProductRepository extends PagingAndSortingRepository<StandardProduct, String> {


	/**
	 *
	 *
	 * @param categorySeq
	 * @param lowerPrice
	 * @param pageable
	 * @return Page
	 */
	Page<StandardProduct> findAllByCategorySeqAndLowestPrice(long categorySeq, int lowerPrice, Pageable pageable);

	/**
	 * 기준 상품 테이블에서 categorySeq와 같은 카테고리 코드를 가지고 있고 <br>
	 *  업체수가 cooperationCompanyCount보다 큰 값을 가지고 있는 기준 상품 목록을 page형식으로 조회
	 *
	 * @param categorySeq 카테고리 코드
	 * @param cooperationCompanyCount
	 * @param pageable
	 * @return Page
	 */
	Page<StandardProduct> findAllByCategorySeqAndCooperationCompanyCountGreaterThan(long categorySeq, int cooperationCompanyCount, Pageable pageable);

	@Override
	List<StandardProduct> findAll();

	/**
	 * 기준 상품 테이블에서 seq와 같은 기준 상품 코드를 가지고 있는 기준 상품 조회
	 * @param seq 기준 상품 코드
	 * @return StandardProduct
	 */
	StandardProduct findBySeq(String seq);

}
