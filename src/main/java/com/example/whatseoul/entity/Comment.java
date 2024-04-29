package com.example.whatseoul.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long comId;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	@ManyToOne
	@JoinColumn(name = "POST_ID")
	private Post post;

	@Column(name = "COM_CONTENT", nullable = false, length = 500)
	private String comContent;

	@Column(name = "COM_CREATED",nullable = false)
	@CreatedDate
	private LocalDateTime comCreated;

	@LastModifiedDate
	@Column(name = "COM_MODIFIED")
	private LocalDateTime comModified;

}