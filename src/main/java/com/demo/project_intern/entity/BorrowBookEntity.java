package com.demo.project_intern.entity;

import com.demo.project_intern.constant.BorrowStatus;
import com.demo.project_intern.constant.BorrowType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "borrow_book", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class BorrowBookEntity extends AbstractEntity<Long>  {
    @Column(name = "code")
    private String code;
    @Column(name = "borrow_date")
    private LocalDate borrowDate;
    @Column(name = "expected_return_date")
    private LocalDate expectedReturnDate;
    @Column(name = "status")
    private BorrowStatus borrowStatus;
    @Column(name = "borrow_type")
    private BorrowType borrowType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "borrowBook", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<BorrowDetailEntity> borrowDetails = new ArrayList<>();
}
