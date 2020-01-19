package com.jpa.po;

import javax.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "role")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
public class Role {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
    private String name;
}
