package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.Users;
import com.example.demo.mapper.UsersMapper;

/**
 * define class that implements UserDetailsService;
 * 
 **/

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UsersMapper usersMappers;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// call usersMappers's method to search database
		QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", username);
		Users users = usersMappers.selectOne(queryWrapper);
		
		if (users == null) {
			throw new UsernameNotFoundException("User Not Exists.");
		}
		
		List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("admins, ROLE_sale");
		return new User(users.getUsername(), new BCryptPasswordEncoder().encode(users.getPassword()), auths);  
	}

}
