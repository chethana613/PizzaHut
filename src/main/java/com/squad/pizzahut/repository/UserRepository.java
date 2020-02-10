package com.squad.pizzahut.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squad.pizzahut.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByMobileAndPassword(Long email,String password);

	Optional<User> findByUserId(Long userId);


}
