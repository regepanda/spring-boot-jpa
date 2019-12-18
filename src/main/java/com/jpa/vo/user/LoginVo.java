package com.jpa.vo.user;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginVo {
	@NotEmpty(message = "用户名不能为空")
	private String username;
	
	@NotEmpty(message = "密码不能为空")
	private String password;
}
