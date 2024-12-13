package com.spring.jwt.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.spring.jwt.entity.PasswordEncoder;
import com.spring.jwt.entity.User;
import com.spring.jwt.repository.UserRepository;

@Service
@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    

    public User signup(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }
    

    
    public User findByUsername(String username) {
    	System.out.println(username); 
        return userRepository.findByUsername(username).orElse(null);
        }
       
    public User findByEmail(String email) {
    	return userRepository.findByEmail(email).orElse(null);
    }
}