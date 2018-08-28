package com.project.model;

import javax.validation.constraints.*;
import javax.persistence.*;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "Customer")
@SequenceGenerator(name = "seq_2", initialValue = 1125, allocationSize = 1)
public class Customer implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String sex;
	private String name;
	private String tel;

	public Customer() {
	}

	public Customer(Integer id, String sex, String name, String tel) {
		super();
		this.id = id;
		this.sex = sex;
		this.name = name;
		this.tel = tel;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_2")
	// @NotNull(message="編號: 請勿空白(VO validation)")
	// @DecimalMin(value = "1000", message = "數字必需大於{value}")
	// @DecimalMax(value = "9999", message = "數字必需小於{value}")
	// @Digits( integer = 4, fraction = 0 , message="編號請輸入長度為4的正整數")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "sex")
//	@NotEmpty(message = "性別: 請勿空白(VO validation)")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "name")
	@Pattern(regexp = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,18}$", message = "請輸入中/英文字，且長度在2~18之間(VO validation)")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "tel")
	@NotEmpty(message = "電話: 請勿空白(VO validation)")
	@Pattern(regexp = "^[(0-9)]{10,12}$", message = "電話號碼格式錯誤，請輸入長度在10~12之間的整數(VO validation)")
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
}
