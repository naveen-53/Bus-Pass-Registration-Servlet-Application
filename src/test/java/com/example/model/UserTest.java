package com.example.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UserTest {
	
	User user = new User();
	
	@Test
	void tsetUserGetterSetter() {
		
		user.setId(1);
		user.setUsername("naveen");
		user.setPassword("0000");
		user.setRole("ADMIN");
		
		assertEquals(1,user.getId());
		assertEquals("naveen",user.getUsername());
		assertEquals("0000",user.getPassword());
		assertEquals("ADMIN",user.getRole());
		
	}

}
