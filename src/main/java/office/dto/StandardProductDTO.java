package office.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StandardProductDTO {
	private int seq;
	private int categorySeq;
	private String name;
	private String imageSource;
	private String imageSourceURL;
	private String manufactureDate;
	//private int lowestPrice;
	//private int mobileLowestPrice;
	//private int averagePrice;
	//private int cooperationComanyCount;

	public StandardProductDTO() {

	}

}
