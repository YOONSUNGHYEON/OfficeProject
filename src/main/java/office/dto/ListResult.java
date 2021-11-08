package office.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListResult<T> extends ResultResponse {
    private List<T> list;


	public ListResult(List<T> list, int code, String msg) {
		super(code, msg);
		this.list = list;
	}
}