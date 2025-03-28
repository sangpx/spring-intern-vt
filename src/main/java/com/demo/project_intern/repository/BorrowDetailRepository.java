package com.demo.project_intern.repository;

import com.demo.project_intern.entity.BorrowDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowDetailRepository extends JpaRepository<BorrowDetailEntity, Long> {
}
