package office.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import office.entity.CooperationProduct;
import office.entity.CooperationProductID;

public interface CooperationProductRepository extends PagingAndSortingRepository<CooperationProduct, CooperationProductID> {

	/**
	 * 협력사 상품 테이블에서 categorySeq와 같은 카테고리 코드를 가지고 있고 standardProductSeq와 같은 기준 상품 코드를 가지고 있는 협력사 상품목록을 Page형식으로 조회
	 * @param categorySeq 카테고리 코드
	 * @param standardProductSeq 기준 상품 코드
	 * @param pageable 조회할 페이지 번호, 한 페이지 당 조회 갯 수, 정렬 기준 (정렬할 기준컬럼,ASC|DESC)
	 * @return Page

	Page<CooperationProduct> findAllByCategorySeqAndStandardProductSeq(long categorySeq, String standardProductSeq, Pageable pageable);
 */
	/**
	 * 협력사 상품 테이블에서 cooperationProductSeqd와  cooperationCompanySeq를 복합키로 가지고 있는 협력사 상품을 조회
	 * @param cooperationProductSeq 협력사 상품 코드
	 * @param cooperationCompanySeq 협력사 코드
	 * @return CooperationProduct 조회할 페이지 번호, 한 페이지 당 조회 갯 수, 정렬 기준 (정렬할 기준컬럼,ASC|DESC)
	 */
	CooperationProduct findByCooperationProductSeqAndCooperationCompanySeq(String cooperationProductSeq, String cooperationCompanySeq);

	/**
	 * 협력사 상품 테이블에서 standardProductSeq와 같은 기준 상품 코드를 가지고 있는 협력사 상품목록을 ArrayList형식으로 조회
	 * @param standardProductSeq 기준 상품 코드
	 * @return ArrayList

	ArrayList<CooperationProduct> findByStandardProductSeq(String standardProductSeq);
 */
	/**
	 * 협력사 상품 테이블에서 standardProductSeq와 같은 기준 상품 코드를 가지고 있는 협력사 상품목록을 Page형식으로 조회
	 * @param standardProductSeq 기준 상품 코드
	 * @param pageable 조회할 페이지 번호, 한 페이지 당 조회 갯 수, 정렬 기준 (정렬할 기준컬럼,ASC|DESC)
	 * @return Page

	Page<CooperationProduct> findByStandardProductSeq(String standardProductSeq, Pageable pageable);
 */
	/**
	 * 협력사 상품 테이블에서 categorySeq와 같은 카테고리 코드를 가지고 있고<br>
	 * standardProductSeq 칼럼 데이터가 null이 아닌 협력사 상품 목록을 Page형식으로 조회
	 * @param categorySeq 카테고리 코드
	 * @param pageable 조회할 페이지 번호, 한 페이지 당 조회 갯 수, 정렬 기준 (정렬할 기준컬럼,ASC|DESC)
	 * @return Page

	Page<CooperationProduct> findAllByCategorySeqAndStandardProductSeqNotNull(long categorySeq, Pageable pageable);
*/
	CooperationProduct getOne(CooperationProductID cooperationProductID);

	Page<CooperationProduct> findAllByCategorySeq(long categorySeq, Pageable pageable);

}
