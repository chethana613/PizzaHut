package com.squad.pizzahut.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squad.pizzahut.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
