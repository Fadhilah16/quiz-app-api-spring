package com.quiz.app.services.user;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quiz.app.dtos.auth.JwtResponseDto;
import com.quiz.app.dtos.auth.LoginRequestDto;
import com.quiz.app.models.Role;
import com.quiz.app.models.User;
import com.quiz.app.models.enums.RoleTypes;
import com.quiz.app.repositories.RoleRepo;
import com.quiz.app.repositories.UserRepo;
import com.quiz.app.security.JwtUtils;

import lombok.AllArgsConstructor;


@Service
@Transactional
@AllArgsConstructor
public class UserService {
    private RoleRepo roleRepo;
    private UserRepo userRepo;
    private JwtUtils jwtUtils;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;


    public User createUser(User user){
       Role role = roleRepo.findByName(RoleTypes.ROLE_USER).orElseThrow(()-> new RuntimeException("Role is not found."));
       user.getRoles().add(role); 
       user.setPassword(passwordEncoder.encode(user.getPassword()));
       user.setCreatedAt(new Date());
       user.setUpdatedAt(new Date());
       return userRepo.save(user);
    }

    public Boolean existsByUsername(String username){
        return userRepo.existsByUsername(username);
    }

    public Optional<User> findUserByUsername(String username){
        return userRepo.findByUsername(username);
    }

    public Optional<User> findUserByJwtUsername(HttpServletRequest request){
		String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.parseJwt(request));
		return userRepo.findByUsername(username);
	}

    public JwtResponseDto userIsAuthenticated(LoginRequestDto loginRequest){
        Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());

        return new JwtResponseDto(token, userDetails.getId(), userDetails.getUsername(), roles);
    }


    
}
