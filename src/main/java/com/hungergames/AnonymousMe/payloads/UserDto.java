package com.hungergames.AnonymousMe.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDto {

	private int id;
	
	private String username;
	
	private String password;
	
	private String email;
}
