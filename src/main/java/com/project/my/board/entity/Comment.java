package com.project.my.board.entity;

import com.project.my.board.dto.CommentDto;
import com.project.my.user.entity.Users;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Where(clause = "is_delete = false")
@SQLDelete(sql = "UPDATE tb_comment SET is_delete = true, modified_date = now() WHERE comment_id = ?")
@Table(name = "TB_COMMENT")
public class Comment extends com.project.my.common.entity.BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content; // 내용

    private Boolean isDelete;  // 삭제 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;


    public void update(CommentDto dto){
        if(dto.content() != null)
            this.content = dto.content();
    }

}
