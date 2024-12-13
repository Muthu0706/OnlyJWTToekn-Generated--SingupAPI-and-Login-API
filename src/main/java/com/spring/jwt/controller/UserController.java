package com.spring.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spring.jwt.entity.JwtUtil;
import com.spring.jwt.entity.PasswordEncoder;
import com.spring.jwt.entity.User;
import com.spring.jwt.service.UserService;

import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/api")
public class UserController {
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtUtil  jwtUtil;
    
    @Autowired
    private PasswordEncoder password;
    
	@Value("${jwt.secret}")
	private String secret;

	
    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user) {
        User savedUser = userService.signup(user);
        return ResponseEntity.ok(savedUser);
    }
    
	
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User user) {
	    User foundUser = null;
	   if (user.getUsername() != null) {
	        foundUser = userService.findByUsername(user.getUsername());
	        String token = jwtUtil.generateToken(foundUser.getUsername());
	        return ResponseEntity.ok(token);
	    }
	    else {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }
      }
		
	@GetMapping("/hello")
	public String hello(@RequestHeader String authorization) {
	    if (authorization != null && authorization.startsWith("Bearer ")) {
	        String token = authorization.substring(7);
	        try {
	            String user = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	            return "Hello, " + user + "!";
	        } catch (Exception e) {
	            return "Unauthorized";
	        }
	    } else {
	        return "Unauthorized";
	    }
	}
}