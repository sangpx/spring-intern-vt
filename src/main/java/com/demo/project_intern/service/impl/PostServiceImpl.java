package com.demo.project_intern.service.impl;

import com.demo.project_intern.constant.ErrorCode;
import com.demo.project_intern.dto.PostDto;
import com.demo.project_intern.dto.request.post.PostCreateRequest;
import com.demo.project_intern.dto.request.post.PostSearchRequest;
import com.demo.project_intern.dto.request.post.PostUpdateRequest;
import com.demo.project_intern.entity.BookEntity;
import com.demo.project_intern.entity.PostEntity;
import com.demo.project_intern.exception.BaseLibraryException;
import com.demo.project_intern.repository.BookRepository;
import com.demo.project_intern.repository.PostRepository;
import com.demo.project_intern.service.PostService;
import com.demo.project_intern.utils.PageableUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final BookRepository bookRepository;
    private final ModelMapper mapper;

    @Override
    public PostDto createPost(PostCreateRequest request) {
        //TODO: Retrieve userId have permission CREATE_DATA
        PostEntity postEntity = mapper.map(request, PostEntity.class);
        BookEntity book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));
        postEntity.setBook(book);
        postRepository.save(postEntity);
        return PostDto
                .builder()
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .bookId(postEntity.getBook().getId())
                .build();
    }

    @Override
    public List<PostDto> getPosts() {
        return postRepository.findAll()
                .stream()
                .map(postEntity -> {
                    PostDto postDto = mapper.map(postEntity, PostDto.class);
                    if (postEntity.getBook() != null) {
                        postDto.setBookId(postEntity.getBook().getId());
                    }
                    return postDto;
                })
                .toList();
    }

    @Override
    public PostDto getPost(Long postId) {
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));
        return PostDto
                .builder()
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .bookId(postEntity.getBook().getId())
                .build();
    }

    @Override
    public PostDto updatePost(Long postId, PostUpdateRequest request) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUpdatedAt(LocalDate.now());
        postRepository.save(post);
        return PostDto
                .builder()
                .title(post.getTitle())
                .content(post.getContent())
                .bookId(post.getBook().getId())
                .build();
    }

    @Override
    public void deletePost(Long postId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));
        postRepository.delete(post);
    }

    @Override
    public Page<PostDto> search(PostSearchRequest request) {
        Pageable pageable = PageableUtils.from(request);
        return postRepository.search(request, pageable);
    }
}
