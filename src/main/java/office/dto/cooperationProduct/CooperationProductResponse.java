package office.dto.cooperationProduct;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class CooperationProductResponse {
	private String cooperationProductSeq;
	private String cooperationCompanySeq;
	private String cooperationCompanyName;
	private String categoryName;
	private String name;
	private String URL;
	private int price;
	private int mobilePrice;
	private String imageURL;
	private String inputDate;

	public CooperationProductResponse(String cooperationProductSeq, String cooperationCompanySeq, String cooperationCompanyName,  String categoryName, String name, String URL, int price, int mobilePrice, String imageURL, String inputDate) {
		this.cooperationProductSeq = cooperationProductSeq;
		this.cooperationCompanySeq = cooperationCompanySeq;
		this.cooperationCompanyName = cooperationCompanyName;
		this.categoryName = categoryName;
		this.name = name;
		this.URL = URL;
		this.price = price;
		this.mobilePrice = mobilePrice;
		this.imageURL = imageURL;
		this.inputDate = inputDate;
	}






}
