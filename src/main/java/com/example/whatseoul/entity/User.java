package com.example.whatseoul.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_tb")
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

	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive = true;  // 계정 활성 상태, 기본값은 true

	public void setIsActive(boolean b) {
	}
}