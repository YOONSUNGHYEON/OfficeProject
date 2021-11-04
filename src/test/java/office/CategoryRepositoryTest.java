package office;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import office.entity.Category;
import office.repository.CategoryRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource("classpath:application-dev.properties")
@DisplayName("카테고리 레포지토리 시험하기")
public class CategoryRepositoryTest {

	@Autowired
	CategoryRepository categoryRepository;

	@Test
	@Order(1)
	@DisplayName("카테고리 저장하기😱")
	public void save() {
		Category category = new Category((long) 1, "주방가전");
		Category savedCategory = categoryRepository.save(category);
		assertEquals("주방가전", savedCategory.getName());
	}

	@Test
	@Order(2)
	@DisplayName("카테고리 목록 조회하기")
	public void findAll() {
		List<Category> categoryList = categoryRepository.findAll();
		assertEquals(1, categoryList.size());
	}
}
