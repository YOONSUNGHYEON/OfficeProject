package office.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import office.dto.category.CategoryListResponse;
import office.dto.category.CategoryResponse;
import office.service.CategoryService;

@RestController
@RequiredArgsConstructor
public class CategoryController {
	private static final Logger log = LoggerFactory.getLogger(CategoryController.class);
	private final CategoryService categoryService;

	/**
	 * @return
	 */
	@GetMapping(value = "/categorys")
	public CategoryListResponse findCategoryList() {
		List<CategoryResponse> categoryList = categoryService.findAll();
		CategoryListResponse categoryListResponse = new CategoryListResponse(categoryList, 200, "조회 성공");
		log.info("findCategoryList");
		return categoryListResponse;
	}
}
