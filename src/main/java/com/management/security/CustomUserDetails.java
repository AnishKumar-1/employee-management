package com.management.security;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.management.models.UserModel;
import com.management.repository.UserModelRepo;


@Service
public class CustomUserDetails implements UserDetailsService{

    @Autowired
    private UserModelRepo userModelRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	UserModel userModel=userModelRepo.findByEmail(email);
    	List<GrantedAuthority>authorities=grantedAuthorities(userModel.getRole().getName());
    	return buildUserForAuthentication(userModel,authorities);
    }
    
    
    
    //method which build or bind userdetails
    public UserDetails buildUserForAuthentication(UserModel userModel,List<GrantedAuthority> authority) {
    	return new User(userModel.getEmail(),userModel.getPassword(),authority);
    }
    
    
  	//method for convert single role to  list of grantedAthorities
  	public List<GrantedAuthority> grantedAuthorities(String userRole){
  		return Collections.singletonList(new SimpleGrantedAuthority(userRole));
  	}
     

}
