package office.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchRequest {
	int linkOption;
	int categorySeq;
	int page;
}
