package office.dto.cooperationProduct;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CooperationProductListLinkReqeust {
	String StandardProductSeq;
	CooperationProductIDRequest[] cooperationProductID;
}
