package office.dto.sort;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderEnum {
	DESC(1, "desc"),
	ASC(2, "asc");
	private int code;
    private final String name;


	/**
	 * OrderEnum을 Map형태로 반환을 해준다.<br>
	 * key는 1 or 2, value는 DESC or ASC <br>
	 *
	 * @return Map<Integer, String> orderEnumrMap
	 */
    public static Map<Integer, String>  makeMap() {
    	Map<Integer, String> orderEnumrMap = new HashMap<Integer, String>();
		for (OrderEnum order : OrderEnum.values()) {
			orderEnumrMap.put(order.getCode(), order.getName());
		}
		return orderEnumrMap;
    }
}
