package office.dto;

import org.springframework.data.domain.Page;

import lombok.Getter;

@Getter
public class PageResult<T> extends ResultResponse{

	Page<T> page;

	public PageResult(Page<T> page, int code, String msg) {
		super(code, msg);
		this.page = page;
	}


}
