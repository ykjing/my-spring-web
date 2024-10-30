package com.ticketcenter.user.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, String>{
	
	@Transactional
	@Modifying
	@Query("UPDATE User u SET u.salt= ?1 , u.password= ?2 WHERE u.id= ?3")
	int updatePwdById(String salt, String pwd, String id);
}
