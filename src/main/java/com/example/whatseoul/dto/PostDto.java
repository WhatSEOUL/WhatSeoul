package com.example.whatseoul.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    private String postTitle;
    private String postContent;
    private String postFile;
    private Integer viewCount;
    private String createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    private LocalDateTime modifiedAt;
    private Long userId;

}
