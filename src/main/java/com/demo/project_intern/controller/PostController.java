package com.demo.project_intern.controller;

import com.demo.project_intern.config.Translator;
import com.demo.project_intern.constant.EntityType;
import com.demo.project_intern.dto.PostDto;
import com.demo.project_intern.dto.TopLikedPostDto;
import com.demo.project_intern.dto.UserDto;
import com.demo.project_intern.dto.request.post.PostCreateRequest;
import com.demo.project_intern.dto.request.post.PostLikeRequest;
import com.demo.project_intern.dto.request.post.PostSearchRequest;
import com.demo.project_intern.dto.request.post.PostUpdateRequest;
import com.demo.project_intern.dto.response.ResponseData;
import com.demo.project_intern.dto.response.UserLikePost;
import com.demo.project_intern.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base-path}/post")
@Slf4j
@Tag(name = "Post Controller")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("")
    @Operation(method = "POST", summary = "Create Post", description = "API Create New Post")
    public ResponseData<PostDto> createPost(@RequestBody @Valid PostCreateRequest request) {
        return ResponseData.<PostDto>builder()
                .message(Translator.getSuccessMessage("add", EntityType.POST))
                .data(postService.createPost(request))
                .build();
    }

    @GetMapping("")
    @Operation(method = "GET", summary = "Get List Posts", description = "API Get List Posts")
    public ResponseData<List<PostDto>> getPosts() {
        return ResponseData.<List<PostDto>>builder()
                .message(Translator.getSuccessMessage("getList", EntityType.POST))
                .data(postService.getPosts())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{postId}")
    @Operation(method = "GET", summary = "Get Detail Post", description = "API Get Detail Post")
    public ResponseData<PostDto> getPost(@PathVariable("postId") Long postId) {
        return ResponseData.<PostDto>builder()
                .message(Translator.getSuccessMessage("getDetail", EntityType.POST))
                .data(postService.getPost(postId))
                .build();
    }

    @PutMapping("/{postId}")
    @Operation(method = "PUT", summary = "Update Post", description = "API Update Post")
    public ResponseData<PostDto> updatePost(@PathVariable("postId") Long postId, @RequestBody PostUpdateRequest request) {
        return ResponseData.<PostDto>builder()
                .message(Translator.getSuccessMessage("update", EntityType.POST))
                .data(postService.updatePost(postId, request))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{postId}")
    @Operation(method = "DELETE", summary = "Delete Post", description = "API Delete Post")
    public ResponseData<Void> deletePost(@PathVariable("postId") Long postId) {
        postService.deletePost(postId);
        return ResponseData.<Void>builder()
                .message(Translator.getSuccessMessage("delete", EntityType.POST))
                .build();
    }

    @PostMapping("/paging")
    @Operation(method = "POST", summary = "Get Paging Posts", description = "API Get Paging Posts")
    public ResponseData<Page<PostDto>> getPagingPosts(@RequestBody PostSearchRequest request) {
        return ResponseData.<Page<PostDto>>builder()
                .message(Translator.getSuccessMessage("getList", EntityType.POST))
                .data(postService.search(request))
                .build();
    }

    @PostMapping("/postLike")
    @Operation(method = "POST", summary = "Like Post", description = "API Create Like Post")
    public String likePost (@RequestBody @Valid PostLikeRequest request) {
        postService.toggleLike(request);
        return "successfully";
    }

    @GetMapping("/count/{postId}")
    @Operation(method = "GET", summary = "Count Like Post", description = "API Count Like Post")
    public ResponseData<Long> countLikes(@PathVariable Long postId) {
        return ResponseData.<Long>builder()
                .message(Translator.getSuccessMessage("getDetail", EntityType.POST))
                .data(postService.countByPostId(postId))
                .build();
    }

    @GetMapping("/getUsersWhoLikedPost/{postId}")
    @Operation(method = "GET", summary = "Get Users Who Liked Post", description = "API Get Users Who Liked Post")
    public ResponseData<List<UserLikePost>> getUsersWhoLikedPost(@PathVariable Long postId) {
        return ResponseData.<List<UserLikePost>>builder()
                .message(Translator.getSuccessMessage("getList", EntityType.POST))
                .data(postService.getUsersWhoLikedPost(postId))
                .build();
    }

    @GetMapping("/getTop5MostLikedPosts")
    @Operation(method = "GET", summary = "Get Top5 Most Liked Posts", description = "API Get Top5 Most Liked Posts")
    public ResponseData<List<TopLikedPostDto>> getTop5MostLikedPosts() {
        return ResponseData.<List<TopLikedPostDto>>builder()
                .message(Translator.getSuccessMessage("getList", EntityType.POST))
                .data(postService.getTop5MostLikedPosts())
                .build();
    }
}
