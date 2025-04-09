package com.demo.project_intern.service;

import com.demo.project_intern.dto.CommentDto;
import com.demo.project_intern.dto.request.comment.*;
import com.demo.project_intern.dto.response.CommentReplyResponse;

import java.util.List;

public interface CommentService extends GenericSearchService<CommentSearchRequest, CommentDto> {
    CommentDto createComment(CommentCreateRequest request);
    List<CommentDto> getComments();
    CommentDto getComment(Long commentId);
    CommentDto updateComment(Long commentId, CommentUpdateRequest request);
    void deleteComment(Long commentId);
    List<CommentReplyResponse> getRepliesForComment(Long commentId);
}