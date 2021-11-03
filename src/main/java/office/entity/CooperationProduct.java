package office.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;

import groovy.transform.builder.Builder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Table(name = "tCooperationProduct")
public class CooperationProduct implements Serializable{
	@Id
	@Column(name="sCooperationProductSeq", updatable=false)
	private String cooperationProductSeq;

	@Id
	@Column(name="sCooperationCompanySeq", updatable=false)
	private String cooperationCompanySeq;

	@ManyToOne
    @JoinColumn(name="sStandardProductSeq")
	private StandardProduct standardProduct;

	@ManyToOne
	@JoinColumn(name = "nCategorySeq")
	private Category category;

	@Column(name = "sName", nullable=false)
	private String name;

	@Column(name = "sURL", nullable = false)
	private String URL;

	@Column(name = "nPrice")
	private int price;

	@Column(name = "nMobilePrice")
	private int mobilePrice;

	@Column(name = "sImageURL", nullable = false)
	private String imageURL;

    @CreatedDate
	@Column(name = "dtInputDate", nullable = false)
	private LocalDateTime inputDate;

}
