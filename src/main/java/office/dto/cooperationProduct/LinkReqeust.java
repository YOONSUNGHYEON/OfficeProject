package office.dto.cooperationProduct;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LinkReqeust {
	private String standardProductSeq;
	private String cooperationProductSeq;
	private String cooperationCompanySeq;
}
