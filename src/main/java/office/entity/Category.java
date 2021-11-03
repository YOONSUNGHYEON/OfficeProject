package office.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import groovy.transform.builder.Builder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tCategory")
public class Category {
	@Id
	@Column(name="nCategorySeq", updatable=false)
	private Long seq;

	@Column(name = "sName", nullable=false)
	private String name;

	@Builder
    public Category(Long seq, String name) {
		this.seq = seq;
		this.name = name;
    }

}
