package com.example.whatseoul.service;

import com.example.whatseoul.dto.PostDto;
import com.example.whatseoul.entity.Post;
import com.example.whatseoul.entity.User;
import com.example.whatseoul.repository.post.PostRepository;
import com.example.whatseoul.repository.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final AccountService accountService;
    private final UserRepository userRepository;

    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        Comparator<Post> byCreatedAtComparator = new Comparator<Post>() {
            public int compare(Post p1, Post p2) {
                return p2.getCreatedAt().compareTo(p1.getCreatedAt());
            }
        };

        posts.sort(byCreatedAtComparator);
        List<PostDto> postDtos = posts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return postDtos;
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
        User user = userRepository.findByUserId(userId);
        postDto.setUserEmail(user.getUserEmail());
        postDto.setUserId(userId);
        Post post = convertToEntity(postDto);
        post = postRepository.save(post);
        convertToDto(post);
    }

    public void updatePost(PostDto postDto) {
        if (!postRepository.existsById(postDto.getId())) {
            throw new EntityNotFoundException("Post not found with id: " + postDto.getId());
        }
        Post post = convertToEntity(postDto);
        post = postRepository.save(post);
        convertToDto(post);
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
