package com.maxis.hsbb.usermanagement.db.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "HSBB_USER_ROLE")
@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class HSBBUserRole {
	
	@Id
	@GeneratedValue
	@Column(name = "ROLE_ID")
	private Integer roleId;
	
	@Column(name = "USER_ROLE")
	@NotNull(message = "Role Name is mandatory")
	private String roleName;

	
	@OneToMany(mappedBy = "userRole",fetch = FetchType.LAZY)
	@JsonIgnore
	private List<HSBBUsers> hsbbUsers;
	

}
