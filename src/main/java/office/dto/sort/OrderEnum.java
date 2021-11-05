package office.dto.sort;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderEnum {
	DESC(1, "내림차순"),
	ASC(2, "오름차순");
	private int code;
    private final String name;
}
