package com.jpa.po;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.FetchType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
//@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")//防止循环查询
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String username;
	
	private String nickname;
	
	private String password;
	
	@Column(columnDefinition = "datetime not null comment '生日'")
	@JsonFormat(timezone = "Asia/Shanghai", pattern = "yyyy-MM-dd")
	private Date birthday;
	
	@Column(columnDefinition = "datetime not null comment '死亡时间'")
	@JsonFormat(timezone = "Asia/Shanghai", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dietime;
	
	@ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private List<Role> roles;
	
	@CreatedDate
	@Column(nullable = false, updatable = false)
	@JsonFormat(timezone = "Asia/Shanghai", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	@LastModifiedDate
	@Column(nullable = false)
	@JsonFormat(timezone = "Asia/Shanghai", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private List<Order> orders;
}
