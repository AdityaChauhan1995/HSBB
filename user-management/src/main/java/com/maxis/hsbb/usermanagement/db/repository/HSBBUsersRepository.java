package com.maxis.hsbb.usermanagement.db.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import com.maxis.hsbb.usermanagement.db.model.HSBBOperator;
import com.maxis.hsbb.usermanagement.db.model.HSBBUserRole;
import com.maxis.hsbb.usermanagement.db.model.HSBBUsers;
import com.maxis.hsbb.usermanagement.exceptions.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;


@Repository
@Transactional
@Slf4j
public class HSBBUsersRepository {

	
	@Autowired
	EntityManager em;
	
	public HSBBUsers findById(String userId) {
		return em.find(HSBBUsers.class, userId);
	}
	
	public HSBBUsers save(HSBBUsers user) {

		if(em.find(HSBBUsers.class, user.getUserId()) == null) {
			em.persist(user);
		}else {
			em.merge(user);
		}
		
		return user;
	}
	
	public boolean deleteById(String userId) {
		
		HSBBUsers users = em.find(HSBBUsers.class, userId);
		
		if(users != null) {
			em.remove(users);
			return true;
		}
		return false;
	}
	
	public List<HSBBUsers> findAllUsers(){
		
		TypedQuery<HSBBUsers> query= em.createQuery("select users from HSBBUsers users", HSBBUsers.class);
		List<HSBBUsers> userList = query.getResultList();
		return userList;
		
	}
	
	public List<HSBBOperator> findAllOperators(){
		TypedQuery<HSBBOperator> query = em.createQuery("select operators from HSBBOperator operators", HSBBOperator.class);
		List<HSBBOperator> operatorList = query.getResultList();
		return operatorList;
	}
	
	
	public List<HSBBUserRole> findAllUserRoles(){
		TypedQuery<HSBBUserRole> query = em.createQuery("select role from HSBBUserRole role", HSBBUserRole.class);
		List<HSBBUserRole> roleList = query.getResultList();
		return roleList;
	}
	
	public HSBBUsers validateUser(String userId, String password) throws UserNotFoundException, ValidationException {
		try {
			TypedQuery<HSBBUsers> query = em.createQuery("select users from HSBBUsers users where users.userId=:userId", HSBBUsers.class);
			query.setParameter("userId", userId);
			
			HSBBUsers user = query.getResultStream().findFirst().orElse(null);
			
			if(user == null) {
				throw new UserNotFoundException("User with User Id: "+ userId+" doesn't exist in our record");
			}else {
				if(user.getPassword() != null) {
					if(!BCrypt.checkpw(password, user.getPassword())) {
						throw new ValidationException("Incorrect password entered by user");
					}
				}else {
					throw new ValidationException("Password stored in db is blank");
				}
			}
		}catch(UserNotFoundException e) {
			e.printStackTrace();
			log.error("UserNotFoundException exception inside validateUser in HSBBUsersRepository class"+e);
			throw new UserNotFoundException("User with User Id: "+ userId+" doesn't exist in our record");
		}catch(ValidationException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			log.error("ValidationException exception inside validateUser in HSBBUsersRepository class"+e);
			throw new ValidationException(e);
		}
		
		return em.find(HSBBUsers.class, userId);
	}
	
	
}
