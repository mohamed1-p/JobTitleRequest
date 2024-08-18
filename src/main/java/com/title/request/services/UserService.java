package com.title.request.services;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.title.request.DTO.RegisterDto;
import com.title.request.models.RoleEntity;
import com.title.request.models.UserEntity;
import com.title.request.repository.RoleRepository;
import com.title.request.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    @Autowired
    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder,
    		RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
        this.roleRepository=roleRepository;
    }

   
    public Boolean saveUser(RegisterDto userDto) {
    	if(userRepository.existsByusername(userDto.getUserName())) {
    		return false;
    	}
    	UserEntity user = new UserEntity();
    	user.setName(userDto.getName());
    	user.setUsername(userDto.getUserName());
    	user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    	
    	RoleEntity role = roleRepository.findByRole("EMPLOYEE").orElseThrow();
    	System.out.println("role"+ role);
    	user.setRoles(Collections.singletonList(role));
    	System.out.println("user"+user);
        userRepository.save(user);
        return true;
    }

    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByUsername(username).
				orElseThrow(() -> new UsernameNotFoundException("user not found"));
		
		return new User(user.getUsername(),user.getPassword(),
				mapRolesToAutority(user.getRoles()));
	}
	
	
	private Collection<GrantedAuthority> mapRolesToAutority(List<RoleEntity> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole()))
				.collect(Collectors.toList());
	}

}













