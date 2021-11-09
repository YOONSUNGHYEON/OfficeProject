package office.dto.sort;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderEnum {
	DESC(1, "desc"),
	ASC(2, "asc");
	private int code;
    private final String name;
}
