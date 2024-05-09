package com.example.whatseoul.dto;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;
    private Long userId;
    private Long postId;
    private String comContent;
    private LocalDateTime comCreated;
    private LocalDateTime comModified;


}