package com.as.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.as.entities.LoginRequest;
import com.as.entities.LoginResponse;
import com.as.entities.User;

import com.as.services.UserService;

import ch.qos.logback.core.status.Status;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@ResponseBody
@RequestMapping("/user")

public class HomeController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	@GetMapping("/home")
	public String home()
	{
		return "index";
	}
	
	

	
	@GetMapping("/register")
	public String registerUser(Model  model) {
		User user=new User();
		model.addAttribute("user",user);
		return "register";
	}
	
	@PostMapping("/add")
	public ResponseEntity<User> adduser(@Valid @RequestBody User user){
		userService.registerUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
		
	}
	@PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        boolean isAuthenticated = userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        if (isAuthenticated) {
            return ResponseEntity.ok(new LoginResponse("Login successful", true));
        } else {
            return ResponseEntity.status(401).body(new LoginResponse("Invalid username or password", false));
        }
    }
	
	
	
	
	
	

}
