package com.demo.project_intern.service;

import com.demo.project_intern.dto.PostDto;
import com.demo.project_intern.dto.TopLikedPostDto;
import com.demo.project_intern.dto.UserDto;
import com.demo.project_intern.dto.request.post.PostCreateRequest;
import com.demo.project_intern.dto.request.post.PostLikeRequest;
import com.demo.project_intern.dto.request.post.PostSearchRequest;
import com.demo.project_intern.dto.request.post.PostUpdateRequest;
import com.demo.project_intern.dto.response.UserLikePost;

import java.util.List;

public interface PostService extends GenericSearchService<PostSearchRequest, PostDto> {
    PostDto createPost(PostCreateRequest request);
    List<PostDto> getPosts();
    PostDto getPost(Long postId);
    PostDto updatePost(Long postId, PostUpdateRequest request);
    void deletePost(Long postId);
    void toggleLike(PostLikeRequest request);
    Long countByPostId(Long postId);
    List<UserLikePost> getUsersWhoLikedPost(Long postId);
    List<TopLikedPostDto> getTop5MostLikedPosts();
}