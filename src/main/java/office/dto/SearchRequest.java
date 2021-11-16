package office.dto;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchRequest {
	@NotNull
	int categorySeq;
	@NotNull
	int page;
	@NotNull
	String sortOrder;
}
