package com.demo.project_intern.controller;

import com.demo.project_intern.config.Translator;
import com.demo.project_intern.constant.EntityType;
import com.demo.project_intern.dto.PostDto;
import com.demo.project_intern.dto.request.post.PostCreateRequest;
import com.demo.project_intern.dto.request.post.PostUpdateRequest;
import com.demo.project_intern.dto.response.ResponseData;
import com.demo.project_intern.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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

    @DeleteMapping("/{postId}")
    @Operation(method = "DELETE", summary = "Delete Post", description = "API Delete Post")
    public String deletePost(@PathVariable("postId") Long postId) {
        postService.deletePost(postId);
        return "Deleted successfully!";
    }

    @GetMapping("/paging")
    @Operation(method = "GET", summary = "Get Paging Posts", description = "API Get Paging Posts")
    public ResponseData<Page<PostDto>> getPagingPosts(@RequestParam(required = false) String keyword,
                                                               @RequestParam() int page,
                                                               @RequestParam() int size,
                                                               @RequestParam(defaultValue = "title") String sortBy,
                                                               @RequestParam(defaultValue = "asc") String direction) {
        return ResponseData.<Page<PostDto>>builder()
                .message(Translator.getSuccessMessage("getList", EntityType.POST))
                .data(postService.searchPosts(keyword, page, size, sortBy, direction))
                .build();
    }
}
