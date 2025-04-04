package com.demo.project_intern.utils;

import com.demo.project_intern.dto.request.BaseSearchRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableUtils {
    public static Pageable from(BaseSearchRequest request) {
        String sortBy = request.getSortBy() != null ? request.getSortBy() : "id";
        String direction = request.getDirection() != null ? request.getDirection() : "asc";
        return PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by(Sort.Direction.fromString(direction), sortBy)
        );
    }
}