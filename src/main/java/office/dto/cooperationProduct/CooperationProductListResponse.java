package office.dto.cooperationProduct;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.Setter;
import office.dto.PageResult;

@Getter
@Setter
public class CooperationProductListResponse extends PageResult {
	private String sortOrder;
	public CooperationProductListResponse(String sortOrder, Page<CooperationProductResponse> pages, int code, String msg) {
		super(pages, code, msg);
		this.sortOrder = sortOrder;
	}
}
