package com.example.whatseoul.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "POST_ID")
	private Long id;

	@Column(name = "POST_TITLE", nullable = false, length = 100)
	private String postTitle;

	@Column(name = "POST_CONTENT", nullable = false, length = 3000)
	private  String postContent;

	@Column(name = "VIEW_COUNT", columnDefinition = "integer default 0")
	private int viewCount;

	@Column(name = "CREATED_AT", nullable = false, updatable = false)
	@CreatedDate
	private String createdAt;

	@Column(name = "MODIFIED_AT")
	@LastModifiedDate
	private LocalDateTime modifiedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<Comment> comments = new ArrayList<>();
}
