package com.maxis.hsbb.usermanagement.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "HSBB_USERS")
@EnableJpaRepositories
@ImportResource("classpath*:*jpa-named-queries.properties")
@NamedQuery(name="get_all_active_users", query = "select u from HSBBUsers u")
@Getter@Setter@NoArgsConstructor@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HSBBUsers {
	
	@Id
	@Column(name = "USER_ID")
	@Size(min = 2, message = "User ID should have atleast 2 chareacters")
	private String userId;
	
	@Column(name = "USER_NAME")
	private String userName;
	
	@Column(name = "USER_EMAIL")
	@NotNull @NotBlank
	@Email
	private String userEmail;
	
	@ManyToOne
	@JoinColumn(name = "ROLE_ID")
	private HSBBUserRole userRole;
	
	@ManyToOne
	@JoinColumn(name="OPERATOR_ID")
	private HSBBOperator userOperator;
	
	@JsonIgnore
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "LAST_LOGIN_IP")
	private String lastLoginIP;
	
	@Column(name = "LAST_LOGIN_AT")
	@UpdateTimestamp
	private Date lastLoginAt;
	
	@Column(name = "CREATION_DATE")
	@CreationTimestamp
	private Date creadtedDate;
		

}
