package office.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SortEnum {

	SEQ(6, "seq"),
	NAME(1, "name"),
	LOWEST_PRICE(2, "lowestPrice"),
	MOBILE_LOWEST_PRICE(3, "mobileLowestPrice");




	int code;
	String name;

	public String getName() {
		return this.name;
	}

	public int getCode() {
		return this.code;
	}

}
