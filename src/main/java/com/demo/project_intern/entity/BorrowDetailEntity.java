package com.demo.project_intern.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "borrow_detail")
public class BorrowDetailEntity extends AbstractEntity<Long>  {
    @Column(name = "actual_return_date")
    private LocalDate actualReturnDate;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrow_book_id", nullable = false)
    private BorrowBookEntity borrowBook;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

}
