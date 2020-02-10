package com.squad.pizzahut.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squad.pizzahut.entity.User;
import com.squad.pizzahut.entity.VendorFoodCategory;

@Repository
public interface VendorFoodCategoryRepository extends JpaRepository<VendorFoodCategory, Integer>{

	List<VendorFoodCategory> findByUser(User user);

}
