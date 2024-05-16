package com.example.whatseoul.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

	private Long userId;
	private String userEmail;
	private String userPassword;
	private String userName;
}