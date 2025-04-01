package com.demo.project_intern.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "book", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class BookEntity extends AbstractEntity<Long>  {
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "author")
    private String author;
    @Column(name = "publisher")
    private String publisher;
    @Column(name = "published_year")
    private LocalDate publishedYear;
    @Column(name = "code")
    private String code;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonIgnore
    private Set<CategoryEntity> categories = new HashSet<>();

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PostEntity> posts = new ArrayList<>();

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<BorrowDetailEntity> borrowDetails = new ArrayList<>();

    //TODO: add field quantity
}
