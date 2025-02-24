package com.music.project.controllers;

import java.util.List;

import com.music.project.api.userRole.repository.UserRoleM2MRepository;
import com.music.project.api.userRole.service.UserRoleService;
import com.music.project.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/test")
public class TestController {

	@Autowired
	private UserRoleService userRoleService;

	@GetMapping(value = "test")
	public String test() {
		return "ok";
	}

	@GetMapping("/{userId}/roles")
	public List<Role> getRolesByUserId(@PathVariable Integer userId) {
		List<Role> roles = userRoleService.getRolesByUserId(userId);

		// ko co role
		if (roles.isEmpty()) {
			throw new RuntimeException("ko co role");
		}

		return roles;
	}
	

}
