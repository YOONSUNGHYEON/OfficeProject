package office.dto.standardProduct;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class StandardProductResponse {
	private String seq;
	private Long categorySeq;
	private String name;
	private String imageURL;
	private String manufactureDate;
	private int lowestPrice;
	private int mobileLowestPrice;
	private int averagePrice;
	private int cooperationComanyCount;
}
