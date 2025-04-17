package com.project.my.entity.board;

import com.java.project.api.common.entity.BaseEntity;
import com.java.project.api.dto.board.BoardDto;
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
@SQLDelete(sql = "UPDATE tb_board SET is_delete = true, modified_date = now() WHERE board_id = ?")
@Table(name = "TB_BOARD")
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(length = 100)
    private String classify; //분류

    private String subject; // 제목

    @Column(columnDefinition = "TEXT")
    private String content; // 내용

    @Column(length = 100)
    private String userName; // 회원명

    private int views; // 조회수

    private Boolean isDelete;  // 삭제 여부
    private Boolean isTopExpo; // 상단 고정
    private Boolean answerType; // 댓글 답변 여부

    @ManyToOne
    @JoinColumn(name = "board_config_id")
    private BoardConfig boardConfig;


    public void update(BoardDto dto){
        if(dto.classify() != null)
            this.classify = dto.classify();
        if(dto.subject() != null)
            this.subject = dto.subject();
        if(dto.content() != null)
            this.content = dto.content();
    }


    public void viewUpdate(){
        this.views++;
    }

    public void setAnswerType() {
        this.answerType = !answerType;
    }



}
