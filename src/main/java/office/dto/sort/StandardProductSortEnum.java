package office.dto.sort;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StandardProductSortEnum {
	SEQ(6,"seq"),
	NAME(1,"name"),
	COMBINED_LOWEST_PRICE(2,"combinedLowestPrice"),
	LOWEST_PRICE(3,"lowestPrice"),
	MOBILE_LOWEST_PRICE(4,"mobileLowestPrice"),
	COOPERATION_COMPANY_COUNT(5, "cooperationCompanyCount");
	private final int code;
    private final String name;
}
