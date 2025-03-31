package com.demo.project_intern.service;

import com.demo.project_intern.dto.PostDto;
import com.demo.project_intern.dto.request.post.PostCreateRequest;
import com.demo.project_intern.dto.request.post.PostUpdateRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    PostDto createPost(PostCreateRequest request);
    List<PostDto> getPosts();
    PostDto getPost(Long postId);
    PostDto updatePost(Long postId, PostUpdateRequest request);
    void deletePost(Long postId);
    Page<PostDto> searchPosts (String keyword, int page, int size, String sortBy, String direction);
}
