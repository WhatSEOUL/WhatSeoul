package com.example.whatseoul.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COM_ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "POST_ID")
	private Post post;

	@Column(name = "COM_CONTENT", nullable = false, length = 500)
	private String content;

	@Column(name = "COM_CREATED",nullable = false, updatable = false)
	@CreatedDate
	private String createdAt;

	@LastModifiedDate
	@Column(name = "COM_MODIFIED")
	private LocalDateTime modifiedAt;

	public void setContent(String content) {
		this.content = content;
	}
}