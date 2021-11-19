package office.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import groovy.transform.builder.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@Table(name = "tLinkList")
@IdClass(LinkID.class)
public class Link {

	@Id
	@ManyToOne
	@Column(name = "sStandardProductSeq")
	private StandardProduct standardProduct;

	@Id
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumns(value = { @JoinColumn(name = "sCooperationProductSeq", updatable = false, insertable = false),
			@JoinColumn(name = "sCooperationCompanySeq", updatable = false, insertable = false) })
	private CooperationProduct cooperationProduct;


}
