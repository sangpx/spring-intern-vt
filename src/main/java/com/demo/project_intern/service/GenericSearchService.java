package com.demo.project_intern.service;

import com.demo.project_intern.dto.request.BaseSearchRequest;
import org.springframework.data.domain.Page;

public interface GenericSearchService<R extends BaseSearchRequest, D> {
    Page<D> search(R request);
}