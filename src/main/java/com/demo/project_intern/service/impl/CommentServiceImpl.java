package com.demo.project_intern.service.impl;

import com.demo.project_intern.constant.ErrorCode;
import com.demo.project_intern.dto.CommentDto;
import com.demo.project_intern.dto.request.comment.CommentCreateRequest;
import com.demo.project_intern.dto.request.comment.CommentSearchRequest;
import com.demo.project_intern.dto.request.comment.CommentUpdateRequest;
import com.demo.project_intern.dto.response.CommentReplyResponse;
import com.demo.project_intern.entity.CommentEntity;
import com.demo.project_intern.entity.PostEntity;
import com.demo.project_intern.entity.UserEntity;
import com.demo.project_intern.exception.BaseLibraryException;
import com.demo.project_intern.repository.CommentRepository;
import com.demo.project_intern.repository.PostRepository;
import com.demo.project_intern.service.CommentService;
import com.demo.project_intern.utils.CommonUtil;
import com.demo.project_intern.utils.PageableUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper mapper;
    private final CommonUtil commonUtil;

    @Override
    public CommentDto createComment(CommentCreateRequest request) {
        //lấy thông tin người dùng từ token
        UserEntity currentUser = commonUtil.getCurrentUser();

        PostEntity post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));

        CommentEntity comment = mapper.map(request, CommentEntity.class);
        comment.setPost(post);
        comment.setUser(currentUser);

        if (request.getParentCommentId() != null) {
            CommentEntity parentComment = commentRepository.findById(request.getParentCommentId())
                    .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));
            comment.setParentComment(parentComment);
        }
        commentRepository.save(comment);
        return CommentDto
                .builder()
                .postId(post.getId())
                .userId(currentUser.getId())
                .content(comment.getContent())
                .replies(comment.getReplies()
                        .stream()
                        .map(commentEntity -> mapper.map(comment, CommentReplyResponse.class))
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public List<CommentReplyResponse> getRepliesForComment(Long commentId) {
        List<CommentEntity> replies = commentRepository.findByParentCommentId(commentId);
        return replies.stream()
                .map(commentEntity -> mapper.map(commentEntity, CommentReplyResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getComments() {
        return commentRepository.findAll()
                .stream()
                .map(commentEntity -> mapper.map(commentEntity, CommentDto.class))
                .toList();
    }

    @Override
    public CommentDto getComment(Long commentId) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));
        return CommentDto
                .builder()
                .postId(comment.getPost().getId())
                .userId(comment.getUser().getId())
                .content(comment.getContent())
                .replies(comment.getReplies()
                        .stream()
                        .map(commentEntity -> CommentReplyResponse
                                 .builder()
                                 .content(commentEntity.getContent())
                                 .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public CommentDto updateComment(Long commentId, CommentUpdateRequest request) {
        //lấy thông tin người dùng từ token
        UserEntity currentUser = commonUtil.getCurrentUser();

        CommentEntity commentExisted = commentRepository.findById(commentId)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));

        if(!commentExisted.getUser().getId().equals(currentUser.getId())) {
            throw new BaseLibraryException(ErrorCode.UNAUTHORIZED);
        }

        commentExisted.setContent(request.getContent());
        commentExisted.setUpdatedAt(LocalDate.now());
        commentRepository.save(commentExisted);
        return CommentDto
                .builder()
                .content(commentExisted.getContent())
                .postId(commentExisted.getPost().getId())
                .userId(currentUser.getId())
                .build();
    }

    @Override
    public void deleteComment(Long commentId) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));
        commentRepository.delete(comment);
    }

    @Override
    public Page<CommentDto> search(CommentSearchRequest request) {
        Pageable pageable = PageableUtils.from(request);
        return commentRepository.search(request, pageable);
    }
}
