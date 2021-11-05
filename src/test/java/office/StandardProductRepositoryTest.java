package office;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import office.entity.StandardProduct;
import office.repository.CategoryRepository;
import office.repository.StandardProductRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource("classpath:application-dev.properties")
@DisplayName("기준 상품 레포지토리 시험하기")
public class StandardProductRepositoryTest {
	@Autowired
	StandardProductRepository standardProductRepository;
	@Autowired
	CategoryRepository categoryRepository;

	@Test
	@BeforeEach
	@DisplayName("기준상품 저장하기")
	public void save() {

		LocalDateTime now = LocalDateTime.now();
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));
		for(int i=0; i<10; i++) {
			int code = (int)(Math.random() * 100);
			StandardProduct standardProduct = StandardProduct.builder()
					.imageSource("1")
					.seq(formatedNow+code)
					.category(categoryRepository.findBySeq((long)1))
					.name("testName")
					.imageURL("x20211104152304.jpg")
					.description("ddddd")
					.manufactureDate("2021-03-11")
					.build();
			standardProductRepository.save(standardProduct);
		}


	}
	@Test
	@DisplayName("기준 상품 목록 조회")
	public void findAll() {
		assertEquals(10, standardProductRepository.findAll().size());
	}
	@Test
	@DisplayName("페이지 네이션 테스트")
	public void pagingTest() {
		Pageable firstPageWithTwoElements = PageRequest.of(0, 5);
		Page<StandardProduct> allStandardProducts = standardProductRepository.findAllByCategorySeqAndLowestPrice(1,0,firstPageWithTwoElements);
		assertEquals(5, allStandardProducts.getSize());
	}
}
