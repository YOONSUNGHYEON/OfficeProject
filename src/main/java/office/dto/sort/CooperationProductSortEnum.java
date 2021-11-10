package office.dto.sort;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CooperationProductSortEnum {
    SEQ(1,"cooperationCompany"),
	NAME(2,"name"),
	INPUT_DATE(3,"inputDate");
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
   		for (CooperationProductSortEnum sort : CooperationProductSortEnum.values()) {
   			sortEnumMap.put(sort.getCode(), sort.getName());
   		}
   		return sortEnumMap;
   	}

}
