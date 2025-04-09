package com.demo.project_intern.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "borrow_detail", indexes = {
        @Index(name = "idx_borrow_detail_book_id", columnList = "book_id"),
        @Index(name = "idx_borrow_detail_returned", columnList = "returned")
})
public class BorrowDetailEntity extends AbstractEntity<Long>  {
    @Column(name = "actual_return_date")
    private LocalDate actualReturnDate;
    @Column(name = "quantity")
    private int quantity;

    @Column(name = "returned")
    private boolean returned;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrow_book_id", nullable = false)
    private BorrowBookEntity borrowBook;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;
}
