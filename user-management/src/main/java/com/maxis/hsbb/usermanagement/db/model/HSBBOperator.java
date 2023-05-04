package com.maxis.hsbb.usermanagement.db.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "HSBB_OPERATOR")
@NoArgsConstructor@AllArgsConstructor@Setter@Getter
public class HSBBOperator {

	
	@Id
	@GeneratedValue
	@Column(name = "OPERATOR_ID")
	private Integer operatorId;
	
	
	@Column(name = "OPERATOR_NAME")
	private String operatorName;
	
	@JsonIgnore
	@OneToMany(mappedBy = "userOperator", fetch = FetchType.LAZY)
	private List<HSBBUsers> hsbbUsers;
	
	
}
