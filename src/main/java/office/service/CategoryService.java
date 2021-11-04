package office.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import office.dto.CategoryDTO;
import office.entity.Category;
import office.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepository categoryRepository;

	/**
	 * 카테고리 목록 조회
	 * @return ArrayList<CategoryDTO>
	 */
	public List<CategoryDTO> findAll() {
		List<Category> categoryList = categoryRepository.findAll();
		List<CategoryDTO> categoryDTOList = new ArrayList<>();
		for (Category category : categoryList) {
			categoryDTOList.add(new CategoryDTO(category.getSeq(), category.getName()));
		}
		return categoryDTOList;
	}
}
