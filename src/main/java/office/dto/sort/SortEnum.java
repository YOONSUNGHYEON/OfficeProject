package office.dto.sort;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortEnum {
	SEQ(6,"seq"),
	NAME(1,"name"),
	COMBINED_LOWEST_PRICE(2,"combinedLowestPrice"),
	LOWEST_PRICE(3,"lowestPrice"),
	COOPERATION_COMPANY_COUNT(4, "cooperationCompanyCount"),
	MOBILE_LOWEST_PRICE(4,"mobileLowestPrice");

	private final int code;
    private final String name;
}
