package com.project.my.entity.user;

import com.project.my.common.entity.BaseEntity;
import com.project.my.dto.login.UserRegisterRequest;
import com.project.my.entity.board.Board;
import com.project.my.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.SQLDelete;

@Table(name = "TB_USERS")
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE tb_users SET is_delete = true, modified_date = now()  WHERE users_id = ?")
public class Users extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long id; // 아이디

    @Column(nullable = false)
    private String loginId; // 로그인 아이디

    private String email; // 이메일

    private String password; // 패스워드

    private String nickname; // 닉네임

    @Enumerated(EnumType.STRING)
    private Role role; // 권한 일반/관리자

    @Column(name = "is_delete", nullable = false, columnDefinition = "boolean default false")
    private boolean isDelete = false;

    @OneToMany(mappedBy = "users")
    List<Board> boardList = new ArrayList<>();


    public void update(UserRegisterRequest request, String password) {
        this.email = request.email();
        this.nickname = request.nickname();
        if(password != null) {}
            this.password = password;
    }
}
