package office.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tCooperationCompany")
public class CooperationCompany {
	@Id
	@Column(name="sCooperationCompanySeq", updatable=false)
	private String seq;

	@Column(name = "sName", nullable=false)
	private String name;
}
