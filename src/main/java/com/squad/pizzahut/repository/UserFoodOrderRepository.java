package com.squad.pizzahut.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squad.pizzahut.entity.Food;
import com.squad.pizzahut.entity.UserFoodOrder;
import com.squad.pizzahut.entity.UserOrder;

@Repository
public interface UserFoodOrderRepository extends JpaRepository<UserFoodOrder, Long>{

	List<UserFoodOrder> findByUserOrder(UserOrder userOrder);

	List<UserFoodOrder> findByFood(Food food);

}
