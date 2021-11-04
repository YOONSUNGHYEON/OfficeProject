package office.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import office.entity.Category;

public interface  CategoryRepository extends JpaRepository<Category, Long>{

	@Override
	Category save(Category category);

	@Override
	List<Category> findAll();
}
