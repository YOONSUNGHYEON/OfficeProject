package office.dto.sort;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortEnum {
	SEQ(2,"이름으로 정렬"),
	NAME(2,"이름으로 정렬"),
	COMBINED_LOWEST_PRICE(3,"이름으로 정렬"),
	LOWEST_PRICE(4,"이름으로 정렬"),
	MOBILE_LOWEST_PRICE(5,"이름으로 정렬");

	private final int code;
    private final String name;
}
