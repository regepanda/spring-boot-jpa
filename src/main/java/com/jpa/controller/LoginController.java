package com.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jpa.auth.JwtTokenUtil;
import com.jpa.util.ApiResponse;
import com.jpa.util.Helper;
import com.jpa.vo.user.LoginVo;

@Controller
public class LoginController {
	@Autowired
	@Qualifier("jwtUserDetailsService")
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse login(@RequestBody LoginVo loginVo, BindingResult br) {
		if (br.hasErrors()) {
			return ApiResponse.validateErrResponse(br.getAllErrors());
		}
		UserDetails userDetails = userDetailsService.loadUserByUsername(loginVo.getUsername());
		if (Helper.empty(userDetails)) {
			return ApiResponse.authErrResponse("用户名错误");
		}
		if (!this.passwordEncoder.matches(loginVo.getPassword(), userDetails.getPassword())) {
			return ApiResponse.authErrResponse("密码错误");
		}
		String token = jwtTokenUtil.generateToken(userDetails);
		return new ApiResponse(token);
	}
}
