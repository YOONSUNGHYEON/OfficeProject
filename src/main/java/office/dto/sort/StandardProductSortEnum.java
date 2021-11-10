package office.dto.sort;

import java.util.HashMap;
import java.util.Map;

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

    /**
	 * SortEnum을 Map형태로 반환을 해준다.<br>
	 * key는 정렬 우선순위, value는 정렬 영어 이름 <br>
	 *
	 * @return Map<Integer, String> sortEnumMap
	 */
	public static Map<Integer, String> makeMap() {
		Map<Integer, String> sortEnumMap = new HashMap<Integer, String>();
		for (StandardProductSortEnum sort : StandardProductSortEnum.values()) {
			sortEnumMap.put(sort.getCode(), sort.getName());
		}
		return sortEnumMap;
	}
}
