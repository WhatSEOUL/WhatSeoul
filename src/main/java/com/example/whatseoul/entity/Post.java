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
@NoArgsConstructor

public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postId;

	@Column(name = "POST_TITLE", nullable = false, length = 100)
	private String postTitle;

	@Column(name = "POST_CONTENT", nullable = false, length = 3000)
	private  String postContent;

	@Column(name = "POST_FILE", length = 300)// 렝스 300으로 바꿨습니다.
	private String postFile;

	@Column(name = "VIEW_COUNT")
	private Long viewCount;


	@Column(name = "CREATED_AT", nullable = false)
	@CreatedDate
	private LocalDateTime createdAt;

	@Column(name = "MODIFIED_AT")
	@LastModifiedDate
	private LocalDateTime modifiedAt;
}