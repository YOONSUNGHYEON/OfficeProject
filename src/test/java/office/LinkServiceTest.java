package office;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import office.repository.CategoryRepository;
import office.repository.CooperationProductRepository;
import office.repository.LinkRepository;
import office.repository.StandardProductRepository;
import office.service.LinkService;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource("classpath:application-dev.properties")
@DisplayName("카테고리 레포지토리 시험하기")
public class LinkServiceTest {

	@Autowired
	LinkService linkService;
	@Autowired
	LinkRepository linkRepository;
	@Autowired
	StandardProductRepository standardProductRepository;
	@Autowired
	CooperationProductRepository cooperationProductRepository;
	@Autowired
	CategoryRepository categoryRepository;
/*
	@Test
	@BeforeEach
	@DisplayName("기준상품 저장하기")
	public void saveStandardProduct() {
		LocalDateTime now = LocalDateTime.now();
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));
		int code = (int) (Math.random() * 100);
		StandardProduct standardProduct = StandardProduct.builder().imageSource("1").seq("test").category(categoryRepository.findBySeq((long) 1)).name("testName").imageURL("x20211104152304.jpg").description("ddddd").manufactureDate("2021-03-11").build();
		standardProductRepository.save(standardProduct);

	}

	@Test
	@BeforeEach
	@DisplayName("협력사 상품 저장하기")
	public void saveCooperationProduct() throws ParseException {
		LocalDateTime now = LocalDateTime.now();
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));
		int code = (int) (Math.random() * 100);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = "2000-05-01";
		Date date = new Date(sdf.parse(strDate).getTime());
		CooperationProduct cooperationProduct = new CooperationProduct("test", new CooperationCompany("ED901", "신세계몰"), new Category(Long.valueOf(1), "주방가전"), "test", "test", 10000, 10000, "test",date);
		cooperationProductRepository.save(cooperationProduct);
	}



	@Test
	@Order(1)
	@DisplayName("링크 생성하기")
	public void link() {
		linkService.link(new LinkReqeust("test" ,"test", "ED901"));
		Link link = null;
		link = linkRepository.findByStandardProductSeqAndCooperationProductSeqAndCooperationCompanySeq("test", "test", "ED901");

		assertEquals(null, link);
	}
*/
}
