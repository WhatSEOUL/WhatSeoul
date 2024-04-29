package com.example.whatseoul.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(name = "USER_EMAIL", nullable = false, length = 255) // length(ERD 포함)  길이 255로 변경했습니다.
	private String userEmail;

	@Column(name = "USER_PASSWORD", nullable = false, length = 100) //length(ERD 포함)  길이 100으로 변경했습니다.
	private String userPassword;

	@Column(name = "USER_NAME", nullable = false, length = 30) //length(ERD 포함)  길이 30으로 변경했습니다.
	private String userName;

	@Column(name = "USER_CREATED")
	@CreatedDate
	private LocalDateTime userCreated;
}
