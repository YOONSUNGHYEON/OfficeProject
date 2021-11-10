package office.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import office.entity.Category;

public interface  CategoryRepository extends JpaRepository<Category, Long>{


	/**
	 * 카테 고리 전체 목록 조회
	 * @return List<Category>
	 */
	@Override
	List<Category> findAll();

	/**
	 * 카테고리 코드에 해당하는 카테고리 조회
	 * @param seq 카테고리 코드
	 * @return Category
	 */
	Category findBySeq(Long seq);
}
