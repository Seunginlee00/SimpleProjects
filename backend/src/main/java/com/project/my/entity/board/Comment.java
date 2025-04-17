package com.project.my.entity.board;

import com.java.project.api.common.entity.BaseEntity;
import com.java.project.api.dto.board.CommentDto;
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
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content; // 내용

    @Column(length = 100)
    private String userName; // 회원명

    private Boolean isDelete;  // 삭제 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public void update(CommentDto dto){
        if(dto.content() != null)
            this.content = dto.content();
    }

}
