package com.squad.pizzahut.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squad.pizzahut.entity.User;
import com.squad.pizzahut.entity.UserOrder;

@Repository
public interface UserOrderRepository extends JpaRepository<UserOrder, Long>{
	
	List<UserOrder> findByUserOrderByUserOrderIdDesc(User user);

	List<UserOrder> findByUser(User user);


}
