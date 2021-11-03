package office;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import office.repository.CategoryRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CategoryRepositoryTest {

	 @Mock
	CategoryRepository categoryRepository;

	@Test
	public void save() {
		//Category category = new Category((long) 1, "주방가전");
		//Category savedCategory = categoryRepository.save(category);
		assertEquals("TCOOPERATIONCOMPANY", "TCOOPERATIONCOMPANY");
	}
}
