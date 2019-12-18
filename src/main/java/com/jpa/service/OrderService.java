package com.jpa.service;

import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jpa.dao.OrderRepository;
import com.jpa.po.Order;
import com.jpa.util.JpaException;

@Transactional(rollbackOn = Exception.class)
@Service("orderService")
public class OrderService implements IOrderService {
	
	@Autowired
	OrderRepository orderRepository;

	@Override
	public Order getByid(int id) {
		Optional<Order> ooOptional = orderRepository.getByid(id);
		if (!ooOptional.isPresent()) {
			throw new JpaException("id错误");
		}
		return ooOptional.get();
	}

}
