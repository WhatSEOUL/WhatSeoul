package com.example.whatseoul.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_tb")
@EntityListeners(AuditingEntityListener.class)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(name = "USER_EMAIL", nullable = false, unique = true, length = 255) // length(ERD 포함)  길이 255로 변경했습니다.
	private String userEmail;

	@Column(name = "USER_PASSWORD", nullable = false, length = 100) //length(ERD 포함)  길이 100으로 변경했습니다.
	private String userPassword;

	@Column(name = "USER_NAME", nullable = false, unique = true, length = 30) //length(ERD 포함)  길이 30으로 변경했습니다.
	private String userName;

	@Column(name = "USER_CREATED")
	@CreatedDate
	private LocalDateTime userCreated;

	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive;  // 계정 활성 상태, 기본값은 true
}