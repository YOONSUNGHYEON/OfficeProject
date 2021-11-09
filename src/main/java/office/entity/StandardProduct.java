package office.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import office.dto.standardProduct.StandardProductResponse;

@Getter
@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "tStandardProductList")
public class StandardProduct {
	@Id
	@Column(name = "sStandardProductSeq", updatable = false)
	private String seq;

	@ManyToOne
	@JoinColumn(name = "nCategorySeq")
	private Category category;

	@Column(name = "sName", nullable = false)
	private String name;

	@Column(name = "sImageSource", nullable = false)
	private String imageSource;

	@Column(name = "sImageSourceURL")
	private String imageSourceURL;

	@Column(name = "sImageURL", nullable = false)
	private String imageURL;

	@Column(name = "dtManufactureDate", nullable = false)
	private String manufactureDate;

	@Column(name = "sDescription")
	private String description;

	@Column(name = "nLowestPrice")
	private int lowestPrice;

	@Column(name = "nMobileLowestPrice")
	private int mobileLowestPrice;

	@Column(name = "nAveragePrice")
	private int averagePrice;

	@Column(name = "nCooperationCompanyCount")
	private int cooperationCompanyCount;

	@Column(name = "nCombinedLowestPrice ")
	private int combinedLowestPrice ;



	public StandardProductResponse toDTO() {
		return StandardProductResponse.builder()
				.seq(seq)
				.categorySeq(category.getSeq())
				.name(name)
				.imageURL(imageURL)
				.imageSource(imageSource)
				.lowestPrice(lowestPrice)
				.mobileLowestPrice(mobileLowestPrice)
				.averagePrice(averagePrice)
				.cooperationCompanyCount(cooperationCompanyCount)
				.combinedLowestPrice(combinedLowestPrice)
				.build();
	}


	public StandardProduct updatePrice(StandardProduct standardProduct) {
		this.lowestPrice = standardProduct.getLowestPrice();
		this.mobileLowestPrice = standardProduct.getMobileLowestPrice();
		this.cooperationCompanyCount = standardProduct.getCooperationCompanyCount();
		this.combinedLowestPrice = standardProduct.getCombinedLowestPrice();
		this.averagePrice = standardProduct.getAveragePrice();
		return this;

	}

}
