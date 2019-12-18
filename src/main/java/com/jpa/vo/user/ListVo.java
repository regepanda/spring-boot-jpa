package com.jpa.vo.user;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListVo {
	private int id;
	
	private String username;
	
	private Date createTimeStart;
	
	private Date createTimeEnd;
	
	private Date birthday;

}
