package office.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	@ManyToOne
	@JoinColumn(name="sCooperationCompanySeq",  updatable=false)
	private CooperationCompany cooperationCompany;

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

	@Column(name = "sImageURL")
	private String imageURL;

    @CreatedDate
	@Column(name = "dtInputDate", nullable = false)
	private Date inputDate;

    public CooperationProductResponse toDTO() {
    	return CooperationProductResponse.builder()
    			.cooperationCompanySeq(cooperationCompany.getSeq())
    			.cooperationCompanyName(cooperationCompany.getName())
    			.cooperationProductSeq(cooperationProductSeq)
    			.categoryName(category.getName())
    			.name(name)
    			.imageURL(imageURL)
    			.URL(URL)
    			.price(price)
    			.mobilePrice(mobilePrice)
    			.inputDate(inputDateFormat(inputDate))
    			.build();
    }


    public String inputDateFormat(Date inputDate) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd"); //????????? ????????? ?????? ??????
		return simpleDateFormat.format(inputDate);
	}





}
