package office.dto.standardProduct;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.Setter;
import office.dto.PageResult;

@Getter
@Setter
public class StandardProductListResponse extends PageResult {

	public StandardProductListResponse(Page<StandardProductResponse> pages, int code, String msg) {
		super(pages, code, msg);
	}
}
