package com.maxis.hsbb.usermanagement.db.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.maxis.hsbb.usermanagement.UserManagementApplication;
import com.maxis.hsbb.usermanagement.db.model.HSBBUsers;

@SpringBootTest(classes = UserManagementApplication.class)
public class JPQLTests {

	
	@Autowired
	EntityManager em;
	
	@Autowired
	HSBBUsersRepository hsbbUserReposiotry;
	
	@Test
	public void findAllActiveUsersNamed() {
		
		
		TypedQuery<HSBBUsers> query= em.createNamedQuery("HSBBUsers.getAllActiveUsers", HSBBUsers.class);
		List<HSBBUsers> userList = query.getResultList();		
		assertNotNull(userList);
		
	}
	
	@Test
	public void findAllActiveUsers() {
		
		List<HSBBUsers> userList = hsbbUserReposiotry.findAllUsers();	
		assertNotNull(userList);
		
	}
	
	
}
