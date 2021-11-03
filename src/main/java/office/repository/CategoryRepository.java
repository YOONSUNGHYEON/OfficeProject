package office.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import office.entity.Category;

public interface  CategoryRepository extends JpaRepository<Category, Long>{

	@Override
	Category save(Category category);
}
