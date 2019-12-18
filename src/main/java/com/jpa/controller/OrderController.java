package com.jpa.controller;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jpa.po.Order;
import com.jpa.service.IOrderService;
import com.jpa.util.ApiResponse;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	IOrderService orderService;
	
	@GetMapping("/{id}")
	@ResponseBody
	public ApiResponse detail(@PathVariable("id") int id) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, IllegalArgumentException, IntrospectionException {
		Order order = orderService.getByid(id);
		return new ApiResponse(order);
	}
}
