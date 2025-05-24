package com.radovan.spring.controllers;

import com.radovan.spring.converter.TempConverter;
import com.radovan.spring.dto.RoleDto;
import com.radovan.spring.dto.UserDto;
import com.radovan.spring.entity.UserEntity;
import com.radovan.spring.exceptions.DataNotValidatedException;
import com.radovan.spring.services.RoleService;
import com.radovan.spring.services.UserService;
import com.radovan.spring.utils.AuthenticationRequest;
import com.radovan.spring.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

	private UserService userService;
	private TempConverter tempConverter;
	private JwtUtil jwtUtil;
	private RoleService roleService;

	@Autowired
	private void initialize(UserService userService, TempConverter tempConverter, JwtUtil jwtUtil,
			RoleService roleService) {
		this.userService = userService;
		this.tempConverter = tempConverter;
		this.jwtUtil = jwtUtil;
		this.roleService = roleService;
	}

	@PostMapping(value = "/register")
	public ResponseEntity<String> createUser(@Validated @RequestBody UserDto user, Errors errors) {
		if (errors.hasErrors()) {
			throw new DataNotValidatedException(new Error("User data has not been validated"));
		}

		UserDto storedUser = userService.addUser(user);
		return new ResponseEntity<>("You have been registered successfully!", HttpStatus.OK);
	}

	@PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
	@GetMapping(value = "/users")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		return new ResponseEntity<>(userService.listAll(), HttpStatus.OK);
	}

	@PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
	@DeleteMapping(value = "/delete/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable("userId") Integer userId) {
		userService.deleteUser(userId);
		return new ResponseEntity<>("User with id " + userId + " has been permanently deleted!", HttpStatus.OK);
	}

	@GetMapping(value = "/me")
	public ResponseEntity<UserDto> getMyData() {
		return new ResponseEntity<>(userService.getCurrentUser(), HttpStatus.OK);
	}

	@PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
	@PutMapping(value = "/suspend/{userId}")
	public ResponseEntity<String> suspendUser(@PathVariable("userId") Integer userId) {
		userService.suspendUser(userId);
		return new ResponseEntity<>("User with id " + userId + " has been suspended!", HttpStatus.OK);
	}

	@PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
	@PutMapping(value = "/reactivate/{userId}")
	public ResponseEntity<String> reactivateUser(@PathVariable("userId") Integer userId) {
		userService.reactivateUser(userId);
		return new ResponseEntity<>("User with id " + userId + " has been reactivated!", HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<UserDto> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		Optional<Authentication> authOptional = userService.authenticateUser(authenticationRequest.getUsername(),
				authenticationRequest.getPassword());

		if (authOptional.isEmpty()) {
			throw new CredentialNotFoundException("Invalid username or password!");
		}

		UserDto userDto = userService.getUserByEmail(authenticationRequest.getUsername());
		final UserEntity userDetails = tempConverter.userDtoToEntity(userDto);

		List<RoleDto> userRoles = roleService.listAllByUserId(userDto.getId());
		List<String> roleNames = userRoles.stream().map(RoleDto::getRole).toList(); // ✅ Transformišemo `RoleDto` u
																					// `List<String>`
		final String jwt = jwtUtil.generateToken(userDto.getEmail(), roleNames);

		UserDto authUser = tempConverter.userEntityToDto(userDetails);
		authUser.setAuthToken(jwt);

		return new ResponseEntity<>(authUser, HttpStatus.OK);

	}

	@GetMapping("/public-key")
	public ResponseEntity<String> getPublicKey() {
		return ResponseEntity.ok(jwtUtil.getPublicKeyAsPEM());
	}

}
