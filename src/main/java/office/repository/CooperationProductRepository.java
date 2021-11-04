package office.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import office.entity.Category;

public interface CooperationProductRepository extends JpaRepository<Category, Long>{

}
