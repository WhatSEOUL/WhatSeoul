package com.example.whatseoul.service;

import com.example.whatseoul.dto.CommentDto;
import com.example.whatseoul.entity.Comment;
import com.example.whatseoul.repository.post.CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    public CommentDto createComment(CommentDto commentDto) {
        if (commentDto.getComContent() == null) {
            throw new IllegalArgumentException("Comment content cannot be null");
        }
        Comment comment = convertToEntity(commentDto);
        comment = commentRepository.save(comment);
        return convertToDto(comment);
    }

    public List<CommentDto> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        return comments.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public CommentDto updateComment(Long id, CommentDto commentDto) {
        if (!commentRepository.existsById(id)) {
            throw new EntityNotFoundException("Comment not found with id: " + id);
        }
        Comment comment = convertToEntity(commentDto);
        comment = commentRepository.save(comment);
        return convertToDto(comment);
    }

    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new EntityNotFoundException("Comment not found with id: " + id);
        }
        commentRepository.deleteById(id);
    }


    private CommentDto convertToDto(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }

    private Comment convertToEntity(CommentDto commentDto) {
        return modelMapper.map(commentDto, Comment.class);
    }
}
