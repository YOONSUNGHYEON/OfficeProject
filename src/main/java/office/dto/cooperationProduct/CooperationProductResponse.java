package office.dto.cooperationProduct;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import office.entity.Category;
import office.entity.StandardProduct;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CooperationProductResponse {
	private String cooperationProductSeq;
	private String cooperationCompanySeq;
	private String cooperationCompanyName;
	private StandardProduct standardProduct;
	private Category category;
	private String name;
	private String URL;
	private int price;
	private int mobilePrice;
	private String imageURL;
	private LocalDateTime inputDate;
}
