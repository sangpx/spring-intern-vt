package com.demo.project_intern.controller;

import com.demo.project_intern.config.Translator;
import com.demo.project_intern.constant.EntityType;
import com.demo.project_intern.dto.CommentDto;
import com.demo.project_intern.dto.request.comment.CommentCreateRequest;
import com.demo.project_intern.dto.request.comment.CommentSearchRequest;
import com.demo.project_intern.dto.request.comment.CommentUpdateRequest;
import com.demo.project_intern.dto.response.CommentReplyResponse;
import com.demo.project_intern.dto.response.ResponseData;
import com.demo.project_intern.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base-path}/comment")
@Slf4j
@Tag(name = "Comment Controller")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("")
    @Operation(method = "POST", summary = "Create Comment", description = "API Create New Comment")
    public ResponseData<CommentDto> createPost(@RequestBody @Valid CommentCreateRequest request) {
        return ResponseData.<CommentDto>builder()
                .message(Translator.getSuccessMessage("add", EntityType.COMMENT))
                .data(commentService.createComment(request))
                .build();
    }

    @GetMapping("")
    @Operation(method = "GET", summary = "Get List Comments", description = "API Get List Comments")
    public ResponseData<List<CommentDto>> getPosts() {
        return ResponseData.<List<CommentDto>>builder()
                .message(Translator.getSuccessMessage("getList", EntityType.COMMENT))
                .data(commentService.getComments())
                .build();
    }

    @GetMapping("/getRepliesFor/{commentId}")
    @Operation(method = "GET", summary = "Get Replies For Comment", description = "API Get Replies For Comment")
    public ResponseData<List<CommentReplyResponse>> getRepliesForComment(@PathVariable("commentId") Long commentId) {
        return ResponseData.<List<CommentReplyResponse>>builder()
                .message(Translator.getSuccessMessage("getDetail", EntityType.COMMENT))
                .data(commentService.getRepliesForComment(commentId))
                .build();
    }

    @GetMapping("/{commentId}")
    @Operation(method = "GET", summary = "Get Detail Comment", description = "API Get Detail Comment")
    public ResponseData<CommentDto> getComment(@PathVariable("commentId") Long commentId) {
        return ResponseData.<CommentDto>builder()
                .message(Translator.getSuccessMessage("getDetail", EntityType.COMMENT))
                .data(commentService.getComment(commentId))
                .build();
    }

    @PutMapping("/{commentId}")
    @Operation(method = "PUT", summary = "Update Comment", description = "API Update Comment")
    public ResponseData<CommentDto> updateComment(@PathVariable("commentId") Long commentId, @RequestBody CommentUpdateRequest request) {
        return ResponseData.<CommentDto>builder()
                .message(Translator.getSuccessMessage("update", EntityType.COMMENT))
                .data(commentService.updateComment(commentId, request))
                .build();
    }

    @DeleteMapping("/{commentId}")
    @Operation(method = "DELETE", summary = "Delete Comment", description = "API Delete Comment")
    public ResponseData<Void> deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);return ResponseData.<Void>builder()
                .message(Translator.getSuccessMessage("delete", EntityType.COMMENT))
                .build();

    }

    @PostMapping("/paging")
    @Operation(method = "POST", summary = "Get Paging Comments", description = "API Get Paging Comments")
    public ResponseData<Page<CommentDto>> getPagingComments(@RequestBody CommentSearchRequest request) {
        return ResponseData.<Page<CommentDto>>builder()
                .message(Translator.getSuccessMessage("getList", EntityType.COMMENT))
                .data(commentService.search(request))
                .build();
    }
}