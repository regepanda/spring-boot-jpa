package com.jpa.auth;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.jpa.po.Role;
import com.jpa.po.User;
import com.jpa.service.IUserService;
import com.jpa.util.Helper;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private IUserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<GrantedAuthority> authorities = new ArrayList<>();
		User user = userService.findByUsername(username);
		if (Helper.empty(user)) {
			return null;
		}
	
        List<Role> roles = user.getRoles();
        for (Role role : roles) {
        	authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
		return new SecurityUserDetails(username, user.getPassword(), authorities);
	}

}
