package com.maxis.hsbb.usermanagement.db.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.maxis.hsbb.usermanagement.UserManagementApplication;
import com.maxis.hsbb.usermanagement.db.model.HSBBOperator;
import com.maxis.hsbb.usermanagement.db.model.HSBBUserRole;
import com.maxis.hsbb.usermanagement.db.model.HSBBUsers;

@SpringBootTest(classes = UserManagementApplication.class)
class HSBBUsersTests {

	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	HSBBUsersRepository repository;
	
	
	
	/*
	 * @Test void contextLoads() {
	 * 
	 * logger.info("Inside context loads"); HSBBUsers user =
	 * repository.findById("dkamal@maxis.com.my"); logger.info("user email is:"+
	 * user.getUserEmail()); assertEquals("dkamal@maxis.com.my",
	 * user.getUserEmail()); }
	 */
	
	@Test
	public void findById_basic() {
		HSBBUsers user = repository.findById("dkamal@maxis.com.my");
		logger.info("user email is:"+ user.getUserEmail());
		assertEquals("dkamal@maxis.com.my", user.getUserEmail());
	}

	
	@Test
	@Transactional
	public void deleteById_basic() {
		
		repository.deleteById("kamal.singh8@maxis.com");
		HSBBUsers user = repository.findById("kamal.singh8@maxis.com");
		assertNull(user);
	}
	
	@Test
	@Transactional
	public void save_basic() {
		
		HSBBUsers users = repository.findById("dkamal@maxis.com.my");
		if(users!= null) {
			users.setUserName("Kamaldeep");
		}
		repository.save(users);
		
	}
	
	@Test
	public void findAllActiveUsers() {
		
		List<HSBBUsers> userList = repository.findAllUsers();	
		assertNotNull(userList);
		
	}
	
	
	@Test
	public void createUser() {
		HSBBUserRole userRole =  new HSBBUserRole(1,"Maxis", null);
		HSBBOperator userOperator = new HSBBOperator(1, "MAXIS", null);
		HSBBUsers users = new HSBBUsers("dkamal@maxis.com.my", "Kamal", "dkamal@maxis.com.my", userRole, userOperator, 
				"$2a$10$aCRG.te4dYWd4SS.fuBl2eH5.jL.hFEA1uf7aUlD/T73uP7HJaFFC", "ACTV", "10.200.52.222", new Date(), new Date());
		repository.save(users);
	}
	
}
