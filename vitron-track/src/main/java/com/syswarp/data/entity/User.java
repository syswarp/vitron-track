package com.syswarp.data.entity;

import javax.persistence.Entity;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import com.syswarp.data.AbstractEntity;

@Entity
public class User extends AbstractEntity {
  private String username;
  private String passwordSalt;
  private String passwordHash;
  
  
  
  public User() {}
  
  public User(String username, String password ) {
	 this.username = username;
	 this.passwordSalt = RandomStringUtils.random(32);
	 this.passwordHash =   DigestUtils.sha1Hex(password +  passwordSalt);
   }
  
  public boolean checkPassword(String password){
	  return DigestUtils.sha1Hex(password +  passwordSalt).equals(passwordHash);
  }

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getPasswordSalt() {
	return passwordSalt;
}

public void setPasswordSalt(String passwordSalt) {
	this.passwordSalt = passwordSalt;
}

public String getPasswordHash() {
	return passwordHash;
}

public void setPasswordHash(String passwordHash) {
	this.passwordHash = passwordHash;
}	
  
  
  
}
