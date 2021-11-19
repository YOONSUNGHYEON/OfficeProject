package office.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class LinkID implements Serializable {
	@EqualsAndHashCode.Include
	@Id
	private String standardProduct;

	@EqualsAndHashCode.Include
	@Id
	private CooperationProduct cooperationProduct;



}
