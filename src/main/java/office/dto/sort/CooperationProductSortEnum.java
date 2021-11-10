package office.dto.sort;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CooperationProductSortEnum {
    SEQ(6,"cooperationCompany"),
	NAME(2,"name"),
	INPUT_DATE(3,"inputDate");
	private final int code;
    private final String name;
}
