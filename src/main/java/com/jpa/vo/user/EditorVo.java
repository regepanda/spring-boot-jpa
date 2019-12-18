package com.jpa.vo.user;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditorVo {
	@NotEmpty(message = "姓名不能为空")
	private String username;
	
	@NotEmpty(message = "昵称不能为空")
	private String nickname;
	
	@NotEmpty(message = "密码不能为空")
	private String password;
	
	@NotNull(message = "生日不能为空")
	private Date birthday;
	
	@NotNull(message = "死亡时间不能为空")
	private Date dietime;
}
