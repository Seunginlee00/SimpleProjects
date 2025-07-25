package com.project.my.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_role_list")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoleList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 외래키로 실제 연관관계를 관리
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_list_id", nullable = false, referencedColumnName = "users_id")
    private Users users;

    @Column(name = "user_role_list_id")
    @Enumerated(EnumType.ORDINAL)
    private UserRole usersRole;
}
