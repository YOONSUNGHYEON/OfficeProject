package office.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchRequest {
	int categorySeq;
	int page;
	String sortOrder;
}
