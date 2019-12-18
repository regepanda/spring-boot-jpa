package com.jpa.service;

import com.jpa.po.User;
import com.jpa.util.PageBo;
import com.jpa.vo.user.EditorVo;
import com.jpa.vo.user.ListVo;

public interface IUserService {
	/**
	 * 用户详情
	 * @param id
	 * @return
	 */
	User detail(int id);
	
	/**
	 * 用户列表
	 * @return
	 */
	PageBo<User> list(int pageNow, int pageSize, ListVo listVo);
	
	/**
	 * 添加用户
	 * @param editorVo
	 */
	void add(EditorVo editorVo);
	
	/**
	 * 修改用户
	 * @param id
	 * @param editorVo
	 */
	void update(int id, EditorVo editorVo);
	
	/**
	 * 根据用户名查找用户
	 * @param name
	 * @return
	 */
	User findByUsername(String name);
}
