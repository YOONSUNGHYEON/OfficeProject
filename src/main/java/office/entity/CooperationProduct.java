package office.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import groovy.transform.builder.Builder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import office.dto.cooperationProduct.CooperationProductResponse;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@IdClass(CooperationProductID.class)
@Table(name = "tCooperationProductList")
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

    public CooperationProductResponse toDTO() {
    	return CooperationProductResponse.builder()
    			.cooperationCompanySeq(cooperationCompanySeq)
    			.cooperationProductSeq(cooperationProductSeq)
    			.category(category)
    			.standardProduct(standardProduct)
    			.name(name)
    			.imageURL(imageURL)
    			.URL(URL)
    			.price(price)
    			.mobilePrice(mobilePrice)
    			.inputDate(inputDate)
    			.build();
    }

    public CooperationProduct updateStandardProduct(StandardProduct standardProduct) {
    	this.standardProduct = standardProduct;
    	return this;
    }


}
