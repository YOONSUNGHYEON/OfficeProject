package office.dto;

import org.springframework.data.domain.Page;


public class PageResult<T> extends ResultResponse{

	Page<T> pages;

	public PageResult(Page<T> pages, int code, String msg) {
		super(code, msg);
		this.pages = pages;
	}


}
