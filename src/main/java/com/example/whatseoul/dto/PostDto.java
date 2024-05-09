package com.example.whatseoul.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    private String postTitle;
    private String postContent;
    private String postFile;
    private Long viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
