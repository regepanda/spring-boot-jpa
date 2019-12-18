package com.jpa.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.jpa.dao.UserRepository;
import com.jpa.po.User;
import com.jpa.util.Helper;
import com.jpa.util.JpaException;
import com.jpa.util.PageBo;
import com.jpa.vo.user.EditorVo;
import com.jpa.vo.user.ListVo;

@Service("userService")
@Transactional(rollbackOn = Exception.class)
public class UserService implements IUserService {
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepository userRepository;

	public User detail(int id) {
		User user = this.userRepository.findById(id).orElseThrow(() -> new JpaException("用户id错误"));
		return user;
	}

	/**
	 * 用户列表
	 */
	@Override
	public PageBo<User> list(int pageNow, int pageSize, ListVo listVo) {
		if (pageNow <= 1) {
			pageNow = 1;
		}
		if (pageSize <= 0) {
			pageSize = 15;
		}
		Pageable pageable = PageRequest.of(pageNow - 1, pageSize);
		Specification<User> specification = this.specificationByQueryRequest(listVo);
		Page<User> page = this.userRepository.findAll(specification, pageable);
		return new PageBo<User>(page);
	}
	
	/**
	 * 根据查询条件构造查询语句
	 * @param listVo
	 * @return
	 */
	public Specification<User> specificationByQueryRequest(ListVo listVo) {
		Specification<User> spec = (root, query, criteriaBuilder) -> { 
			List<Predicate> predicates = new ArrayList<>();
			if (!Helper.empty(listVo.getId()) && listVo.getId() != 0) {
				predicates.add(criteriaBuilder.equal(root.get("id").as(Integer.class), listVo.getId()));
			}
			if (!Helper.empty(listVo.getUsername())) {
				predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + listVo.getUsername() + "%"));
			}
			if (!Helper.empty(listVo.getCreateTimeStart())) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(Date.class), listVo.getCreateTimeStart()));
			}
			if (!Helper.empty(listVo.getCreateTimeEnd())) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(Date.class), listVo.getCreateTimeEnd()));
			}
			if (!Helper.empty(listVo.getBirthday())) {
				predicates.add(criteriaBuilder.equal(root.get("birthday").as(Date.class), listVo.getBirthday()));
			}
			query.where(predicates.toArray(new Predicate[predicates.size()]));
			query.orderBy(criteriaBuilder.desc(root.get("id")));
			return query.getRestriction();
		};
		return spec;
	}

	/**
	 * 添加用户
	 */
	@Override
	public void add(EditorVo editorVo) {
		if (userRepository.existsByUsername(editorVo.getUsername())) {
			throw new JpaException("用户存在");
		}
		String password = passwordEncoder.encode(editorVo.getPassword());
		System.out.println("生成的密码是" + password + "长度是" + password.length());
		User user = new User();
		user.setUsername(editorVo.getUsername());
		user.setNickname(editorVo.getNickname());
		user.setBirthday(editorVo.getBirthday());
		user.setPassword(password);
		user.setDietime(editorVo.getDietime());
		this.userRepository.save(user);
	}

	/**
	 * 修改用户
	 */
	@Override
	public void update(int id, EditorVo editorVo) {
		Optional<User> uOptional = this.userRepository.findById(id);
		if (!uOptional.isPresent()) {
			throw new JpaException("用户ID错误");
		}
		User user = uOptional.get();
		BeanUtils.copyProperties(editorVo, user);
		this.userRepository.save(user);
	}

	/**
	 * 根据用户名查找用户
	 */
	@Override
	public User findByUsername(String name) {
		return this.userRepository.findByUsername(name);
	}

}
