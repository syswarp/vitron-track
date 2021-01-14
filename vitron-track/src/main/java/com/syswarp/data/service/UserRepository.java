package com.syswarp.data.service;
import com.syswarp.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, Integer> {
 
	User getByUsername(String username);
 
}
