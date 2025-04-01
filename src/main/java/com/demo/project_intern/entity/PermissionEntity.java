package com.demo.project_intern.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "permission", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class PermissionEntity extends AbstractEntity<Long>  {
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;
}
