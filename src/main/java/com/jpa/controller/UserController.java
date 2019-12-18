package com.jpa.controller;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jpa.po.User;
import com.jpa.service.IUserService;
import com.jpa.util.ApiResponse;
import com.jpa.util.Helper;
import com.jpa.util.PageBo;
import com.jpa.vo.user.EditorVo;
import com.jpa.vo.user.ListVo;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;
	
	/**
	 * 获取当前登录用户的信息
	 * @return
	 */
	@RequestMapping(value = "/currenty-user", method = RequestMethod.GET)
	@ResponseBody
	public ApiResponse getCurrentyLoginUser() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return new ApiResponse(userDetails);
	}
	
	/**
	 * 用户详情
	 * @param id
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IntrospectionException
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ApiResponse get(@PathVariable("id") int id) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, IllegalArgumentException, IntrospectionException {
		User user = userService.detail(id);
		Helper.initPropNull(user, "orders.user");
		return new ApiResponse(user);
	}
	
	/**
	 * 用户列表
	 * @param pageNow
	 * @param pageSize
	 * @param listVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list/{pageNow}/{pageSize}", method = RequestMethod.GET)
	@ResponseBody
	public ApiResponse list(@PathVariable("pageNow") int pageNow, @PathVariable("pageSize") int pageSize, ListVo listVo) throws Exception {
		System.out.println(listVo.getCreateTimeEnd());
		PageBo<User> pageBo = userService.list(pageNow, pageSize, listVo);
		Helper.initPropNull(pageBo, "orders.user");
		return new ApiResponse(pageBo);
	}
	
	/**
	 * 添加用户
	 * @param editorVo
	 * @param br
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse add(@Valid @RequestBody EditorVo editorVo, BindingResult br) {
		if (br.hasErrors()) {
			return ApiResponse.validateErrResponse(br.getAllErrors());
		}
		System.out.println(editorVo.getUsername());
		this.userService.add(editorVo);
		return new ApiResponse();
	}
	
	/**
	 * 修改用户
	 * @param id
	 * @param editorVo
	 * @param br
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ApiResponse update(@PathVariable("id") int id, @Valid @RequestBody EditorVo editorVo, BindingResult br) {
		if (br.hasErrors()) {
			return ApiResponse.validateErrResponse(br.getAllErrors());
		}
		this.userService.update(id, editorVo);
		return null;
	}

}
