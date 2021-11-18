package office.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import groovy.transform.builder.Builder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "tLinkList")
public class Link {


	@Id
	@Column(name="nLinkSeq", updatable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long linkSeq;

	@ManyToOne
    @JoinColumn(name="sStandardProductSeq")
	private StandardProduct standardProduct;

	@ManyToOne
	@JoinColumns({
         @JoinColumn(name="cooperationProductSeq"),
         @JoinColumn(name="cooperationCompanySeq")
	 })
	private CooperationProduct cooperationProduct;

	public Link(StandardProduct standardProduct, CooperationProduct cooperationProduct) {
		this.standardProduct = standardProduct;
		this.cooperationProduct = cooperationProduct;
	}
}
