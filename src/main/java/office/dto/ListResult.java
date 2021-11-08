package office.dto;

import java.util.List;


public class ListResult<T> extends ResultResponse {
    private List<T> list;
    private String sortOption;


	public ListResult(List<T> list, int code, String msg) {
		super(code, msg);
		this.list = list;
	}

	public ListResult(List<T> list, ResultResponse resultResponse, String sortOption) {
		super(resultResponse.getCode(), resultResponse.getMsg());
		this.list = list;
		this.sortOption = sortOption;
	}
	public ListResult(List<T> list, ResultResponse resultResponse) {
		super(resultResponse.getCode(), resultResponse.getMsg());
		this.list = list;
	}


}