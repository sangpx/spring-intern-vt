package com.demo.project_intern.entity;

import jakarta.persistence.*;
import lombok.*;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "category", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class CategoryEntity extends AbstractEntity<Long>  {
    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
}
