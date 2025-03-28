package com.demo.project_intern.repository;

import com.demo.project_intern.entity.BorrowBookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BorrowBookRepository extends JpaRepository<BorrowBookEntity, Long> {
}
