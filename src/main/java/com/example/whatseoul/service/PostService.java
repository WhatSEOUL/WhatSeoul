package com.example.whatseoul.service;

import com.example.whatseoul.dto.PostDto;
import com.example.whatseoul.entity.Post;
import com.example.whatseoul.repository.post.PostRepository;
import com.example.whatseoul.repository.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final AccountService accountService;

    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));
        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);

        return convertToDto(post);
    }

    public void createPost(PostDto postDto) {
        Long userId = accountService.getAuthenticatedUserId();
//        Long userId = accountService.getAuthenticatedUserId();
//        User user = userRepository.findByUserId(userId);
//        postDto.setUserId(user.getUserId());
        postDto.setUserId(userId);
        Post post = convertToEntity(postDto);
//        post.setViewCount(0L);
        post = postRepository.save(post);
        convertToDto(post);
    }

    public PostDto updatePost(Long id, PostDto postDto) {
        if (!postRepository.existsById(id)) {
            throw new EntityNotFoundException("Post not found with id: " + id);
        }
        Post post = convertToEntity(postDto);
        post.setId(id);
        post = postRepository.save(post);
        return convertToDto(post);
    }

    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new EntityNotFoundException("Post not found with id: " + id);
        }
        postRepository.deleteById(id);
    }

    private PostDto convertToDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }

    private Post convertToEntity(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }
}
