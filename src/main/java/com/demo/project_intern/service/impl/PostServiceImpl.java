package com.demo.project_intern.service.impl;

import com.demo.project_intern.constant.ErrorCode;
import com.demo.project_intern.dto.PostDto;
import com.demo.project_intern.dto.TopLikedPostDto;
import com.demo.project_intern.dto.UserDto;
import com.demo.project_intern.dto.request.post.PostCreateRequest;
import com.demo.project_intern.dto.request.post.PostLikeRequest;
import com.demo.project_intern.dto.request.post.PostSearchRequest;
import com.demo.project_intern.dto.request.post.PostUpdateRequest;
import com.demo.project_intern.dto.response.UserLikePost;
import com.demo.project_intern.entity.BookEntity;
import com.demo.project_intern.entity.PostEntity;
import com.demo.project_intern.entity.PostLikeEntity;
import com.demo.project_intern.entity.UserEntity;
import com.demo.project_intern.exception.BaseLibraryException;
import com.demo.project_intern.repository.BookRepository;
import com.demo.project_intern.repository.PostLikeRepository;
import com.demo.project_intern.repository.PostRepository;
import com.demo.project_intern.service.PostService;
import com.demo.project_intern.utils.CommonUtil;
import com.demo.project_intern.utils.PageableUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final PostLikeRepository postLikeRepository;
    private final ModelMapper mapper;
    private final CommonUtil commonUtil;


    @Override
    public PostDto createPost(PostCreateRequest request) {
        //lấy thông tin người dùng từ token
        UserEntity currentUser = commonUtil.getCurrentUser();

        PostEntity postEntity = mapper.map(request, PostEntity.class);

        BookEntity book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));

        postEntity.setBook(book);
        postEntity.setUser(currentUser);
        postRepository.save(postEntity);
        return PostDto
                .builder()
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .bookId(postEntity.getBook().getId())
                .userId(currentUser.getId())
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
        UserEntity currentUser = commonUtil.getCurrentUser();

        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));

        if(!post.getUser().getId().equals(currentUser.getId())) {
            throw new BaseLibraryException(ErrorCode.UNAUTHORIZED);
        }

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUpdatedAt(LocalDate.now());
        postRepository.save(post);
        return PostDto
                .builder()
                .title(post.getTitle())
                .content(post.getContent())
                .bookId(post.getBook().getId())
                .userId(currentUser.getId())
                .build();
    }

    @Override
    public void deletePost(Long postId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));
        postRepository.delete(post);
    }

    @Override
    public void toggleLike(PostLikeRequest request) {
        UserEntity currentUser = commonUtil.getCurrentUser();

        PostEntity post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));

        Optional<PostLikeEntity> existingLike = postLikeRepository
                .findByUserIdAndPostId(currentUser.getId(), post.getId());

        if (existingLike.isPresent()) {
            postLikeRepository.delete(existingLike.get());
        } else {
            PostLikeEntity postLike = PostLikeEntity
                    .builder()
                    .likeAt(LocalDate.now())
                    .user(currentUser)
                    .post(post)
                    .build();
            postLikeRepository.save(postLike);
        }
    }

    @Override
    public Long countByPostId(Long postId) {
        return postLikeRepository.countByPostId(postId);
    }

    @Override
    public List<UserLikePost> getUsersWhoLikedPost(Long postId) {
        List<PostLikeEntity> postLikeEntities = postLikeRepository.findAllByPostId(postId);
        return  postLikeEntities.stream()
                .map(like -> mapper.map(like.getUser(), UserLikePost.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TopLikedPostDto> getTop5MostLikedPosts() {
        Pageable topFive = PageRequest.of(0, 5);
        List<Object[]> results = postLikeRepository.findTop5MostLikedPosts(topFive);

        return results
                .stream()
                .map(record -> {
                    Long postId = (Long) record[0];
                    Long likeCount = (Long) record[1];

                    PostEntity post = postRepository.findById(postId)
                            .orElseThrow(() -> new BaseLibraryException(ErrorCode.RESOURCE_NOT_FOUND));

                    return TopLikedPostDto
                            .builder()
                            .postTitle(post.getTitle())
                            .likeCount(likeCount)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public Page<PostDto> search(PostSearchRequest request) {
        Pageable pageable = PageableUtils.from(request);
        return postRepository.search(request, pageable);
    }
}
