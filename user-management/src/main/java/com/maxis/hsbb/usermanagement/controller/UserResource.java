package com.maxis.hsbb.usermanagement.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.maxis.hsbb.usermanagement.db.model.HSBBOperator;
import com.maxis.hsbb.usermanagement.db.model.HSBBUserRole;
import com.maxis.hsbb.usermanagement.db.model.HSBBUserValidationReq;
import com.maxis.hsbb.usermanagement.db.model.HSBBUsers;
import com.maxis.hsbb.usermanagement.db.model.LOVResponse;
import com.maxis.hsbb.usermanagement.db.repository.HSBBUsersRepository;
import com.maxis.hsbb.usermanagement.exceptions.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@CrossOrigin 
public class UserResource {

	@Autowired
	HSBBUsersRepository hsbbUserRepository;



	@GetMapping("/users")
	public List<HSBBUsers> retrieveAllActiveUsers(HttpServletRequest request){


		String ip = request.getRemoteAddr();

		return hsbbUserRepository.findAllUsers();

	}

	@GetMapping("/users/{userId}")
	public HSBBUsers retrieveUser(@PathVariable String userId){
		return hsbbUserRepository.findById(userId);

	}

	@GetMapping("/operators")
	public List<LOVResponse> retrieveOperators() {


		List<LOVResponse> lovList = new ArrayList<LOVResponse>();

		List<HSBBOperator>  operatorList = hsbbUserRepository.findAllOperators();
		operatorList.forEach(operator -> lovList.add(new LOVResponse(operator.getOperatorId(), 
				operator.getOperatorName(), operator.getOperatorName())));

		return lovList;
	}

	@GetMapping("/roles")
	public List<LOVResponse> retrieveUserRoles(){
		List<LOVResponse> lovList = new ArrayList<LOVResponse>();

		List<HSBBUserRole>  roleList = hsbbUserRepository.findAllUserRoles();

		roleList.forEach( role-> lovList.add(new LOVResponse(role.getRoleId(), role.getRoleName(), role.getRoleName())));

		return lovList;
	}

	@PostMapping("/users/validateuser")
	public HSBBUsers valdiateUserLogin(@RequestBody HSBBUserValidationReq user ) throws UserNotFoundException, ValidationException{

		return hsbbUserRepository.validateUser(user.getUserId(), user.getPassword());
	}

	@PostMapping("/users")
	public ResponseEntity<Object> saveUser(@Valid @RequestBody  HSBBUsers user) {


		user.setPassword(BCrypt.hashpw("Maxis@123", BCrypt.gensalt()));
		user.setStatus("ACTV");
		HSBBUsers savedUser = hsbbUserRepository.save(user);
		//user
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{userId}")
				.buildAndExpand(savedUser.getUserId()).toUri();

		return ResponseEntity.created(location).build();

	}
	
	
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<Object> deleteUser(@PathVariable String userId) {

		hsbbUserRepository.deleteById(userId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}

}
