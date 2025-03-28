package com.demo.project_intern.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "borrow_book")
public class BorrowBookEntity extends AbstractEntity<Long>  {
    @Column(name = "borrow_date")
    private LocalDate borrowDate;
    @Column(name = "expected_return_date")
    private LocalDate expectedReturnDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "borrowBook", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BorrowDetailEntity> borrowDetails = new ArrayList<>();
}
