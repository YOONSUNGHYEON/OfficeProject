package office.dto.standardProduct;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.Setter;
import office.dto.PageResult;

@Getter
@Setter
public class StandardProductListResponse extends PageResult {
	private String sortOrder;
	public StandardProductListResponse(String sortOrder, Page<StandardProductResponse> pages, int code, String msg) {
		super(pages, code, msg);
		this.sortOrder = sortOrder;
	}
}
